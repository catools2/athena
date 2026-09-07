-- athena.mv_pod_basic_info
-- type      : MATERIALIZED VIEW
-- dep level : 1
-- depends on: mvw_pod_basic_info
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP MATERIALIZED VIEW IF EXISTS athena.mv_pod_basic_info CASCADE;

CREATE MATERIALIZED VIEW athena.mv_pod_basic_info
TABLESPACE pg_default
AS SELECT pod_rank,
    app,
    version,
    namespace,
    created_at,
    last_sync,
    project_id,
    status_id,
    phase,
    name
   FROM athena.mvw_pod_basic_info t
WITH DATA;

CREATE UNIQUE INDEX mv_pod_basic_info_row_uidx
    ON athena.mv_pod_basic_info
        (pod_rank, app, version, namespace, created_at, last_sync, project_id, status_id, phase, name);
