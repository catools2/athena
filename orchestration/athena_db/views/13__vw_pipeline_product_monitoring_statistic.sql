-- athena.vw_pipeline_product_monitoring_statistic
-- type      : VIEW
-- dep level : 0
-- depends on: (base tables only)
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.vw_pipeline_product_monitoring_statistic CASCADE;

CREATE OR REPLACE VIEW athena.vw_pipeline_product_monitoring_statistic
AS SELECT DISTINCT replace(p.name::text, ' Monitoring'::text, ''::text) AS pipeline_short_name,
    "position"(p.name::text, 'Internal'::text) > 0 AS internal_service,
    p.name AS pipeline_name,
    p.number AS pipeline_number,
    p.description AS pipeline_desciption,
    v.name AS version,
    env.name AS environment,
    proj.name AS project,
    e.package_name,
    e.class_name,
    e.method_name,
    max(date_trunc('hour'::text, p.start_date) + (date_part('minute'::text, p.start_date)::integer / 15)::double precision * '00:15:00'::interval) AS start_date,
    max(date_trunc('hour'::text, p.end_date) + (date_part('minute'::text, p.end_date)::integer / 15)::double precision * '00:15:00'::interval) AS end_date,
    sum(
        CASE s.name
            WHEN 'SUCCESS'::text THEN 1
            ELSE 0
        END) AS total_sucess,
    sum(
        CASE s.name
            WHEN 'FAILURE'::text THEN 1
            ELSE 0
        END) AS total_failure,
    sum(
        CASE s.name
            WHEN 'SKIP'::text THEN 1
            ELSE 0
        END) AS total_skip,
    sum(
        CASE s.name
            WHEN 'SUCCESS'::text THEN 1
            ELSE 0
        END) * 100 / GREATEST(count(DISTINCT e.id), 1::bigint) AS sucess_rate,
    sum(
        CASE s.name
            WHEN 'FAILURE'::text THEN 1
            ELSE 0
        END) * 100 / GREATEST(count(DISTINCT e.id), 1::bigint) AS failure_rate,
    sum(
        CASE s.name
            WHEN 'SKIP'::text THEN 1
            ELSE 0
        END) * 100 / GREATEST(count(DISTINCT e.id), 1::bigint) AS skip_rate,
    count(DISTINCT e.id) AS total_execution,
    max(e.end_time) - min(e.start_time) AS total_duration
   FROM athena_pipeline.pipeline p
     JOIN athena_pipeline.execution e ON e.pipeline_id = p.id
     JOIN athena_core.environment env ON p.environment_id = env.id
     LEFT JOIN athena_core.app_version v ON p.version_id = v.id
     LEFT JOIN athena_core.project proj ON v.project_id = proj.id
     LEFT JOIN athena_pipeline.status s ON e.status_id = s.id
  WHERE proj.code::text = 'DEMO'::text AND p.end_date > (now() - '1 mon'::interval) AND p.name::text ~~ '%Monitoring%'::text AND p.end_date IS NOT NULL
  GROUP BY p.name, p.number, p.description, v.name, env.name, proj.name, e.package_name, e.class_name, e.method_name;
