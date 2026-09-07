-- athena.mv_regression_automation_to_manual_ratio
-- type      : MATERIALIZED VIEW
-- dep level : 1
-- depends on: mvw_regression_automation_to_manual_ratio
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP MATERIALIZED VIEW IF EXISTS athena.mv_regression_automation_to_manual_ratio CASCADE;

CREATE MATERIALIZED VIEW athena.mv_regression_automation_to_manual_ratio
TABLESPACE pg_default
AS SELECT project_code,
    version,
    automated,
    playwright,
    sme,
    manual,
    golden
   FROM athena.mvw_regression_automation_to_manual_ratio t
WITH DATA;
