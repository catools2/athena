-- The rows behind a mark. Every optional filter corresponds to one thing a reader can click on
-- the quality page - a day on the activity chart, a segment of a cycle's bar, a bucket in the
-- failure profile - so one query backs every detail dialog instead of one query per chart.
SELECT t.item_key,
       t.name,
       t.cycle_short_name,
       t.version,
       t.item_priority,
       t.item_type,
       t.execution_status,
       t.executed_on,
       t.executor
FROM athena.mv_test_cycle_statistics t
WHERE t.item_execution_rank = 1
  AND t.executed_on BETWEEN :timeFrom AND :timeTo
  AND (:cycle IS NULL OR t.cycle_code = :cycle)
  AND (:status IS NULL OR t.execution_status = :status)
  AND (:priority IS NULL OR t.item_priority = :priority)
  AND (:itemType IS NULL OR t.item_type = :itemType)
  AND (:executor IS NULL OR t.executor = :executor)
  AND (:version IS NULL OR t.version = :version)
  AND (:project IS NULL OR t.project_code = :project)
  AND (cardinality(:team) = 0 OR jsonb_exists_any(t.teams_set, :team))
  AND (:search IS NULL
       OR t.item_key ILIKE '%' || :search || '%'
       OR t.name ILIKE '%' || :search || '%')
ORDER BY t.executed_on DESC NULLS LAST, t.item_key
LIMIT 500
