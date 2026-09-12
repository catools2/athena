#!/usr/bin/env python3
"""Check that the loaded data actually reaches the UI.

    python3 verify.py --host http://localhost:8080

Deliberately probes the curated analytics queries rather than counting rows in PostgreSQL. "The
inserts succeeded" and "the dashboard shows something" are different claims, and only the second
one is what anybody wanted - most of the ways this goes wrong (a missing pod label, a status
string a view does not recognise, an unrefreshed materialized view) leave the rows in place and
the panels empty.
"""

from __future__ import annotations

import argparse
import sys
from datetime import datetime, timedelta, timezone

import requests

WINDOW_DAYS = 200


def window(days: int = WINDOW_DAYS) -> dict:
    end = datetime.now(timezone.utc)
    return {
        "timeFrom": (end - timedelta(days=days)).strftime("%Y-%m-%dT%H:%M:%S.000Z"),
        "timeTo": end.strftime("%Y-%m-%dT%H:%M:%S.000Z"),
    }


def run(host: str, query: str, params: dict) -> dict | None:
    response = requests.post(f"{host}/analytics/queries/{query}/run", json=params, timeout=60)
    if not response.ok:
        return None
    return response.json()


def check(host: str, name: str, query: str, params: dict, assertion) -> bool:
    result = run(host, query, params)
    if result is None:
        print(f"  FAIL  {name:<34} query {query} did not run")
        return False
    ok, detail = assertion(result)
    print(f"  {'ok  ' if ok else 'FAIL'}  {name:<34} {detail}")
    return ok


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__,
                                     formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("--host", default="http://localhost:8080")
    parser.add_argument("--months", type=int, default=6,
                        help="the window the data was generated over; sizes the regression probe")
    args = parser.parse_args()
    w = window()
    scope = {**w, "version": None, "project": None, "team": []}
    results = []

    print(f"probing {args.host} over the last {WINDOW_DAYS} days\n")

    def rows(result: dict) -> list:
        return result.get("rows", [])

    # The three panels that were empty before this generator existed.
    results.append(check(args.host, "every domain ingested", "change_coverage", {},
                         lambda r: (all(row[1] for row in rows(r)),
                                    ", ".join(f"{row[0]}={'yes' if row[1] else 'NO'}"
                                              for row in rows(r)))))
    results.append(check(args.host, "commits", "correlate_commits",
                         {**w, "repository": [], "author": None, "search": None},
                         lambda r: (len(rows(r)) > 0, f"{len(rows(r))} rows")))
    results.append(check(args.host, "pipeline runs", "correlate_pipeline_runs",
                         {**w, "pipeline": None, "version": None, "environment": None},
                         lambda r: (len(rows(r)) > 0, f"{len(rows(r))} rows")))
    results.append(check(args.host, "pods", "correlate_pods",
                         {**w, "namespace": None, "app": None, "search": None},
                         lambda r: (len(rows(r)) > 0,
                                    f"{len(rows(r))} rows (mv_pod_basic_info, so the labels worked)")))

    # Quality: the shapes the cycles page draws.
    def cycles_assertion(r):
        data = rows(r)
        cols = [c["name"] for c in r["columns"]]
        blocked = sum(row[cols.index("blocked")] for row in data)
        not_run = sum(row[cols.index("not_run")] for row in data)
        return (len(data) > 0 and blocked > 0 and not_run > 0,
                f"{len(data)} cycles, {blocked} blocked, {not_run} not run")

    results.append(check(args.host, "cycles with blocked and not-run", "cycles_list",
                         {**scope, "status": None, "search": None}, cycles_assertion))

    def profile_assertion(r):
        data = rows(r)
        dims = {row[0] for row in data}
        teams = [row for row in data if row[0] == "team"]
        spread = ({row[1]: row[5] for row in teams} if teams else {})
        return (dims >= {"priority", "team", "executor", "type"} and len(set(spread.values())) > 1,
                f"dimensions {sorted(dims)}; team failures {spread}")

    results.append(check(args.host, "failure profile, all dimensions", "cycle_failure_profile",
                         {**scope, "cycle": None}, profile_assertion))

    # Performance: the planted regression must be visible as a positive delta.
    #
    # perf_regression compares the chosen window against the window immediately before it, so the
    # window has to straddle the step change: the generator plants it two thirds of the way in, so
    # a window of one third of the range puts the regression in the current half and clean data in
    # the baseline. Too wide and the baseline predates the dataset; too narrow and both halves sit
    # after the step and the delta correctly vanishes.
    regression_window = window(int(args.months * 30 / 3))
    def regression_assertion(r):
        data = rows(r)
        cols = [c["name"] for c in r["columns"]]
        worst = data[0] if data else None
        return (bool(data) and worst[cols.index("delta_ms")] > 0,
                f"{len(data)} actions; worst = {worst[0]} +{worst[cols.index('delta_ms')]}ms"
                if worst else "no comparable actions")

    results.append(check(args.host, "a timing regression is visible", "perf_regression",
                         {**regression_window, "action": None, "environment": None,
                          "project": None, "actionType": None}, regression_assertion))

    # Overview: all three activity series must be non-zero on overlapping days.
    def activity_assertion(r):
        data = rows(r)
        both = [row for row in data if row[1] and row[2] and row[3]]
        return (len(both) > 0,
                f"{len(both)} of {len(data)} days carry tests, commits and pipeline runs together")

    results.append(check(args.host, "activity correlates across domains", "overview_activity",
                         scope, activity_assertion))

    failed = results.count(False)
    print(f"\n{len(results) - failed}/{len(results)} checks passed")
    if failed:
        print("\nIf rows exist but checks fail, the materialized views are probably stale:")
        print("  SELECT * FROM athena.refresh_analytics_views();")
    return 1 if failed else 0


if __name__ == "__main__":
    sys.exit(main())
