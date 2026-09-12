-- How one cycle burned through its scope: executions per day, and the running total against the
-- number of tests the cycle contains.
--
-- A burn-up rather than a burn-down. The remaining count alone cannot distinguish a cycle that
-- is nearly finished from one whose scope was cut, and the flat line against a rising one is
-- what shows a cycle that has stalled.
WITH size AS (
    SELECT count(*) AS total
    FROM athena.mv_test_cycle_statistics
    WHERE cycle_code = :cycle AND item_execution_rank = 1
),
daily AS (
    SELECT date_trunc('day', executed_on)                         AS bucket,
           count(*)                                               AS executed,
           count(*) FILTER (WHERE execution_status = 'Pass')      AS passed,
           count(*) FILTER (WHERE execution_status = 'Fail')      AS failed
    FROM athena.mv_test_cycle_statistics
    WHERE cycle_code = :cycle
      AND item_execution_rank = 1
      AND executed_on IS NOT NULL
    GROUP BY 1
)
SELECT d.bucket,
       d.executed,
       d.passed,
       d.failed,
       sum(d.executed) OVER (ORDER BY d.bucket) AS cumulative_executed,
       s.total                                  AS cycle_total
FROM daily d
CROSS JOIN size s
ORDER BY d.bucket
