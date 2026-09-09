select distinct i.item_key, i.name, i.rca_set ->>0 as rca, i.item_version_set as versions, i.item_status, i.created_on, i.updated_on, i.teams_set as team
FROM athena.mv_items i
WHERE project_code = 'DEMO' and item_type = 'Bug'
AND created_on between :timeFrom and :timeTo
AND EXISTS (
    SELECT 1
    FROM jsonb_array_elements_text(teams_set) AS team
    WHERE team.value = ANY(:team)
)
