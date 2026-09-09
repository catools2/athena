SELECT
    item_status,
    avg(EXTRACT(DAY FROM (Age(updated_on, created_on)))) AS total
FROM athena.mv_items
WHERE project_code = 'DEMO' and item_type = 'Test'
Group by item_status
Order by item_status
