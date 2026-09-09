SELECT distinct jsonb_array_elements_text(affected_version_set)
FROM athena.mv_test_cycle_statistics
WHERE item_key ~~* 'demo-t%' and item_type = 'Test'
AND created_on between :timeFrom and :timeTo
