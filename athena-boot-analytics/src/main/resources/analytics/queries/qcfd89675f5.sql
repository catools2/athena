SELECT 
    cycle_short_name AS "Test Cycle",
    SUM(pass + "blocked" + skipped + fail) / Sum(total)::float * 100 AS "Total Executed"
FROM athena.mv_test_cycle_statistics
WHERE cycle_name LIKE '%Regression%' 
    AND project_code = 'DEMO' 
    AND version = :versions
    AND cycle_name NOT LIKE '%Automated%' and cycle_name NOT LIKE '%SME%' 
GROUP BY cycle_short_name
