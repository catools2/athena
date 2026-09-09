SELECT 
  to_char(e.end_time AT TIME ZONE 'UTC', 'DD Mon YYYY HH24:MI:SS') AS last_execution
FROM athena_pipeline.execution e
WHERE e.pipeline_id = (
    SELECT p.id
    FROM athena_pipeline.pipeline p
    JOIN athena_core.environment env ON p.environment_id = env.id
    LEFT JOIN athena_core.app_version v ON p.version_id = v.id
    LEFT JOIN athena_core.project proj ON v.project_id = proj.id
    WHERE proj.code::text = 'DEMO'::text
      AND p.start_date > (now() - '1 mon'::interval)
      AND p.name::text ~~ 'Baseline Tests - MFA Service'::text
      AND p.start_date IS NOT NULL
    ORDER BY p.start_date DESC
    LIMIT 1
)
ORDER BY e.end_time DESC
LIMIT 1;
