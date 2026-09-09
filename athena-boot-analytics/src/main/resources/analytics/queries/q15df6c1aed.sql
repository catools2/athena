SELECT
    item_status,
    COUNT(DISTINCT item_id) AS total
FROM athena.mv_items i
WHERE i.project_code = 'DEMO'
  AND i.item_type = 'Bug'
  AND i.created_on between :timeFrom and :timeTo
  AND EXISTS (
        SELECT 1
        FROM jsonb_array_elements_text(teams_set) AS team
        WHERE team.value = ANY(:team)
  )
GROUP BY item_status
Order by item_status
