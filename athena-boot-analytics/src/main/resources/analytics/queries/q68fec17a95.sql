SELECT distinct jsonb_array_elements_text(teams_set)
FROM athena.mv_inventory_trend
WHERE item_key ~~* 'demo-t%' and item_type = 'Test' and occurred between :timeFrom and :timeTo
