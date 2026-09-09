SELECT
    functional_area_set ->>0,
    COUNT(DISTINCT item_id) AS total
FROM athena.mv_items i
WHERE i.item_key ilike 'demo-t%'
  AND i.item_type = 'Test'
  AND i.item_status = 'Playwright Automated '
  AND i.updated_on between :timeFrom and :timeTo
  and functional_area_set ->> 0 != 'total' 
GROUP BY functional_area_set ->>0
Order by 2 desc
