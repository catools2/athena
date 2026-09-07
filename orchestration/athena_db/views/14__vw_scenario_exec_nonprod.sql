-- athena.vw_scenario_exec_nonprod
-- type      : VIEW
-- dep level : 0
-- depends on: (base tables only)
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.vw_scenario_exec_nonprod CASCADE;

CREATE OR REPLACE VIEW athena.vw_scenario_exec_nonprod AS
SELECT to_char(se.start_time, 'DD-Mon-YYYY HH24:MI:SS'::text) AS formatted_start_time,
       to_char(se.end_time, 'DD-Mon-YYYY HH24:MI:SS'::text) AS formatted_end_time,
       se.start_time,
       se.end_time,
       se.scenario,
       se.feature,
       TRIM(BOTH
            FROM s.name) AS status,
       p.number AS pipeline_number,
       se.pipeline_id,
       e.name AS env
FROM athena_pipeline.scenario_execution se
LEFT JOIN athena_pipeline.pipeline p ON p.id = se.pipeline_id
LEFT JOIN athena_core.app_version av ON p.version_id = av.id
LEFT JOIN athena_core.project proj ON av.project_id = proj.id
LEFT JOIN athena_pipeline.status s ON se.status_id = s.id
LEFT JOIN athena_core.environment e ON p.environment_id = e.id
WHERE proj.code::text = 'CI_RUN'::text
ORDER BY p.start_date DESC;
