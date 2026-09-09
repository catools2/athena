SELECT
    version as version,
    count(distinct item_key) AS total
FROM athena.mv_items i
CROSS JOIN LATERAL jsonb_array_elements_text(item_version_set) AS version
WHERE i.item_key ilike 'demo-t%'
  AND i.item_type = 'Test'
  AND i.item_status = 'Playwright Automated '
  AND i.updated_on between :timeFrom and :timeTo
  and functional_area_set ->> 0 != 'total' 
GROUP BY version
order by 2 desc
