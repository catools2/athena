SELECT
  DATE_TRUNC('day', i.created_on) AS time,
  teams_set ->>0 AS metric,
  COUNT(DISTINCT i.item_id) AS value
FROM athena.mv_items i
LEFT JOIN athena.vw_core_normalized_users vcnu ON i.created_by = vcnu.id
WHERE i.project_code = 'DEMO'
  AND i.item_type = 'Bug'
  AND i.created_on between :timeFrom and :timeTo
  AND vcnu.username = ANY(:username)
  AND EXISTS (
        SELECT 1
        FROM jsonb_array_elements_text(teams_set) AS team
        WHERE team.value = ANY(:team)
  )
GROUP BY teams_set ->>0, DATE_TRUNC('day', i.created_on)
ORDER BY time ASC
