# Analytics query registry

Generated from [`../../../../../grafana`](../../../../../grafana) by
[`../../../../tools/extract_grafana.py`](../../../../tools/extract_grafana.py).

165 named, parameterised queries over the analytics views in
[`orchestration/athena_db/views`](../../../../../orchestration/athena_db/views). `queries.json` is
the manifest — id, title, the views each query reads, its parameters, and the dashboards it came
from. `queries/<id>.sql` holds the SQL.

Regenerate after changing a dashboard:

```bash
python3 athena-boot-analytics/tools/extract_grafana.py            # dry run
python3 athena-boot-analytics/tools/extract_grafana.py --write    # rewrite queries/ and queries.json
```

It rewrites the directory wholesale, so review the diff.

## Why the SQL is not verbatim Grafana

Grafana templating is not SQL. Most variables become ordinary bind parameters, but three
constructs cannot and are handled structurally.

**`$cycle_type` is an operator, not a value.** It expands to `LIKE` or `NOT LIKE` in 40 queries.
A bind parameter cannot carry an operator, so it is emitted as a `{{cycle_type}}` slot filled at
render time from the closed whitelist in the manifest. This is the one place where text is
substituted into SQL, which is exactly why the allowed values are enumerated rather than accepted
from the caller.

**`jsonb ?` collides with the JDBC parameter marker.** `teams_set ? 'x'` sends a `?` that the
driver reads as a placeholder. Rewritten to the function forms, which are the same operators:

| Grafana | Registry | Parameter |
|---|---|---|
| `col ? '$var'` | `jsonb_exists(col, :var)` | scalar |
| `col ?\| ARRAY[${var:sqlstring}]` | `jsonb_exists_any(col, :var)` | list |
| `col ? ($var)` | `jsonb_exists_any(col, :var)` | list |

The last row is a deliberate behaviour change. Every dashboard using the parenthesised form
declares `$team` as `multi: true`, so Grafana renders `? ('a','b')` — a record — and Postgres
fails with `operator does not exist: jsonb ? record`. Those panels only work today when exactly
one value is selected. "Any of these keys" is what was meant and what `?|` expresses elsewhere in
the same dashboards, so the registry uses `jsonb_exists_any` and the panels work for one value or
many.

**`ARRAY[${x}] = ARRAY['__all__']`** is Grafana's "All" sentinel. It becomes
`cardinality(:x) = 0`, so an empty list means "do not filter" rather than "match nothing".

Time macros map directly: `$__timeFrom()`/`$__timeTo()` to `:timeFrom`/`:timeTo`, and
`$__timeFilter(col)` to `col BETWEEN :timeFrom AND :timeTo`.

## Parameter kinds

| Kind | Binds as | Used by |
|---|---|---|
| `instant` | `timestamptz` | `timeFrom`, `timeTo` |
| `scalar` | `text` | `version`, `versions`, `environment`, `username`, `name`, `components`, `team` |
| `list` | `text[]` | `team`, `versions`, `reporters`, `rcas`, `affected_versions`, `username`, `name`, `components` |
| `operator` | not bound — whitelist substitution | `cycle_type` |

## Not registered

Two queries in `sanbox.json` read `athena.mv_review_candidate_test`, which is not among the 41
objects in `views/`. The extractor rejects any query naming an unknown view, so they are excluded
rather than shipped as runtime failures. Define that view and re-run to pick them up.

The 110 InfluxDB and 44 Prometheus panels are not SQL and are out of scope for this registry.

## Verifying a regeneration

Against a database with the schema and views applied:

1. Every query must `PREPARE` with its declared parameter types — this catches a translation that
   produced invalid SQL or referenced a column that does not exist.
2. For queries whose views hold data, the registry SQL and the original Grafana SQL must return
   identical rowsets, checked with both a concrete selection and the "All" case.
