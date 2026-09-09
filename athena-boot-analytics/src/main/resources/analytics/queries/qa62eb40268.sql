-- Build daily buckets for the dashboard range
WITH buckets AS (
  SELECT generate_series(
    date_trunc('hour', :timeFrom::timestamp),
    date_trunc('hour', :timeTo::timestamp),
    interval '1 hour'
  ) AS hour
),
-- Aggregate "rate" per class per day (you currently SUM, keeping that for shape)
hourly AS (
  SELECT
    date_trunc('hour', start_date)          AS hour,
    class_name                             AS metric,
    SUM(sucess_rate)::double precision     AS executions   -- note: this is a rate being summed
  FROM athena.vw_pipeline_product_monitoring_statistic
  WHERE start_date BETWEEN :timeFrom AND :timeTo and environment = :environment
  GROUP BY 1, 2
),
classes AS (
  SELECT DISTINCT metric FROM hourly
),
grid AS (
  SELECT b.hour, c.metric
  FROM buckets b
  CROSS JOIN classes c
),
filled AS (
  SELECT
    g.hour,
    g.metric,
    COALESCE(d.executions, 0.0)::double precision AS executions
  FROM grid g
  LEFT JOIN hourly d
    ON d.hour = g.hour AND d.metric = g.metric
),
cum AS (
  SELECT
    hour AS "time",
    metric,
    SUM(executions) OVER (
      PARTITION BY metric
      ORDER BY hour
      ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    )::double precision AS cum_value
  FROM filled
)
SELECT
  "time",
  metric,
  LEAST(cum_value, 100.0)::double precision AS value
FROM cum
ORDER BY "time", metric
