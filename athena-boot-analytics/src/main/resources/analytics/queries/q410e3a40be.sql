SELECT
    vcnu.username as reporter,
    count(distinct item_key) AS total
FROM athena.mv_items i
left join athena.vw_core_normalized_users vcnu on vcnu.id = i.created_by
Where project_code = 'DEMO' and item_type = 'Bug'
AND i.created_on between :timeFrom and :timeTo
GROUP BY vcnu.username
order by 2 desc
