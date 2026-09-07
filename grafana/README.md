# Grafana dashboards

Reporting dashboards over the analytics views in
[`../orchestration/athena_db/views`](../orchestration/athena_db/views). Import them through
**Dashboards → New → Import** and paste the JSON, or point Grafana's file provisioner at this
directory.

Two variants of the same set:

| Directory | Intended for |
|-----------|--------------|
| `onprem/` | A self-hosted Grafana. The more actively maintained of the two. |
| `aws/`    | A managed Grafana. Same dashboards, some panels trimmed. |

## Before they show anything

**Pick the datasources on import.** Every panel resolves its datasource through a dashboard
variable rather than a hard-coded uid, so the import screen asks for them:

| Variable | Type | Points at |
|----------|------|-----------|
| `athena_datasource` | PostgreSQL | The athena database, with the `athena` schema views applied |
| `influx_datasource` | InfluxDB | Pipeline/JVM telemetry, for the batch and perf dashboards |
| `k8s_datasource`, `sa_datasource` | Prometheus | Cluster metrics, on the healthcheck dashboards |

**Apply the views first.** The SQL panels read `athena.mv_*` / `athena.vw_*`; without
[`../orchestration/athena_db/apply_views.sh`](../orchestration/athena_db/apply_views.sh) every
Postgres panel returns an error. The materialized views also need refreshing on a schedule, or
the numbers stay frozen at whenever they were built.

**Expect to adapt the filters.** Names and literals here are generic (`DEMO` project code,
`demo-t%` test keys, `com.example.automation.demo.*` package prefixes, `%Automated%` /
`%Golden%` / `%Playwright%` cycle-name matching). Those encode one team's Jira and pipeline
conventions, not universal ones — panels will return empty until they match your own. The same
caveat applies to the view definitions themselves; see the
[views README](../orchestration/athena_db/views/README.md).

## Known gaps

`sanbox.json` is a scratch dashboard and queries `athena.mv_review_candidate_test`, which is not
among the 41 objects in `views/`. Those four panels will error until you define that view
yourself. The other 27 dashboards reference only views that exist.
