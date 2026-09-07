-- athena.mv_pipeline_execution_metadata
-- type      : MATERIALIZED VIEW
-- dep level : 1
-- depends on: mvw_pipeline_execution_metadata
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP MATERIALIZED VIEW IF EXISTS athena.mv_pipeline_execution_metadata CASCADE;

CREATE MATERIALIZED VIEW athena.mv_pipeline_execution_metadata
TABLESPACE pg_default
AS SELECT execution_id,
    metadata
   FROM athena.mvw_pipeline_execution_metadata t
WITH DATA;
