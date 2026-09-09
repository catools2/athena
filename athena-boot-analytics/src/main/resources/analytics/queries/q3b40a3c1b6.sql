SELECT
  DATE_TRUNC('day', i.created_on) AS time,
  area AS metric,
  COUNT(DISTINCT i.item_id) AS value
FROM athena.mv_items i
CROSS JOIN LATERAL jsonb_array_elements_text(functional_area_set) AS area
WHERE i.project_code = 'DEMO'
  AND i.item_type = 'Bug'
  AND i.created_on between :timeFrom and :timeTo
GROUP BY area, DATE_TRUNC('day', i.created_on)
ORDER BY time ASC
