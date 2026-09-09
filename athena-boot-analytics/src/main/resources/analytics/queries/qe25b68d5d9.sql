select distinct i.item_key, i.name, area as functional_area, i.item_version_set as versions, i.teams_set as team
FROM athena.mv_items i
CROSS JOIN LATERAL jsonb_array_elements_text(functional_area_set) AS area
WHERE i.item_key ilike 'demo-t%'
  AND i.item_type = 'Test'
  AND i.item_status = 'Playwright Automated '
  AND i.created_on between :timeFrom and :timeTo
