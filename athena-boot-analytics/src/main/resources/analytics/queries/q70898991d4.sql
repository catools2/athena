SELECT
    team as team,
    count(distinct item_key) AS total
FROM athena.mv_items i
CROSS JOIN LATERAL jsonb_array_elements_text(teams_set) AS team
WHERE i.item_key ilike 'demo-t%'
  AND i.item_type = 'Test'
  AND i.item_status = 'Playwright Automated '
  AND i.updated_on between :timeFrom and :timeTo
GROUP BY team
order by 2 desc
