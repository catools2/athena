-- Test outcomes in the window, joined to the item they cover. The "what ran" half.
SELECT tcs.item_key,
       tcs.name,
       tcs.cycle_short_name,
       tcs.version,
       tcs.execution_status,
       tcs.executed_on,
       tcs.executor
FROM athena.mv_test_cycle_statistics tcs
WHERE tcs.executed_on BETWEEN :timeFrom AND :timeTo
  AND tcs.item_execution_rank = 1
  AND (:status IS NULL OR tcs.execution_status = :status)
  AND (:version IS NULL OR tcs.version = :version)
  AND (:search IS NULL
       OR tcs.item_key ILIKE '%' || :search || '%'
       OR tcs.name ILIKE '%' || :search || '%')
ORDER BY tcs.executed_on DESC
LIMIT 500
