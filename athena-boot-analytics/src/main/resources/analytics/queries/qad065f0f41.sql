select item_key, item_status, name, cycle_code, cycle_short_name, executed_on, executor, execution_status, folder_set from athena.mv_test_cycle_statistics
WHERE cycle_name LIKE '%Automated Team%' 
  AND project_code = 'DEMO' 
  AND item_execution_rank = 1
  AND version = :version
  AND jsonb_exists(teams_set, :team)
  AND jsonb_exists(folder_set, '/Automated Test Cases')
  AND cycle_short_name LIKE '%Automated%'
