SELECT
    COUNT(DISTINCT item_id) AS total
FROM athena.mv_items i
WHERE project_code = 'DEMO' and item_type = 'Bug'
AND EXISTS (
    SELECT 1
    FROM jsonb_array_elements_text(affected_version_set) AS version
    WHERE version.value = :versions
)
and trim(environment_set ->> 0) = 'Production'
