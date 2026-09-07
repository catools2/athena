-- athena.mvw_tested_api_path_info
-- type      : VIEW
-- dep level : 0
-- depends on: (base tables only)
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.mvw_tested_api_path_info CASCADE;

CREATE OR REPLACE VIEW athena.mvw_tested_api_path_info
AS SELECT DISTINCT value::jsonb ->> 'm'::text AS method,
    regexp_replace(value::jsonb ->> 'p'::text, '\d+'::text, '----'::text, 'g'::text) AS url,
    (value::jsonb -> 'i'::text)::boolean AS internal
   FROM athena_pipeline.execution_metadata
  WHERE name::text = 'API_PATH_INFO'::text;
