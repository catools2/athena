-- athena.mvw_product_sql_metrics
-- type      : VIEW
-- dep level : 0
-- depends on: (base tables only)
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.mvw_product_sql_metrics CASCADE;

CREATE OR REPLACE VIEW athena.mvw_product_sql_metrics AS
SELECT a.command,
       a.parameter,
       date_trunc('hour'::text, m.action_time) AS action_time,
       sum(m.duration) AS total_duration,
       a.target,
       e.name AS environment,
       p.name AS project,
       count(*) AS total
FROM athena_metric.metric m
JOIN athena_metric.action a ON a.id = m.action_id
JOIN athena_core.environment e ON e.id = m.environment_id
JOIN athena_core.project p ON p.id = m.project_id
WHERE a.category::text = 'sql'::text
  AND length(a.target::text) < 20
GROUP BY (date_trunc('hour'::text, m.action_time)), a.target,
                                                    a.command,
                                                    a.parameter,
                                                    e.name,
                                                    p.name;
