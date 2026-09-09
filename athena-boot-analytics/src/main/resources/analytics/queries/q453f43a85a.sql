select distinct i.item_key, i.name, i.rca_set ->>0 as rca, i.item_version_set as versions, i.item_status, i.created_on, i.updated_on, i.teams_set as team
FROM athena.mv_items i
WHERE project_code = 'DEMO' and item_type = 'Bug'
AND EXISTS (
    SELECT 1
    FROM jsonb_array_elements_text(affected_version_set) AS version
    WHERE version.value = :versions
)
and trim(environment_set ->> 0) = 'Production'
