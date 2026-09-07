-- athena.mv_scenario_execution
-- type      : MATERIALIZED VIEW
-- dep level : 1
-- depends on: mvw_scenario_execution
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP MATERIALIZED VIEW IF EXISTS athena.mv_scenario_execution CASCADE;

CREATE MATERIALIZED VIEW athena.mv_scenario_execution
TABLESPACE pg_default
AS SELECT formatted_start_time,
    formatted_end_time,
    scenario,
    feature,
    btrim,
    number,
    pipeline_id
   FROM athena.mvw_scenario_execution
WITH DATA;
