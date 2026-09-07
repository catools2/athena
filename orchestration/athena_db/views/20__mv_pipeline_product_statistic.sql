-- athena.mv_pipeline_product_statistic
-- type      : MATERIALIZED VIEW
-- dep level : 1
-- depends on: mvw_pipeline_product_statistic
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP MATERIALIZED VIEW IF EXISTS athena.mv_pipeline_product_statistic CASCADE;

CREATE MATERIALIZED VIEW athena.mv_pipeline_product_statistic
TABLESPACE pg_default
AS SELECT pipeline_name,
    pipeline_number,
    pipeline_desciption,
    version,
    environment,
    project,
    start_date,
    end_date,
    total_sucess,
    total_failure,
    total_skip,
    sucess_rate,
    failure_rate,
    skip_rate,
    total_execution,
    total_duration
   FROM athena.mvw_pipeline_product_statistic t
WITH DATA;

CREATE UNIQUE INDEX mv_pipeline_product_statistic_key_uidx
    ON athena.mv_pipeline_product_statistic
        (project, environment, pipeline_name, pipeline_number, start_date);
