-- athena.mv_api_path_info_with_matching_pattern
-- type      : MATERIALIZED VIEW
-- dep level : 1
-- depends on: mvw_api_path_info_with_matching_pattern
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP MATERIALIZED VIEW IF EXISTS athena.mv_api_path_info_with_matching_pattern CASCADE;

CREATE MATERIALIZED VIEW athena.mv_api_path_info_with_matching_pattern
TABLESPACE pg_default
AS SELECT project,
    spec_name,
    spec_title,
    first_time_seen,
    id,
    last_sync_time,
    method,
    url,
    title,
    description,
    parameters,
    url_pattern,
    internal
   FROM athena.mvw_api_path_info_with_matching_pattern t
WITH DATA;

CREATE UNIQUE INDEX mv_api_path_info_matching_id_uidx
    ON athena.mv_api_path_info_with_matching_pattern (id);
