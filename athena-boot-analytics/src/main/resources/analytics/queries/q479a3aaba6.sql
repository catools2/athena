WITH categorized_cycles AS (
    SELECT DISTINCT 
        tcs.item_id,
        tcs.version,
        COALESCE(tcs.teams_set->>0, '--') as team,
        CASE 
            WHEN tcs.cycle_short_name LIKE '%Baseline%' THEN 1 ELSE 0 
        END AS baseline,
        CASE 
            WHEN tcs.cycle_short_name LIKE '%Automated%' THEN 1 ELSE 0 
        END AS automated,
        CASE 
            WHEN tcs.cycle_short_name NOT LIKE '%Automated%' AND tcs.cycle_short_name NOT LIKE '%Baseline%' THEN 1 ELSE 0 
        END AS manual
    FROM athena.mv_test_cycle_statistics tcs
    WHERE 
        tcs.cycle_name LIKE '%Regression%' 
        AND tcs.project_code = 'DEMO' 
        AND tcs.version = :versions
        AND item_execution_rank = 1
)
SELECT 
    replace(t.team, 'Team ',''),
    SUM(t.automated) AS automated,
    SUM(t.manual) AS manual,
    SUM(t.baseline) AS baseline
FROM categorized_cycles t
Where t.team not in ('Mobile', 'Performance', 'SME team')
GROUP BY t.team
ORDER BY t.team
