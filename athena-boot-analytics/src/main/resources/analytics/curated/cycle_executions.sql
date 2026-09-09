-- Every test in one cycle with its latest outcome. The detail behind a cycle row.
SELECT tcs.item_key,
       tcs.name,
       tcs.item_status,
       tcs.item_priority,
       tcs.execution_status,
       tcs.executed_on,
       tcs.executor
FROM athena.mv_test_cycle_statistics tcs
WHERE tcs.cycle_code = :cycle
  AND tcs.item_execution_rank = 1
ORDER BY
  -- Failures first: the reason anyone opens a cycle is to see what broke.
  CASE
    WHEN tcs.execution_status = 'Fail' THEN 0
    WHEN tcs.execution_status ILIKE '%block%' THEN 1
    WHEN tcs.execution_status IS NULL THEN 2
    ELSE 3
  END,
  tcs.item_key
