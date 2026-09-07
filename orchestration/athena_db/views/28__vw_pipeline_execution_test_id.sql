-- athena.vw_pipeline_execution_test_id
-- type      : VIEW
-- dep level : 2
-- depends on: mv_pipeline_execution_metadata
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.vw_pipeline_execution_test_id CASCADE;

CREATE OR REPLACE VIEW athena.vw_pipeline_execution_test_id AS
SELECT DISTINCT pem.execution_id,
                test_id.value AS item_key
FROM athena.mv_pipeline_execution_metadata pem
CROSS JOIN LATERAL jsonb_array_elements_text(pem.metadata -> 'Test ID'::text) test_id(value);
