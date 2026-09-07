-- athena.mv_ci_run_pipeline_execution
-- type      : MATERIALIZED VIEW
-- dep level : 1
-- depends on: mvw_ci_run_pipeline_execution
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP MATERIALIZED VIEW IF EXISTS athena.mv_ci_run_pipeline_execution CASCADE;

CREATE MATERIALIZED VIEW athena.mv_ci_run_pipeline_execution
TABLESPACE pg_default
AS SELECT before_scenario_end_time,
    before_scenario_start_time,
    end_time,
    executor_id,
    id,
    pipeline_id,
    start_time,
    status_id,
    scenario,
    feature,
    parameters,
    pipeline_start_date,
    pipeline_end_date,
    pipeline_name,
    pipeline_number,
    pipeline_description,
    app_version
   FROM athena.mvw_ci_run_pipeline_execution t
WITH DATA;
