-- Headline numbers for the window. One row, read as tiles rather than a chart.
SELECT count(*)                                                        AS samples,
       count(DISTINCT a.name)                                          AS actions,
       count(DISTINCT a.target)                                        AS targets,
       round(percentile_cont(0.50) WITHIN GROUP (ORDER BY m.duration)) AS p50_ms,
       round(percentile_cont(0.95) WITHIN GROUP (ORDER BY m.duration)) AS p95_ms,
       round(percentile_cont(0.99) WITHIN GROUP (ORDER BY m.duration)) AS p99_ms,
       max(m.duration)                                                 AS max_ms
FROM athena_metric.metric m
JOIN athena_metric.action a ON a.id = m.action_id
LEFT JOIN athena_core.environment e ON e.id = m.environment_id
LEFT JOIN athena_core.project p ON p.id = m.project_id
WHERE m.action_time BETWEEN :timeFrom AND :timeTo
  AND (:environment IS NULL OR e.code = :environment)
  AND (:project IS NULL OR p.code = :project)
  AND (:actionType IS NULL OR a.type = :actionType)
