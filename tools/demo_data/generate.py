#!/usr/bin/env python3
"""Load a coherent demo dataset into Athena through the gateway's REST API.

    python3 generate.py --host http://localhost:8080 --profile demo --refresh

Posting through the API rather than into the database is the slower path and the right one: it
exercises the same validation, mapping and foreign-key resolution real ingestion uses, so the
data cannot drift from what the services would actually accept.
"""

from __future__ import annotations

import argparse
import logging
import sys
import time
from datetime import datetime, timezone

import conventions as C
import domains
from client import AthenaClient
from narrative import PROFILES, build_world

log = logging.getLogger("demo_data")

# Ordered because the services resolve foreign keys by name and refuse to create what is missing.
STEPS = [
    ("core", "core entities", domains.load_core),
    ("tms", "tms vocabulary", domains.load_tms_reference),
    ("tms", "test items", domains.load_items),
    ("tms", "test cycles", domains.load_cycles),
    ("git", "repositories", domains.load_repositories),
    ("git", "commits", domains.load_commits),
    ("pipeline", "pipeline statuses", domains.load_pipeline_statuses),
    ("pipeline", "pipelines and executions", domains.load_pipelines),
    ("kube", "pods", domains.load_pods),
    ("metric", "metric samples", domains.load_metrics),
]


def refresh_views(args: argparse.Namespace) -> bool:
    """Refresh the materialized views.

    Not optional in practice. Every mv_* is a snapshot, so a load that skips this leaves every
    panel empty and looks exactly like a broken dashboard rather than like stale data.
    """
    try:
        import psycopg
    except ImportError:
        log.error("psycopg is not installed - run: pip install 'psycopg[binary]'")
        log.error("or refresh by hand:  %s", C.REFRESH_SQL)
        return False

    dsn = (f"host={args.db_host} port={args.db_port} dbname={args.db_name} "
           f"user={args.db_user} password={args.db_password}")
    log.info("refreshing materialized views (this walks the dependency order and can take a while)")
    try:
        with psycopg.connect(dsn, autocommit=True) as conn:
            with conn.cursor() as cur:
                cur.execute(C.REFRESH_SQL)
                for row in cur.fetchall():
                    log.info("      %s", " ".join(str(v) for v in row))
        return True
    except Exception as exc:  # noqa: BLE001 - the message matters more than the type
        log.error("view refresh failed: %s", exc)
        log.error("refresh by hand with:  %s", C.REFRESH_SQL)
        return False


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__,
                                     formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("--host", default="http://localhost:8080", help="gateway base URL")
    parser.add_argument("--profile", choices=sorted(PROFILES), default="demo")
    parser.add_argument("--seed", type=int, default=20260912,
                        help="fixes every generated value and natural key")
    parser.add_argument("--end-date", default=None,
                        help="ISO date the window ends on (default: today)")
    parser.add_argument("--months", type=int, default=None, help="override the profile's window")
    parser.add_argument("--domains", default=None,
                        help="comma-separated subset, e.g. git,pipeline,kube")
    parser.add_argument("--workers", type=int, default=16)
    parser.add_argument("--dry-run", action="store_true",
                        help="build every payload and report the counts, send nothing")
    parser.add_argument("--refresh", action="store_true",
                        help="refresh the materialized views when the load finishes")
    parser.add_argument("--db-host", default="localhost")
    parser.add_argument("--db-port", default="5432")
    parser.add_argument("--db-name", default="athena")
    parser.add_argument("--db-user", default="athena")
    parser.add_argument("--db-password", default="athena")
    parser.add_argument("-v", "--verbose", action="store_true")
    args = parser.parse_args()

    logging.basicConfig(level=logging.DEBUG if args.verbose else logging.INFO,
                        format="%(message)s")

    profile = PROFILES[args.profile]
    if args.months:
        profile = type(profile)(**{**profile.__dict__, "months": args.months})

    end = (datetime.fromisoformat(args.end_date).replace(tzinfo=timezone.utc)
           if args.end_date else datetime.now(timezone.utc))
    world = build_world(profile, args.seed, end)

    client = AthenaClient(args.host, workers=args.workers, dry_run=args.dry_run)
    if not args.dry_run and not client.healthy():
        log.error("cannot reach the analytics API at %s - is the gateway up?", args.host)
        return 1

    wanted = set(args.domains.split(",")) if args.domains else None
    steps = [s for s in STEPS if wanted is None or s[0] in wanted]

    log.info("profile %s | seed %d | %s -> %s | %d sprints",
             profile.name, args.seed, world.start.date(), world.end.date(), len(world.sprints))
    log.info("target %s -> %s%s", args.host,
             ", ".join(sorted({s[0] for s in steps})),
             "  (dry run)" if args.dry_run else "")

    started = time.monotonic()
    failures = 0
    for _, label, loader in steps:
        step_started = time.monotonic()
        log.info("  %s", label)
        result = loader(client, world)
        # load_pipelines loads two related things and reports both.
        results = result if isinstance(result, tuple) else (result,)
        for stats in results:
            failures += stats.failed
            log.info("      %s  (%.1fs)", stats, time.monotonic() - step_started)

    log.info("done in %.0fs", time.monotonic() - started)

    if failures:
        log.warning("%d requests failed - the dataset is incomplete", failures)

    if args.refresh and not args.dry_run:
        if not refresh_views(args):
            return 1
    elif not args.dry_run:
        log.warning("materialized views NOT refreshed; the UI will show nothing until they are.")
        log.warning("re-run with --refresh, or execute:  %s", C.REFRESH_SQL)

    return 1 if failures else 0


if __name__ == "__main__":
    sys.exit(main())
