SELECT
    item_status,
    avg(EXTRACT(DAY FROM (Age(updated_on, created_on)))) AS total
FROM athena.mv_items
WHERE project_code = 'DEMO' and item_type = 'Test' and created_on BETWEEN :timeFrom AND :timeTo
Group by item_status
