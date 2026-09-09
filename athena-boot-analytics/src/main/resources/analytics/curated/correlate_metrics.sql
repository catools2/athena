-- Timing for the same window as the commits and runs above, so "did it get slower" is
-- answered against the same slice of time rather than a different one.
SELECT a.name                                                          AS action,
       count(*)                                                        AS samples,
       round(percentile_cont(0.50) WITHIN GROUP (ORDER BY m.duration)) AS p50_ms,
       round(percentile_cont(0.95) WITHIN GROUP (ORDER BY m.duration)) AS p95_ms
FROM athena_metric.metric m
JOIN athena_metric.action a ON a.id = m.action_id
WHERE m.action_time BETWEEN :timeFrom AND :timeTo
GROUP BY a.name
ORDER BY round(percentile_cont(0.95) WITHIN GROUP (ORDER BY m.duration)) DESC NULLS LAST
LIMIT 100
