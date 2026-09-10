-- Compare each action's p95 in the selected window against the window immediately before it.
--
-- This is the "did this release make anything slower" question. Actions with too few samples
-- on either side are excluded rather than reported with a meaningless delta.
WITH windowed AS (
    SELECT a.name AS action,
           CASE WHEN m.action_time BETWEEN :timeFrom::timestamptz AND :timeTo::timestamptz
                THEN 'current' ELSE 'baseline' END AS period,
           m.duration
    FROM athena_metric.metric m
    JOIN athena_metric.action a ON a.id = m.action_id
    LEFT JOIN athena_core.environment e ON e.id = m.environment_id
    LEFT JOIN athena_core.project p ON p.id = m.project_id
    -- Casts are required: arithmetic between two bind parameters gives PostgreSQL nothing to
    -- infer from and it fails with "operator is not unique: unknown - unknown". Comparing a
    -- parameter to a typed column, as in the BETWEEN above, needs no such help.
    WHERE m.action_time BETWEEN
          (:timeFrom::timestamptz - (:timeTo::timestamptz - :timeFrom::timestamptz))
          AND :timeTo::timestamptz
      -- The filters apply to BOTH windows, so the comparison stays like-for-like. Scoping only
      -- the current side would report every filtered-out action as an improvement.
      AND (:environment IS NULL OR e.code = :environment)
      AND (:project IS NULL OR p.code = :project)
      AND (:actionType IS NULL OR a.type = :actionType)
      AND (:action IS NULL OR a.name ILIKE '%' || :action || '%')
),
stats AS (
    SELECT action, period,
           count(*) AS samples,
           percentile_cont(0.95) WITHIN GROUP (ORDER BY duration) AS p95
    FROM windowed
    GROUP BY action, period
)
SELECT c.action,
       round(b.p95)                                     AS baseline_p95_ms,
       round(c.p95)                                     AS current_p95_ms,
       round(c.p95 - b.p95)                             AS delta_ms,
       round((100.0 * (c.p95 - b.p95) / nullif(b.p95, 0))::numeric, 1) AS delta_pct,
       c.samples                                        AS current_samples
FROM stats c
JOIN stats b ON b.action = c.action AND b.period = 'baseline'
WHERE c.period = 'current'
  AND c.samples >= 5 AND b.samples >= 5
ORDER BY (c.p95 - b.p95) DESC NULLS LAST
LIMIT 100
