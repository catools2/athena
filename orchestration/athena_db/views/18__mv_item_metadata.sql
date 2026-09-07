-- athena.mv_item_metadata
-- type      : MATERIALIZED VIEW
-- dep level : 1
-- depends on: mvw_item_metadata
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP MATERIALIZED VIEW IF EXISTS athena.mv_item_metadata CASCADE;

CREATE MATERIALIZED VIEW athena.mv_item_metadata
TABLESPACE pg_default
AS SELECT item_id,
    metadata
   FROM athena.mvw_item_metadata t
WITH DATA;
