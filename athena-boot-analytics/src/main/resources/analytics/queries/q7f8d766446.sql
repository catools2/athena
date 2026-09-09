SELECT
    rca_set ->>0,
    COUNT(DISTINCT item_id) AS total
FROM athena.mv_items i
WHERE project_code = 'DEMO' and item_type = 'Bug'
AND created_on between :timeFrom and :timeTo
AND EXISTS (
    SELECT 1
    FROM jsonb_array_elements_text(affected_version_set) AS version
    WHERE version.value = :versions
)
and trim(environment_set ->> 0) = 'Production' 
and rca_set ->> 0 != 'total' 
GROUP BY rca_set ->>0
Order by 2 desc
