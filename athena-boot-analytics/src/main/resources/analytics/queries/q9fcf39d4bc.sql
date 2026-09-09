select item_key, item_status, name, cycle_code, cycle_short_name, executed_on, executor, execution_status from athena.mv_test_cycle_statistics
WHERE cycle_name LIKE '%Regression%' 
  AND project_code = 'DEMO' 
  AND item_execution_rank = 1
  AND version = :version
  AND jsonb_exists(teams_set, :team)
  AND cycle_short_name LIKE '%Automated%'
