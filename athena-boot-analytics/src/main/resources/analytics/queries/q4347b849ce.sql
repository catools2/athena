WITH daily AS (
    SELECT 
        DATE_TRUNC('hour', executed_on) AS hour,
        cycle_short_name AS "Test Cycle",
        SUM(pass + "blocked" + fail) / MAX(cycle_size)::float * 100 AS "Total Executed"
    FROM athena.mv_test_cycle_statistics
    WHERE cycle_name LIKE '%Regression%' 
      AND project_code = 'DEMO' 
      AND version = :versions
      AND cycle_short_name LIKE '%Automated%' 
      AND executed_on IS NOT NULL
    GROUP BY DATE_TRUNC('hour', executed_on), cycle_short_name
)
SELECT
    COALESCE(hour, NOW()) AS "time",
    "Test Cycle" AS metric,
    SUM("Total Executed") OVER (
        PARTITION BY "Test Cycle"
        ORDER BY COALESCE(hour, NOW())
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS value
FROM daily
ORDER BY "time", "Test Cycle";
