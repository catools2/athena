-- Every recorded execution of one test, newest first, so a flaky test is visible as a
-- pattern rather than a single red result.
SELECT tcs.cycle_code,
       tcs.cycle_short_name,
       tcs.version,
       tcs.execution_status,
       tcs.executed_on,
       tcs.executor
FROM athena.mv_test_cycle_statistics tcs
WHERE tcs.item_key = :item
ORDER BY tcs.executed_on DESC NULLS LAST
