# Prometheus panels

Prometheus-backed Grafana panels remain outside Athena's PostgreSQL query contract. Atlas Console
can list and run the SQL resources that read Athena views, but it does not silently translate live
Prometheus or InfluxDB panels into SQL.

A future datasource adapter can use the same browser result shape (`columns`, `rows`, `truncated`,
and `freshness`) while keeping datasource credentials outside the browser. Until that adapter
exists, unsupported panels should retain an explicit reason rather than appearing empty.
