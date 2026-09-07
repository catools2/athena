-- athena.mv_pipeline_execution_info
-- type      : MATERIALIZED VIEW
-- dep level : 7
-- depends on: mvw_pipeline_execution_info
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP MATERIALIZED VIEW IF EXISTS athena.mv_pipeline_execution_info CASCADE;

CREATE MATERIALIZED VIEW athena.mv_pipeline_execution_info
TABLESPACE pg_default
AS SELECT start_time_rank,
    item_key_execution_rank,
    functional_group,
    app,
    before_class_end_time,
    before_class_start_time,
    before_method_end_time,
    before_method_start_time,
    end_time,
    executor_id,
    id,
    pipeline_id,
    start_time,
    status_id,
    test_end_time,
    test_start_time,
    class_name,
    method_name,
    package_name,
    parameters,
    project_code,
    item_key,
    item_id,
    pipeline_name,
    pipeline_number,
    version_name,
    metadata
   FROM athena.mvw_pipeline_execution_info t
WITH DATA;
