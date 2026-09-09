SELECT
    DATE_TRUNC('day', updated_on) AS "time",
    PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY EXTRACT(day FROM Age(updated_on, created_on))) FILTER (WHERE item_status = 'Automated') AS "Automated",
    PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY EXTRACT(day FROM Age(updated_on, created_on))) FILTER (WHERE item_status = 'Playwright Automated ') AS "Playwright Automated",
    PERCENTILE_CONT(0.75) WITHIN GROUP (ORDER BY EXTRACT(day FROM Age(updated_on, created_on))) FILTER (WHERE item_status = 'Dev Automated') AS "Dev Automated"
FROM athena.mv_items
Where item_key ~~* 'demo-t%' 
    and item_type = 'Test' 
    and item_status in ('Automated', 'Playwright Automated ', 'Dev Automated')
    and updated_on between :timeFrom and :timeTo 
    and jsonb_exists_any(teams_set, :team) 
GROUP BY DATE_TRUNC('day', updated_on)
ORDER by DATE_TRUNC('day', updated_on)
