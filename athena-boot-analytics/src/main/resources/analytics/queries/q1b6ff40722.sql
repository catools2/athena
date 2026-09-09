WITH categorized_cycles AS (
    SELECT DISTINCT 
        tcs.item_id,
        tcs.version,
        execution_status AS status
    FROM athena.mv_test_cycle_statistics tcs
    WHERE 
        tcs.cycle_name {{cycle_type}} '%Regression%' 
        AND tcs.project_code = 'DEMO' 
        AND tcs.version = :version
        AND item_execution_rank = 1
        AND EXISTS (
            SELECT 1
            FROM jsonb_array_elements_text(tcs.teams_set) AS team
            WHERE team.value = ANY(:team)
        )
)
SELECT 
    status,
    COUNT(*) AS count
FROM categorized_cycles
GROUP BY status
ORDER BY count DESC;
