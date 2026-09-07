-- athena.mv_product_team_scale_link
-- type      : MATERIALIZED VIEW
-- dep level : 1
-- depends on: mvw_product_team_scale_link
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP MATERIALIZED VIEW IF EXISTS athena.mv_product_team_scale_link CASCADE;

CREATE MATERIALIZED VIEW athena.mv_product_team_scale_link
TABLESPACE pg_default
AS SELECT item_id,
    scale_key
   FROM athena.mvw_product_team_scale_link t
WITH DATA;

CREATE UNIQUE INDEX mv_product_team_scale_link_key_uidx
    ON athena.mv_product_team_scale_link (item_id, scale_key);
