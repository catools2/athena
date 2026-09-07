-- athena.mvw_pipeline_execution_metadata
-- type      : VIEW
-- dep level : 0
-- depends on: (base tables only)
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.mvw_pipeline_execution_metadata CASCADE;

CREATE OR REPLACE VIEW athena.mvw_pipeline_execution_metadata
AS WITH metadata_by_name AS (
         SELECT emm.execution_id,
            em.name,
            jsonb_agg(DISTINCT em.value ORDER BY em.value) AS value_set
           FROM athena_pipeline.execution_metadata em
             JOIN athena_pipeline.execution_metadata_mid emm ON emm.metadata_id = em.id
          GROUP BY emm.execution_id, em.name
        )
 SELECT execution_id,
    jsonb_object_agg(name, value_set) AS metadata
   FROM metadata_by_name
  GROUP BY execution_id;
