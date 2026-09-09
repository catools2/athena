-- Daily p95 for the selected actions, so a regression shows as a step rather than a number.
SELECT date_trunc('day', m.action_time)                                 AS bucket,
       round(percentile_cont(0.95) WITHIN GROUP (ORDER BY m.duration)) AS p95_ms,
       round(percentile_cont(0.50) WITHIN GROUP (ORDER BY m.duration)) AS p50_ms,
       count(*)                                                        AS samples
FROM athena_metric.metric m
JOIN athena_metric.action a ON a.id = m.action_id
WHERE m.action_time BETWEEN :timeFrom AND :timeTo
  AND (:action IS NULL OR a.name ILIKE '%' || :action || '%')
GROUP BY date_trunc('day', m.action_time)
ORDER BY 1
