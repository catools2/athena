# Analytics query execution

Atlas Console owns the generated dashboard query catalog under
`atlas-console/src/shared/analytics/queries/`. Each resource is named
`<group>__<query>.sql` and is accompanied by the console manifest. The console sends the SQL to
this service through `POST /queries/execute`.

Athena validates every request as one read-only `SELECT` or `WITH` statement, binds named
parameters, applies the row cap, and returns rows, columns, and freshness metadata. There is no
registered-query service or second generated SQL copy in Athena.

## Console catalog generation

Run the generator from the Athena repository to refresh the console-owned catalog:

```bash
python3 athena-boot-analytics/tools/extract_grafana.py
python3 athena-boot-analytics/tools/extract_grafana.py --write
python3 athena-boot-analytics/tools/selftest.py
```

The generator writes the manifest and SQL files under the Atlas Console source tree. Review the
diff and keep one file per distinct SQL body.
