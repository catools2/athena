SELECT count(distinct i.item_id),
       DATE_TRUNC('month', i.created_on) as month
FROM athena.mv_items i
LEFT JOIN athena.mv_test_cycle_statistics tcs
       ON i.item_id = tcs.item_id
Where i.item_key ~~ 'DEMO-T%'
       AND tcs.cycle_name LIKE '%Regression%'
       AND i.created_on < NOW() - INTERVAL '4 months'
       AND tcs.executed_on IS NULL
       AND EXISTS (
              SELECT 1
              FROM jsonb_array_elements_text(tcs.teams_set) AS team
              WHERE team.value = ANY(:team)
       )
GROUP BY DATE_TRUNC('month', i.created_on);
