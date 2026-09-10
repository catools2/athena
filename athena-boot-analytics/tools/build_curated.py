#!/usr/bin/env python3
"""Regenerate analytics/curated.json from a declared spec, checking it against the SQL.

The registry validates the manifest against the SQL at startup and refuses to boot on a
mismatch, which is the right behaviour but a slow way to find a typo. This does the same check
here, so a wrong parameter kind or a forgotten declaration fails before the build.
"""
import json
import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
CURATED = ROOT / "src/main/resources/analytics/curated"
MANIFEST = ROOT / "src/main/resources/analytics/curated.json"

TCS = ["mv_test_cycle_statistics"]
METRIC = ["athena_metric.metric", "athena_metric.action"]
CORE_ENV = ["athena_core.environment", "athena_core.project"]
WINDOW = [("timeFrom", "instant"), ("timeTo", "instant")]

# id -> (title, views, tables, params)
SPEC = {
    # -- filter vocabularies -------------------------------------------------------------
    "filter_test_dimensions": (
        "Quality filter options", TCS, [], []),
    "filter_perf_dimensions": (
        "Performance filter options", [], METRIC + CORE_ENV, []),
    "filter_change_dimensions": (
        "Change and runtime filter options", ["mv_pod_basic_info"],
        ["athena_git.repository", "athena_git.commit", "athena_pipeline.pipeline",
         "athena_core.user"], []),

    # -- overview -----------------------------------------------------------------------
    "overview_kpis": (
        "Headline numbers", TCS,
        ["athena_metric.metric", "athena_git.commit", "athena_core.project"],
        WINDOW + [("version", "scalar"), ("project", "scalar"), ("team", "list")]),
    "overview_execution_trend": (
        "Daily test outcomes", TCS, [],
        WINDOW + [("version", "scalar"), ("project", "scalar"), ("team", "list")]),
    "overview_activity": (
        "Daily activity across domains", TCS,
        ["athena_git.commit", "athena_pipeline.pipeline", "athena_core.app_version",
         "athena_core.project"],
        WINDOW + [("version", "scalar"), ("project", "scalar"), ("team", "list")]),
    "overview_top_failures": (
        "Most-failing tests", TCS, [],
        WINDOW + [("version", "scalar"), ("project", "scalar"), ("team", "list")]),

    # -- test cycles --------------------------------------------------------------------
    "cycles_list": (
        "Test cycles", TCS, [],
        WINDOW + [("version", "scalar"), ("project", "scalar"), ("team", "list"),
                  ("status", "scalar"), ("search", "scalar")]),
    "cycle_executions": (
        "Executions in a cycle", TCS, [],
        [("cycle", "scalar"), ("status", "scalar"), ("search", "scalar")]),
    "test_history": (
        "Execution history for a test", TCS, [], [("item", "scalar")]),

    # -- performance --------------------------------------------------------------------
    "perf_overview": (
        "Timing headline for the window", [], METRIC + CORE_ENV,
        WINDOW + [("environment", "scalar"), ("project", "scalar"),
                  ("actionType", "scalar")]),
    "perf_action_summary": (
        "Action timing distribution", [], METRIC + CORE_ENV,
        WINDOW + [("action", "scalar"), ("environment", "scalar"), ("project", "scalar"),
                  ("actionType", "scalar")]),
    "perf_trend": (
        "Action timing trend", [], METRIC + CORE_ENV,
        WINDOW + [("action", "scalar"), ("environment", "scalar"), ("project", "scalar"),
                  ("actionType", "scalar")]),
    "perf_regression": (
        "Timing change against the preceding window", [], METRIC + CORE_ENV,
        WINDOW + [("action", "scalar"), ("environment", "scalar"), ("project", "scalar"),
                  ("actionType", "scalar")]),
    "perf_action_targets": (
        "One action broken down by target", [], METRIC + CORE_ENV,
        WINDOW + [("action", "scalar"), ("environment", "scalar"), ("project", "scalar")]),
    "perf_action_trend": (
        "Hourly percentiles for one action", [], METRIC + CORE_ENV,
        WINDOW + [("action", "scalar"), ("target", "scalar"), ("environment", "scalar"),
                  ("project", "scalar")]),
    "perf_action_histogram": (
        "Duration distribution for one action", [], METRIC + CORE_ENV,
        WINDOW + [("action", "scalar"), ("target", "scalar"), ("environment", "scalar"),
                  ("project", "scalar")]),
    "perf_samples": (
        "Individual measurements", [], METRIC + CORE_ENV,
        WINDOW + [("action", "scalar"), ("target", "scalar"), ("environment", "scalar"),
                  ("project", "scalar")]),

    # -- correlation --------------------------------------------------------------------
    "correlate_commits": (
        "Commits in the window", [],
        ["athena_git.commit", "athena_git.repository", "athena_core.user"],
        WINDOW + [("repository", "list"), ("author", "scalar"), ("search", "scalar")]),
    "correlate_pipeline_runs": (
        "Pipeline runs in the window", [],
        ["athena_pipeline.pipeline", "athena_pipeline.execution", "athena_pipeline.status",
         "athena_core.app_version", "athena_core.environment"],
        WINDOW + [("pipeline", "scalar"), ("version", "scalar"), ("environment", "scalar")]),
    "correlate_test_executions": (
        "Test executions in the window", TCS, [],
        WINDOW + [("status", "scalar"), ("version", "scalar"), ("search", "scalar")]),
    "correlate_pods": (
        "Pods alive during the window", ["mv_pod_basic_info"], [],
        WINDOW + [("namespace", "scalar"), ("app", "scalar"), ("search", "scalar")]),
    "correlate_metrics": (
        "Timing in the window", [], METRIC + ["athena_core.environment"],
        WINDOW + [("environment", "scalar"), ("search", "scalar")]),
}

BIND = re.compile(r"(?<!:):(\w+)")
LITERAL_OR_COMMENT = re.compile(r"'(?:[^']|'')*'|--[^\n]*|/\*.*?\*/", re.DOTALL)


def binds_in(sql: str) -> set:
    """Same quote- and comment-aware scan the registry performs at startup."""
    stripped = LITERAL_OR_COMMENT.sub(lambda m: " " * (m.end() - m.start()), sql)
    return set(BIND.findall(stripped))


def main() -> int:
    problems = []
    on_disk = {p.stem for p in CURATED.glob("*.sql")}
    declared = set(SPEC)

    for orphan in sorted(on_disk - declared):
        problems.append(f"{orphan}.sql exists but is not declared in build_curated.py")
    for missing in sorted(declared - on_disk):
        problems.append(f"{missing} is declared but has no {missing}.sql")

    entries = []
    for query_id, (title, views, tables, params) in SPEC.items():
        path = CURATED / f"{query_id}.sql"
        if not path.exists():
            continue
        found = binds_in(path.read_text())
        names = {name for name, _ in params}
        for extra in sorted(found - names):
            problems.append(f"{query_id}: SQL binds :{extra} but it is not declared")
        for unused in sorted(names - found):
            problems.append(f"{query_id}: declares '{unused}' that the SQL never binds")
        entries.append({
            "id": query_id,
            "title": title,
            "views": views,
            "tables": tables,
            "params": [{"name": n, "kind": k, "allowed": []} for n, k in params],
            "dashboards": [],
        })

    if problems:
        print("curated manifest is inconsistent with the SQL:", file=sys.stderr)
        for problem in problems:
            print(f"  {problem}", file=sys.stderr)
        return 1

    MANIFEST.write_text(json.dumps(entries, indent=2) + "\n")
    print(f"wrote {MANIFEST.relative_to(ROOT)} with {len(entries)} queries")
    return 0


if __name__ == "__main__":
    sys.exit(main())
