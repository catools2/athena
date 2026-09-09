SELECT 
    cycle_short_name AS "Test Cycle",
    SUM(pass + "blocked" + skipped + fail) / Sum(total)::float * 100 AS "Total Executed"
FROM athena.mv_test_cycle_statistics
WHERE cycle_name LIKE '%Regression%' 
    AND project_code = 'DEMO' 
    AND version = :version
    AND cycle_short_name LIKE '%Automated%' 
    AND version = :version
    AND EXISTS (
        SELECT 1
        FROM jsonb_array_elements_text(teams_set) AS team
        WHERE team.value = ANY(:team)
    )
GROUP BY cycle_short_name
