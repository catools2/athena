SELECT
    item_status,
    COUNT(DISTINCT item_id) AS total
FROM athena.mv_items
WHERE project_code = 'DEMO' and item_type = 'Test' and item_key ~~ 'DEMO-T%'
GROUP BY item_status
Order by item_status
