-- athena.mvw_execution_info
-- type      : VIEW
-- dep level : 4
-- depends on: mv_items, vw_pipeline_execution_test_id
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.mvw_execution_info CASCADE;

CREATE OR REPLACE VIEW athena.mvw_execution_info AS
SELECT DISTINCT rank() OVER (PARTITION BY e.package_name, e.class_name, e.method_name, e.parameters
                             ORDER BY e.start_time DESC) AS start_time_rank,
                rank() OVER (PARTITION BY i.item_key
                             ORDER BY e.test_start_time DESC) AS item_key_execution_rank,
                CASE
                    WHEN e.package_name::text ~~ 'com.example.legacy.rest%'::text THEN 'legacy'::character varying
                    WHEN e.package_name::text ~~ '%demo%.sanity%'::text THEN 'sanity'::character varying
                    WHEN e.package_name::text ~~ '%demo%.api%'::text THEN 'api'::character varying
                    WHEN e.package_name::text ~~ '%demo%.web%'::text THEN 'web'::character varying
                    WHEN e.package_name::text ~~ '%demo%.aws%'::text THEN 'aws'::character varying
                    WHEN e.package_name::text ~~ '%demo%.batch%'::text THEN 'batch'::character varying
                    ELSE e.package_name
                END AS functional_group,
                CASE
                    WHEN e.package_name::text ~~ 'com.example.legacy.rest%'::text THEN rg_legacy.legacy_group::character varying
                    WHEN e.package_name::text ~~ '%demo%.sanity%'::text THEN ''::character varying
                    WHEN e.package_name::text ~~ '%demo%.api%'::text THEN rg_api.api_group::character varying
                    WHEN e.package_name::text ~~ '%demo%.web%'::text THEN rg_web.web_group::character varying
                    WHEN e.package_name::text ~~ '%demo%.aws%'::text THEN rg_aws.aws_group::character varying
                    WHEN e.package_name::text ~~ '%demo%.batch%'::text THEN rg_batch.batch_group::character varying
                    ELSE e.package_name
                END AS app,
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
                i.project_code,
                i.item_key,
                i.item_id
FROM athena_pipeline.execution e
LEFT JOIN athena.vw_pipeline_execution_test_id m ON e.id = m.execution_id
LEFT JOIN athena.mv_items i ON i.item_key::text = m.item_key
LEFT JOIN LATERAL
  (SELECT (regexp_matches(e.package_name::text, 'com.example.legacy.rest\.([^.]+)'::text))[1] AS legacy_group) rg_legacy ON true
LEFT JOIN LATERAL
  (SELECT (regexp_matches(e.package_name::text,
    'com.example.automation.demo.api\.(internal|external)\.([^.]+)'::text))[2] AS api_group) rg_api ON true
LEFT JOIN LATERAL
  (SELECT (regexp_matches(e.package_name::text,
    'com.example.automation.demo.web\.testcases\.([^.]+)'::text))[1] AS web_group) rg_web ON true
LEFT JOIN LATERAL
  (SELECT (regexp_matches(e.package_name::text,
    'com.example.automation.demo.batch\.([^.]+)'::text))[1] AS batch_group) rg_batch ON true
LEFT JOIN LATERAL
  (SELECT (regexp_matches(e.package_name::text,
    'com.example.automation.demo.aws\.([^.]+)'::text))[1] AS aws_group) rg_aws ON true;
