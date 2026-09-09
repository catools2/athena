SELECT
    vcnu.username,
    COUNT(DISTINCT item_id) AS total
FROM athena.mv_items i
left join athena.vw_core_normalized_users vcnu on i.created_by = vcnu.id
WHERE project_code = 'DEMO' and item_type = 'Bug'
AND created_on between :timeFrom and :timeTo
AND vcnu.username = ANY(:username)
AND EXISTS (
    SELECT 1
    FROM jsonb_array_elements_text(teams_set) AS team
    WHERE team.value = ANY(:team)
)
GROUP BY vcnu.username
Order by 2 desc
