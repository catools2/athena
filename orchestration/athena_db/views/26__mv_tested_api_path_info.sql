-- athena.mv_tested_api_path_info
-- type      : MATERIALIZED VIEW
-- dep level : 1
-- depends on: mvw_tested_api_path_info
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP MATERIALIZED VIEW IF EXISTS athena.mv_tested_api_path_info CASCADE;

CREATE MATERIALIZED VIEW athena.mv_tested_api_path_info
TABLESPACE pg_default
AS SELECT method,
    url,
    internal
   FROM athena.mvw_tested_api_path_info t
WITH DATA;

CREATE UNIQUE INDEX mv_tested_api_path_info_key_uidx
    ON athena.mv_tested_api_path_info (method, url, internal);
