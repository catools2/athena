-- Duration distribution for one action, in fixed buckets.
--
-- The shape matters as much as the percentiles: a wide flat spread and a tight cluster with a
-- few far outliers have the same p95 and completely different causes.
--
-- The two CTEs must carry identical predicates. bounds sets the bucket width from the maximum,
-- so a filter applied to only one of them would bucket the filtered rows against an unfiltered
-- scale and quietly squash the histogram into its left-hand columns.
WITH bounds AS (
    SELECT max(m.duration) AS max_ms
    FROM athena_metric.metric m
    JOIN athena_metric.action a ON a.id = m.action_id
    LEFT JOIN athena_core.environment e ON e.id = m.environment_id
    LEFT JOIN athena_core.project p ON p.id = m.project_id
    WHERE m.action_time BETWEEN :timeFrom AND :timeTo
      AND a.name = :action
      AND (:target IS NULL OR a.target = :target)
      AND (:environment IS NULL OR e.code = :environment)
      AND (:project IS NULL OR p.code = :project)
),
bucketed AS (
    SELECT width_bucket(m.duration, 0, greatest(b.max_ms, 1), 20) AS bucket,
           b.max_ms
    FROM athena_metric.metric m
    JOIN athena_metric.action a ON a.id = m.action_id
    LEFT JOIN athena_core.environment e ON e.id = m.environment_id
    LEFT JOIN athena_core.project p ON p.id = m.project_id
    CROSS JOIN bounds b
    WHERE m.action_time BETWEEN :timeFrom AND :timeTo
      AND a.name = :action
      AND (:target IS NULL OR a.target = :target)
      AND (:environment IS NULL OR e.code = :environment)
      AND (:project IS NULL OR p.code = :project)
)
SELECT round((bucket - 1) * max_ms / 20.0)::bigint || '-' || round(bucket * max_ms / 20.0)::bigint AS range_ms,
       round((bucket - 1) * max_ms / 20.0)::bigint                                                 AS lower_ms,
       count(*)                                                                                    AS samples
FROM bucketed
GROUP BY bucket, max_ms
ORDER BY lower_ms
