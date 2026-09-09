SELECT cycle_short_name, sum(total) AS total, sum(pass) AS pass, sum(fail) AS fail, sum(skipped) AS skipped, sum(blocked) AS blocked
FROM athena.mv_test_cycle_statistics tcs
WHERE 
    tcs.cycle_name {{cycle_type}} '%Regression%' 
    AND tcs.project_code = 'DEMO' 
    AND tcs.version = :versions
    AND tcs.cycle_name LIKE '% SME%'
    AND item_execution_rank = 1
GROUP BY cycle_short_name
ORDER BY cycle_short_name DESC
LIMIT 100;
