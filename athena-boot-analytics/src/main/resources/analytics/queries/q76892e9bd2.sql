WITH categorized_cycles AS (
    SELECT DISTINCT 
        tcs.item_id,
        tcs.version,
        tcs.teams_set,
        CASE 
            WHEN tcs.cycle_short_name LIKE '%Automated%' or tcs.cycle_short_name LIKE '%Playwright%' THEN 1 ELSE 0 
        END AS automated,
        CASE 
            WHEN tcs.cycle_name LIKE '%Manual%' Or tcs.cycle_name LIKE '%Final%' THEN 1 ELSE 0 
        END AS manual
    FROM athena.mv_test_cycle_statistics tcs
    WHERE 
        tcs.cycle_name {{cycle_type}} '%Regression%' 
        AND tcs.project_code = 'DEMO' 
        AND tcs.version = :versions
)
SELECT 
    t.version,
    SUM(t.automated) AS "Automated",
    SUM(t.manual) AS "Manual"
FROM categorized_cycles t
GROUP BY t.version
