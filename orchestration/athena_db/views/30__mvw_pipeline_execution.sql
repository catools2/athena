-- athena.mvw_pipeline_execution
-- type      : VIEW
-- dep level : 3
-- depends on: mv_pipeline_execution_metadata, vw_pipeline_execution_test_id, vw_product_team_scale_link
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.mvw_pipeline_execution CASCADE;

CREATE OR REPLACE VIEW athena.mvw_pipeline_execution AS
SELECT DISTINCT rank() OVER (PARTITION BY e.package_name, e.class_name, e.method_name, e.parameters
                             ORDER BY e.start_time DESC) AS start_time_rank,
                rank() OVER (PARTITION BY (COALESCE(lnk.item_key, m.item_key::character varying))
                             ORDER BY e.test_start_time DESC) AS item_key_execution_rank,
                CASE
                    WHEN e.package_name::text ~~ '%demo%.sanity%'::text THEN 'sanity'::character varying
                    WHEN e.package_name::text ~~ '%demo%.api%'::text THEN 'api'::character varying
                    WHEN e.package_name::text ~~ '%demo%.web%'::text THEN 'web'::character varying
                    WHEN e.package_name::text ~~ '%demo%.batch%'::text THEN 'batch'::character varying
                    ELSE e.package_name
                END AS functional_group,
                COALESCE(lnk.item_key, m.item_key::character varying) AS item_key,
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
                p.name AS pipeline_name,
                p.number AS pipeline_number,
                av.name AS version_name,
                COALESCE(pem.metadata, '{}'::jsonb) AS metadata
FROM athena_pipeline.execution e
LEFT JOIN athena_pipeline.pipeline p ON p.id = e.pipeline_id
LEFT JOIN athena_core.app_version av ON p.version_id = av.id
LEFT JOIN athena.vw_pipeline_execution_test_id m ON e.id = m.execution_id
LEFT JOIN athena.vw_product_team_scale_link lnk ON lnk.item_key::text = m.item_key
LEFT JOIN athena.mv_pipeline_execution_metadata pem ON pem.execution_id = e.id
WHERE p.name::text !~~* 'local%'::text;
