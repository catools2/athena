-- Hourly percentiles for one action, optionally narrowed to a single target.
--
-- Hourly rather than daily: at this level the question is "when did it change", and a daily
-- bucket hides a regression that landed mid-afternoon.
SELECT date_trunc('hour', m.action_time)                               AS bucket,
       round(percentile_cont(0.50) WITHIN GROUP (ORDER BY m.duration)) AS p50_ms,
       round(percentile_cont(0.95) WITHIN GROUP (ORDER BY m.duration)) AS p95_ms,
       max(m.duration)                                                 AS max_ms,
       count(*)                                                        AS samples
FROM athena_metric.metric m
JOIN athena_metric.action a ON a.id = m.action_id
LEFT JOIN athena_core.environment e ON e.id = m.environment_id
LEFT JOIN athena_core.project p ON p.id = m.project_id
WHERE m.action_time BETWEEN :timeFrom AND :timeTo
  AND a.name = :action
  AND (:target IS NULL OR a.target = :target)
  AND (:environment IS NULL OR e.code = :environment)
  AND (:project IS NULL OR p.code = :project)
GROUP BY date_trunc('hour', m.action_time)
ORDER BY 1
