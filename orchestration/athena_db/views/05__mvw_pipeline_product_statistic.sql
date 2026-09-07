-- athena.mvw_pipeline_product_statistic
-- type      : VIEW
-- dep level : 0
-- depends on: (base tables only)
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.mvw_pipeline_product_statistic CASCADE;

CREATE OR REPLACE VIEW athena.mvw_pipeline_product_statistic
AS SELECT DISTINCT p.name AS pipeline_name,
    p.number AS pipeline_number,
    p.description AS pipeline_desciption,
    v.name AS version,
    env.name AS environment,
    proj.name AS project,
    min(p.start_date) AS start_date,
    max(p.end_date) AS end_date,
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
     LEFT JOIN athena_pipeline.execution e ON e.pipeline_id = p.id
     LEFT JOIN athena_core.environment env ON p.environment_id = env.id
     LEFT JOIN athena_core.app_version v ON p.version_id = v.id
     LEFT JOIN athena_core.project proj ON v.project_id = proj.id
     LEFT JOIN athena_pipeline.status s ON e.status_id = s.id
  WHERE proj.code::text = 'DEMO'::text AND p.end_date > (now() - '1 year'::interval year)
  GROUP BY p.name, p.number, p.description, v.name, env.name, proj.name;
