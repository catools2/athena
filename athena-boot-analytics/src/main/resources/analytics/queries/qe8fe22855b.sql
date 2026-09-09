SELECT distinct jsonb_array_elements_text(teams_set)
FROM athena.mv_test_cycle_statistics
WHERE item_key ~~* 'demo-t%' and item_type = 'Test' and version = :version
