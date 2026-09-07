-- athena.mvw_pipeline_execution_info
-- type      : VIEW
-- dep level : 6
-- depends on: mv_execution_info, mv_pipeline_execution_metadata
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.mvw_pipeline_execution_info CASCADE;

CREATE OR REPLACE VIEW athena.mvw_pipeline_execution_info AS
SELECT DISTINCT e.start_time_rank,
                e.item_key_execution_rank,
                e.functional_group,
                e.app,
                e.before_class_end_time,
                e.before_class_start_time,
                e.before_method_end_time,
                e.before_method_start_time,
                e.end_time,
                e.executor_id,
                e.id,
                e.pipeline_id,
                e.start_time,
                e.status_id,
                e.test_end_time,
                e.test_start_time,
                e.class_name,
                e.method_name,
                e.package_name,
                e.parameters,
                e.project_code,
                e.item_key,
                e.item_id,
                p.name AS pipeline_name,
                p.number AS pipeline_number,
                av.name AS version_name,
                COALESCE(pem.metadata, '{}'::jsonb) AS metadata
FROM athena.mv_execution_info e
LEFT JOIN athena_pipeline.pipeline p ON p.id = e.pipeline_id
LEFT JOIN athena_core.app_version av ON p.version_id = av.id
LEFT JOIN athena.mv_pipeline_execution_metadata pem ON pem.execution_id = e.id;
