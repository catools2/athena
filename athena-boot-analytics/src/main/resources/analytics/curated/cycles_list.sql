-- Test cycles with their execution rollup, newest first.
-- Backs the cycle browser: one row per cycle, with the counts a QA lead reads first.
SELECT tcs.cycle_code,
       tcs.cycle_short_name,
       tcs.cycle_name,
       tcs.version,
       tcs.project_code,
       count(*)                                                          AS total,
       count(*) FILTER (WHERE tcs.execution_status = 'Pass')             AS passed,
       count(*) FILTER (WHERE tcs.execution_status = 'Fail')             AS failed,
       count(*) FILTER (WHERE tcs.execution_status ILIKE '%block%')      AS blocked,
       count(*) FILTER (WHERE tcs.execution_status IS NULL)              AS unexecuted,
       round((100.0 * count(*) FILTER (WHERE tcs.execution_status = 'Pass')
              / nullif(count(*), 0))::numeric, 1)                                   AS pass_rate,
       max(tcs.executed_on)                                              AS last_executed
FROM athena.mv_test_cycle_statistics tcs
WHERE tcs.item_execution_rank = 1
  AND (:version IS NULL OR tcs.version = :version)
  AND (cardinality(:team) = 0 OR jsonb_exists_any(tcs.teams_set, :team))
GROUP BY tcs.cycle_code, tcs.cycle_short_name, tcs.cycle_name, tcs.version, tcs.project_code
ORDER BY max(tcs.executed_on) DESC NULLS LAST, tcs.cycle_code
