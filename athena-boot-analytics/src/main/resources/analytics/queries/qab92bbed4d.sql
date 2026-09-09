select distinct i.item_key, i.item_status, i.name, i.updated_on, vcnu.username as updated_by
FROM athena.mv_items i
LEFT JOIN athena.mv_test_cycle_statistics tcs ON i.item_id = tcs.item_id
LEFT JOIN athena.vw_core_normalized_users vcnu ON vcnu.id = i.updated_by
Where i.item_key ~~ 'DEMO-T%'
       AND tcs.cycle_name LIKE '%Regression%'
       AND i.created_on < NOW() - INTERVAL '4 months'
       AND tcs.executed_on IS NULL
       AND EXISTS (
              SELECT 1
              FROM jsonb_array_elements_text(tcs.teams_set) AS team
              WHERE team.value = ANY(:team)
       )
