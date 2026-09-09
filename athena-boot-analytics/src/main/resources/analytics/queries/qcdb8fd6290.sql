SELECT cycle_short_name, count(*) AS total, sum(pass) AS pass, sum(fail) AS fail, sum(skipped) AS skipped, sum(blocked) AS blocked
FROM athena.mv_test_cycle_statistics tcs
WHERE 
    tcs.cycle_name LIKE '%Regression%' 
    AND tcs.project_code = 'DEMO' 
    AND version = :version
    AND jsonb_exists(teams_set, :team)
    AND cycle_short_name LIKE '%Automated%' 
    AND item_execution_rank = 1
GROUP BY cycle_short_name
ORDER BY cycle_short_name DESC
LIMIT 100;
