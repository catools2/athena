SELECT 
    cycle_short_name AS "Test Cycle",
    SUM(pass + "blocked" + skipped + fail) / Sum(total)::float * 100 AS "Total Executed"
FROM athena.mv_test_cycle_statistics
WHERE cycle_name {{cycle_type}} '%Regression%' 
    AND project_code = 'DEMO' 
    AND version = :versions
    AND cycle_short_name LIKE '%Automated%' 
GROUP by cycle_short_name
