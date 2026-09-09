SELECT
    item_priority as priority,
    count(distinct item_key) AS total
FROM athena.mv_items i
WHERE i.item_key ilike 'demo-t%'
  AND i.item_type = 'Test'
  AND i.item_status = 'Playwright Automated '
  AND i.updated_on between :timeFrom and :timeTo
GROUP BY item_priority
order by 2 desc
