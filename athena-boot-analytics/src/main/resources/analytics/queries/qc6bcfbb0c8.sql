WITH categorized_cycles AS (
    SELECT DISTINCT 
        tcs.item_id,
        tcs.version,
        tcs.teams_set,
        CASE WHEN tcs.cycle_short_name LIKE '%Baseline%' THEN 1 ELSE 0 END AS baseline,
        CASE WHEN tcs.cycle_short_name LIKE '%Automated%' THEN 1 ELSE 0 END AS automated,
        CASE WHEN tcs.cycle_name NOT LIKE '%Automated%' THEN 1 ELSE 0 END AS manual
    FROM athena.mv_test_cycle_statistics tcs
    WHERE 
        tcs.cycle_name LIKE '%Regression%' 
        AND tcs.project_code = 'DEMO' 
        AND tcs.version = :version
        AND jsonb_exists(tcs.teams_set, :team)
        AND item_execution_rank = 1
)
SELECT
    t.version,
    SUM(t.automated) AS "Automated",
    SUM(t.manual) AS "Manual",
    SUM(t.baseline) AS "Dev Automated"
FROM categorized_cycles t
GROUP BY t.version
ORDER BY SUM(t.automated + t.manual + t.baseline)
