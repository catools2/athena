select distinct vcnu.username
FROM athena.mv_items i
LEFT JOIN athena.vw_core_normalized_users vcnu ON vcnu.id = i.updated_by
WHERE project_code = 'DEMO' and item_type = 'Bug' 
  AND i.created_on between :timeFrom and :timeTo
  AND EXISTS (
        SELECT 1
        FROM jsonb_array_elements_text(teams_set) AS team
        WHERE team.value = ANY(:team)
  )
