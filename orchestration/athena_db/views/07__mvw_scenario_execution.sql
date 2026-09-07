-- athena.mvw_scenario_execution
-- type      : VIEW
-- dep level : 0
-- depends on: (base tables only)
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.mvw_scenario_execution CASCADE;

CREATE OR REPLACE VIEW athena.mvw_scenario_execution AS
SELECT to_char(se.start_time, 'DD-Mon-YYYY HH24:MI:SS'::text) AS formatted_start_time,
       to_char(se.end_time, 'DD-Mon-YYYY HH24:MI:SS'::text) AS formatted_end_time,
       se.scenario,
       se.feature,
       TRIM(BOTH
            FROM s.name) AS btrim,
       p.number,
       se.pipeline_id
FROM athena_pipeline.scenario_execution se
LEFT JOIN athena_pipeline.pipeline p ON p.id = se.pipeline_id
LEFT JOIN athena_core.app_version av ON p.version_id = av.id
LEFT JOIN athena_core.project proj ON av.project_id = proj.id
LEFT JOIN athena_pipeline.status s ON se.status_id = s.id
WHERE proj.code::text = 'CI_RUN'::text
  AND se.feature::text ~~ '%prod'::text;
