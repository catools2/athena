-- Individual measurements. The bottom of the drill-down: the actual records behind a
-- percentile, so a suspicious p95 can be traced to the specific slow calls that produced it.
SELECT m.action_time,
       m.duration          AS duration_ms,
       a.name              AS action,
       a.target,
       a.type              AS action_type,
       a.command,
       e.code              AS environment,
       p.code              AS project
FROM athena_metric.metric m
JOIN athena_metric.action a ON a.id = m.action_id
LEFT JOIN athena_core.environment e ON e.id = m.environment_id
LEFT JOIN athena_core.project p ON p.id = m.project_id
WHERE m.action_time BETWEEN :timeFrom AND :timeTo
  AND a.name = :action
  AND (:target IS NULL OR a.target = :target)
  AND (:environment IS NULL OR e.code = :environment)
  AND (:project IS NULL OR p.code = :project)
  -- Slowest first: the reason to open this list is to look at the tail.
ORDER BY m.duration DESC NULLS LAST
LIMIT 500
