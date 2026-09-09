WITH categorized_cycles AS (
    SELECT DISTINCT 
        tcs.item_id,
        tcs.version,
        tcs.teams_set,
        CASE 
            WHEN tcs.cycle_short_name LIKE '%SME%' THEN 1 ELSE 0 
        END AS sme,
        CASE 
            WHEN tcs.cycle_short_name LIKE '%Baseline%' THEN 1 ELSE 0 
        END AS baseline,
        CASE 
            WHEN tcs.cycle_short_name LIKE '%Automated%' THEN 1 ELSE 0 
        END AS automated,
        CASE 
            WHEN tcs.cycle_short_name NOT LIKE '%Automated%' AND tcs.cycle_short_name NOT LIKE '%Baseline%' and tcs.cycle_short_name NOT LIKE '%SME%' THEN 1 ELSE 0 
        END AS manual
    FROM athena.mv_test_cycle_statistics tcs
    WHERE 
        tcs.cycle_name LIKE '%Regression%' 
        AND tcs.project_code = 'DEMO' 
        AND tcs.version = :versions
        AND item_execution_rank = 1
)
SELECT 
    t.version,
    SUM(t.automated) AS "Automated",
    SUM(t.manual) AS "Manual",
    SUM(t.baseline) AS "Dev Automated",
    SUM(t.sme) AS "SME"
FROM categorized_cycles t
GROUP BY t.version
ORDER BY SUM(t.automated + t.manual + t.baseline)
