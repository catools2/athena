-- Test cycles with their execution rollup, newest first.
-- Backs the cycle browser: one row per cycle, with the counts a QA lead reads first.
--
-- Every count states its own scope in its name, because two scopes genuinely coexist here. A test
-- that has never run has no timestamp to filter on, and "planned but not executed" is exactly what
-- this page exists to surface - so those rows survive every window and are reported separately as
-- not_run. A single "total" spanning both would mean "executions in this window plus tests that
-- have never run", which is not a quantity anyone wants. Keeping them apart also makes the
-- drill-through exact: on a one-day window, executed equals the height of the bar that was clicked.
--
-- Two kinds of filter act here, and the difference matters:
--
--   Row filters (window, version, project, team) decide which executions are counted, so they
--   change the numbers in every column.
--
--   Cycle filters (status, search) decide which cycles appear, and are applied in HAVING so they
--   do NOT change the numbers. Restricting the counted rows to one status would report a cycle as
--   "12 executed, 12 failed" whenever the status filter was Fail - a rollup that agrees with the
--   filter instead of describing the cycle.
SELECT tcs.cycle_code,
       tcs.cycle_short_name,
       tcs.cycle_name,
       tcs.version,
       tcs.project_code,
       count(*) FILTER (WHERE tcs.executed_on IS NOT NULL)               AS executed,
       count(*) FILTER (WHERE tcs.execution_status = 'Pass')             AS passed,
       count(*) FILTER (WHERE tcs.execution_status = 'Fail')             AS failed,
       count(*) FILTER (WHERE tcs.execution_status ILIKE '%block%')      AS blocked,
       count(*) FILTER (WHERE tcs.executed_on IS NULL)                   AS not_run,
       round((100.0 * count(*) FILTER (WHERE tcs.execution_status = 'Pass')
              / nullif(count(*) FILTER (WHERE tcs.executed_on IS NOT NULL), 0))::numeric, 1)
                                                                         AS pass_rate,
       max(tcs.executed_on)                                              AS last_executed
FROM athena.mv_test_cycle_statistics tcs
WHERE tcs.item_execution_rank = 1
  AND (tcs.executed_on IS NULL OR tcs.executed_on BETWEEN :timeFrom AND :timeTo)
  AND (:version IS NULL OR tcs.version = :version)
  AND (:project IS NULL OR tcs.project_code = :project)
  AND (cardinality(:team) = 0 OR jsonb_exists_any(tcs.teams_set, :team))
GROUP BY tcs.cycle_code, tcs.cycle_short_name, tcs.cycle_name, tcs.version, tcs.project_code
HAVING (:status IS NULL OR count(*) FILTER (WHERE tcs.execution_status = :status) > 0)
   AND (:search IS NULL
        OR tcs.cycle_code ILIKE '%' || :search || '%'
        OR tcs.cycle_name ILIKE '%' || :search || '%')
ORDER BY max(tcs.executed_on) DESC NULLS LAST, tcs.cycle_code
