SELECT 
    cycle_short_name AS "Test Cycle",
    SUM(pass + "blocked" + skipped + fail) / Sum(total)::float * 100 AS "Total Executed"
FROM athena.mv_test_cycle_statistics
WHERE cycle_name LIKE '%Regression%' 
    AND project_code = 'DEMO' 
    AND version = :version
    AND jsonb_exists(teams_set, :team)
    AND cycle_short_name LIKE '%Automated%' 
GROUP BY cycle_short_name
