SELECT
  DATE_TRUNC('day', i.created_on) AS time,
  teams_set ->>0 AS metric,
  COUNT(DISTINCT i.item_id) AS value
FROM athena.mv_items i
WHERE i.project_code = 'DEMO'
  AND i.item_type = 'Bug'
  AND i.created_on between :timeFrom and :timeTo
AND EXISTS (
    SELECT 1
    FROM jsonb_array_elements_text(affected_version_set) AS version
    WHERE version.value = :versions
)
and trim(environment_set ->> 0) = 'Production' 
GROUP BY teams_set ->>0, DATE_TRUNC('day', i.created_on)
ORDER BY time ASC
