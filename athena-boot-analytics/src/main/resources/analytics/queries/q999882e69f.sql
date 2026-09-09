WITH categorized_cycles AS (
    SELECT DISTINCT 
        tcs.item_id,
        tcs.version,
        execution_status AS status
    FROM athena.mv_test_cycle_statistics tcs
    WHERE 
        tcs.cycle_name LIKE '%Regression%' 
        AND tcs.project_code = 'DEMO' 
        AND tcs.version = :versions
        AND item_execution_rank = 1
)
SELECT 
    status,
    COUNT(*) AS count
FROM categorized_cycles
GROUP BY status
ORDER BY count DESC;
