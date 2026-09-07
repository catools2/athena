-- athena.mvw_ci_run_pipeline_execution
-- type      : VIEW
-- dep level : 0
-- depends on: (base tables only)
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.mvw_ci_run_pipeline_execution CASCADE;

CREATE OR REPLACE VIEW athena.mvw_ci_run_pipeline_execution AS
SELECT se.before_scenario_end_time,
       se.before_scenario_start_time,
       se.end_time,
       se.executor_id,
       se.id,
       se.pipeline_id,
       se.start_time,
       se.status_id,
       se.scenario,
       se.feature,
       se.parameters,
       p.start_date AS pipeline_start_date,
       p.end_date AS pipeline_end_date,
       p.name AS pipeline_name,
       p.number AS pipeline_number,
       p.description AS pipeline_description,
       av.name AS app_version
FROM athena_pipeline.scenario_execution se
LEFT JOIN athena_pipeline.pipeline p ON p.id = se.pipeline_id
LEFT JOIN athena_core.app_version av ON p.version_id = av.id
LEFT JOIN athena_core.project proj ON av.project_id = proj.id
WHERE proj.code::text = 'CI_RUN'::text;
