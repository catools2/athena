-- Timing distribution per action, over athena_metric.
--
-- Percentiles rather than an average: a mean hides the tail, and the tail is what users feel.
-- p95 and max are the columns that move first when something regresses.
SELECT a.name                                                          AS action,
       a.type                                                          AS action_type,
       a.target,
       count(*)                                                        AS samples,
       round(avg(m.duration))                                          AS avg_ms,
       round(percentile_cont(0.50) WITHIN GROUP (ORDER BY m.duration)) AS p50_ms,
       round(percentile_cont(0.95) WITHIN GROUP (ORDER BY m.duration)) AS p95_ms,
       max(m.duration)                                                 AS max_ms
FROM athena_metric.metric m
JOIN athena_metric.action a ON a.id = m.action_id
WHERE m.action_time BETWEEN :timeFrom AND :timeTo
  AND (:action IS NULL OR a.name ILIKE '%' || :action || '%')
GROUP BY a.name, a.type, a.target
HAVING count(*) > 0
ORDER BY round(percentile_cont(0.95) WITHIN GROUP (ORDER BY m.duration)) DESC NULLS LAST
LIMIT 200
