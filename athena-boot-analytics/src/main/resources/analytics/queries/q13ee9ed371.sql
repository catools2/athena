SELECT
    label,
    sum(CASE WHEN cycle_short_name LIKE '%Baseline%' THEN 1 ELSE 0 END) AS baseline,
    sum(CASE WHEN cycle_short_name LIKE '%Automated%' THEN 1 ELSE 0 END) AS automated,
    sum(CASE WHEN cycle_name NOT LIKE '%Automated%' THEN 1 ELSE 0 END) AS manual,
    SUM(pass) AS "Pass",
    SUM(fail) AS "Fail",
    SUM(skipped) AS "Skipped",
    SUM(blocked) AS "Blocked",
    SUM(in_progress) AS "InProgress",
    SUM(unexecuted) AS "Unexecuted",
    SUM(in_progress + unexecuted) AS "Total Unexecuted",
    COUNT(*) - SUM(in_progress + unexecuted) AS "Total Executed",
    SUM(total) AS "Total"
FROM athena.mv_test_cycle_statistics
CROSS JOIN LATERAL jsonb_array_elements_text(label_set) AS label
WHERE cycle_name {{cycle_type}} '%Regression%' 
  AND project_code = 'DEMO' 
  AND version = :versions
  AND item_execution_rank = 1
GROUP BY label
ORDER BY label
LIMIT 10000
