-- athena.mv_product_sql_metrics
-- type      : MATERIALIZED VIEW
-- dep level : 1
-- depends on: mvw_product_sql_metrics
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP MATERIALIZED VIEW IF EXISTS athena.mv_product_sql_metrics CASCADE;

CREATE MATERIALIZED VIEW athena.mv_product_sql_metrics
TABLESPACE pg_default
AS SELECT command,
    parameter,
    action_time,
    total_duration,
    target,
    environment,
    project,
    total
   FROM athena.mvw_product_sql_metrics t
WITH DATA;
