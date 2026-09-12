-- Daily execution outcomes across whatever the page is currently scoped to.
--
-- Takes an optional :cycle so one query serves both levels of the page: all cycles when nothing
-- is selected, one cycle when something is. Two near-identical queries would be two places for
-- the filter semantics to drift apart.
SELECT date_trunc('day', t.executed_on)                           AS bucket,
       count(*) FILTER (WHERE t.execution_status = 'Pass')        AS passed,
       count(*) FILTER (WHERE t.execution_status ILIKE '%block%') AS blocked,
       count(*) FILTER (WHERE t.execution_status = 'Fail')        AS failed,
       count(DISTINCT t.cycle_code)                               AS cycles_touched
FROM athena.mv_test_cycle_statistics t
WHERE t.item_execution_rank = 1
  AND t.executed_on BETWEEN :timeFrom AND :timeTo
  AND (:cycle IS NULL OR t.cycle_code = :cycle)
  AND (:version IS NULL OR t.version = :version)
  AND (:project IS NULL OR t.project_code = :project)
  AND (cardinality(:team) = 0 OR jsonb_exists_any(t.teams_set, :team))
GROUP BY date_trunc('day', t.executed_on)
ORDER BY 1
