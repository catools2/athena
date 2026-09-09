SELECT
    version as version,
    count(distinct item_key) AS total
FROM athena.mv_items i
CROSS JOIN LATERAL jsonb_array_elements_text(item_version_set) AS version
Where project_code = 'DEMO' and item_type = 'Bug'
AND i.created_on between :timeFrom and :timeTo
GROUP BY version
order by 2 desc
