WITH latest_pipeline AS (
  SELECT p.id
  FROM athena_pipeline.pipeline p
  JOIN athena_core.environment env ON p.environment_id = env.id
  LEFT JOIN athena_core.app_version v ON p.version_id = v.id
  LEFT JOIN athena_core.project proj ON v.project_id = proj.id
  WHERE proj.code::text = 'DEMO'::text
      AND p.start_date between :timeFrom and :timeTo
      AND p.name = ANY(:components)
      AND p.start_date IS NOT NULL
  ORDER BY p.start_date DESC
  LIMIT 1
)
SELECT
    COUNT(*) AS count,
    s.name AS status
FROM (SELECT DISTINCT ON (e.method_name)
        e.method_name,
        s.name
    FROM athena_pipeline.execution e
    JOIN athena_pipeline.status s ON e.status_id = s.id
    WHERE e.pipeline_id = (SELECT id FROM latest_pipeline)
    ORDER BY e.method_name, e.end_time DESC
) sub
JOIN athena_pipeline.status s ON sub.name = s.name
GROUP BY s.name
ORDER BY s.name;
