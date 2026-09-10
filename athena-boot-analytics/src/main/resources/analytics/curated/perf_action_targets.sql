-- One action broken down by target. The second level of the drill-down: which endpoint,
-- page or resource inside this action is actually slow.
SELECT a.target,
       a.type                                                          AS action_type,
       count(*)                                                        AS samples,
       round(percentile_cont(0.50) WITHIN GROUP (ORDER BY m.duration)) AS p50_ms,
       round(percentile_cont(0.95) WITHIN GROUP (ORDER BY m.duration)) AS p95_ms,
       max(m.duration)                                                 AS max_ms,
       min(m.action_time)                                              AS first_seen,
       max(m.action_time)                                              AS last_seen
FROM athena_metric.metric m
JOIN athena_metric.action a ON a.id = m.action_id
LEFT JOIN athena_core.environment e ON e.id = m.environment_id
LEFT JOIN athena_core.project p ON p.id = m.project_id
WHERE m.action_time BETWEEN :timeFrom AND :timeTo
  AND a.name = :action
  AND (:environment IS NULL OR e.code = :environment)
  AND (:project IS NULL OR p.code = :project)
GROUP BY a.target, a.type
ORDER BY round(percentile_cont(0.95) WITHIN GROUP (ORDER BY m.duration)) DESC NULLS LAST
