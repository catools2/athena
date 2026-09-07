-- athena.mvw_api_path_info_with_matching_pattern
-- type      : VIEW
-- dep level : 0
-- depends on: (base tables only)
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.mvw_api_path_info_with_matching_pattern CASCADE;

CREATE OR REPLACE VIEW athena.mvw_api_path_info_with_matching_pattern
AS SELECT DISTINCT pr.name AS project,
    s.name AS spec_name,
    s.title AS spec_title,
    p.first_time_seen,
    p.id,
    p.last_sync_time,
    p.method,
    p.url,
    p.title,
    p.description,
    p.parameters,
    regexp_replace(replace(p.url::text, '*'::text, ''::text), '{.*?}'::text, '.+'::text, 'g'::text) AS url_pattern,
    lower(s.name::text) ~~ 'internal%'::text AS internal
   FROM athena_openapi.api_path p
     LEFT JOIN athena_openapi.api_spec s ON p.spec_id = s.id
     LEFT JOIN athena_core.project pr ON pr.id = s.project_id;
