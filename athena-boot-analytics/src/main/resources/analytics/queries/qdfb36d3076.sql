select distinct i.item_key, i.item_status, i.name, i.created_on, i.updated_on, vcnu.username as created_by
FROM athena.mv_items i
LEFT JOIN athena.vw_core_normalized_users vcnu ON vcnu.id = i.created_by
WHERE project_code = 'DEMO' and item_type = 'Bug'
AND created_on between :timeFrom and :timeTo
AND vcnu.username = ANY(:username)
AND EXISTS (
    SELECT 1
    FROM jsonb_array_elements_text(teams_set) AS team
    WHERE team.value = ANY(:team)
)
