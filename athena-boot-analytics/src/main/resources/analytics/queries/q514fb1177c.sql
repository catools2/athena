SELECT
  DATE_TRUNC('day', i.updated_on) AS time,
  item_priority AS metric,
  COUNT(DISTINCT i.item_id) AS value
FROM athena.mv_items i
WHERE i.item_key ilike 'demo-t%'
  AND i.item_type = 'Test'
  AND i.item_status = 'Playwright Automated '
  AND i.updated_on between :timeFrom and :timeTo
GROUP BY item_priority, DATE_TRUNC('day', i.updated_on)
ORDER BY time ASC
