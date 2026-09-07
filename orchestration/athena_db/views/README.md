# athena analytics views

Reporting views over the athena service schemas, feeding the Grafana dashboards
in [`../../../grafana`](../../../grafana).

**These files are the source of truth. Edit them directly.** They were originally
produced by [`../generate_views.py`](../generate_views.py) from a pgAdmin dump of a
live `athena` schema, and that script is still here for re-importing a whole schema
in one go — but it overwrites this directory wholesale, so it is an import tool
rather than a build step. Nothing regenerates these files on a normal change.

Names and literals here are deliberately generic (`product_`, `team`, `DEMO`). Several
views encode reporting conventions rather than universal truths — the status
vocabularies in `mvw_items`, the package-name prefixes in `mvw_pipeline_execution`,
the id mapping in `vw_core_normalized_users` — so expect to adapt them to your own
Jira and pipeline naming before the numbers mean anything.

## Layout

`00__schema.sql` creates the `athena` schema, then 41 objects (23 views,
18 materialized views) numbered `NN__<name>.sql` in
**dependency order** across 8 levels. Applying them in ascending numeric order
is always safe; each file's header lists what it depends on.

| Level | Contents |
|-------|----------|
| 0 | 15 base wrapper views (`mvw_*`, `vw_*`) reading only service tables |
| 1 | 11 materialized views over those wrappers |
| 2–3 | `mvw_items` → `mv_items`, `vw_pipeline_execution_test_id`, `mvw_pipeline_execution` |
| 4–5 | execution / transition / test-cycle wrappers and their MVs |
| 6–7 | `mvw_inventory_trend`, `mvw_pipeline_execution_info` and their MVs |

## Applying

```bash
export PGHOST=... PGDATABASE=athena PGUSER=postgres
../apply_views.sh          # everything, in order
../apply_views.sh 27       # just 27__mvw_items.sql
../apply_views.sh 27 41    # 27 through 41
```

Every file `DROP ... CASCADE`s before recreating, so applying a low-numbered
file alone drops everything downstream of it. Apply from that file to the end
if you are unsure.

## Flyway

There is no separate aggregate file: applying `views/*.sql` in filename order
already rebuilds the whole graph, because each file `DROP ... CASCADE`s its own
object immediately before recreating it. So a repeatable migration is just a
concatenation:

```bash
cat orchestration/athena_db/views/*.sql > R__athena_views.sql
```

Nothing is wired into a service's `db/migration` location yet. These views span
the `athena_core`, `athena_tms`, `athena_pipeline`, `athena_kube`,
`athena_openapi` and `athena_metric` schemas, so whichever Flyway run adopts
them must execute after every service has migrated its own schema.

Recreating the materialized views repopulates them (`WITH DATA`);
`mv_items` and `mv_item_metadata` dominate that cost.

## Conventions

Each object appears twice: a `mvw_`/`vw_` wrapper view holding the query, and a
matching `mv_` materialized view that snapshots it. Splitting them keeps the query
readable and reviewable on its own, and lets a refresh be scheduled independently of
the definition. Refresh the `mv_*` objects on whatever cadence your reporting needs;
`mv_items` and `mv_item_metadata` dominate that cost.

`../add_view_indexes.sql` adds the indexes the dashboards' filters rely on, and
several `mv_*` files create a unique index of their own so the view can be refreshed
`CONCURRENTLY` without taking a lock that blocks readers.
