SELECT distinct version
FROM athena.mv_test_cycle_statistics
WHERE item_key ~~* 'demo-t%' and item_type = 'Test' and version like '4%'
