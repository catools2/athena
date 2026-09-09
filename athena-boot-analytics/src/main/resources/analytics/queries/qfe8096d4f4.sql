SELECT
  DATE_TRUNC('day', i.created_on) AS time,
  vcnu.username AS metric,
  COUNT(DISTINCT i.item_id) AS value
FROM athena.mv_items i
Left Join athena.vw_core_normalized_users vcnu on vcnu.id = i.created_by 
WHERE i.project_code = 'DEMO'
  AND i.item_type = 'Bug'
  AND i.created_on between :timeFrom and :timeTo
GROUP BY vcnu.username, DATE_TRUNC('day', i.created_on)
ORDER BY time ASC
