-- Daily test outcomes. The headline quality chart: whether the red band is growing.
--
-- Each bucket is a day the reader can click through to, so the day boundary here and the window
-- the quality page receives on that click have to be the same truncation - both are
-- date_trunc('day', ...) in the database's own time zone.
SELECT date_trunc('day', t.executed_on)                              AS bucket,
       count(*) FILTER (WHERE t.execution_status = 'Pass')           AS passed,
       count(*) FILTER (WHERE t.execution_status = 'Fail')           AS failed,
       count(*) FILTER (WHERE t.execution_status ILIKE '%block%')    AS blocked
FROM athena.mv_test_cycle_statistics t
WHERE t.executed_on BETWEEN :timeFrom AND :timeTo
  AND t.item_execution_rank = 1
  AND (:version IS NULL OR t.version = :version)
  AND (:project IS NULL OR t.project_code = :project)
  AND (cardinality(:team) = 0 OR jsonb_exists_any(t.teams_set, :team))
GROUP BY date_trunc('day', t.executed_on)
ORDER BY 1
