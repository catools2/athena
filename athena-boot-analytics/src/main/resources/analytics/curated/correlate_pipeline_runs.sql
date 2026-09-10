-- Pipeline executions in the window, with pass/fail counts per pipeline run.
SELECT p.name        AS pipeline,
       p.number      AS run,
       v.name        AS version,
       env.code      AS environment,
       p.start_date,
       p.end_date,
       count(e.id)                                           AS executions,
       count(*) FILTER (WHERE s.name = 'SUCCESS')            AS passed,
       count(*) FILTER (WHERE s.name <> 'SUCCESS')           AS failed,
       round(extract(epoch FROM (p.end_date - p.start_date))) AS duration_sec
FROM athena_pipeline.pipeline p
LEFT JOIN athena_pipeline.execution e ON e.pipeline_id = p.id
LEFT JOIN athena_pipeline.status s ON s.id = e.status_id
LEFT JOIN athena_core.app_version v ON v.id = p.version_id
LEFT JOIN athena_core.environment env ON env.id = p.environment_id
WHERE p.start_date BETWEEN :timeFrom AND :timeTo
  AND (:pipeline IS NULL OR p.name = :pipeline)
  AND (:version IS NULL OR v.name = :version)
  AND (:environment IS NULL OR env.code = :environment)
GROUP BY p.name, p.number, v.name, env.code, p.start_date, p.end_date
ORDER BY p.start_date DESC
LIMIT 200
