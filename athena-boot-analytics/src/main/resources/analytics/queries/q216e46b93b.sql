SELECT
    version as version,
    count(distinct item_key) AS total
FROM athena.mv_items i
Left Join athena.vw_core_normalized_users vcnu on vcnu.id = i.created_by 
CROSS JOIN LATERAL jsonb_array_elements_text(item_version_set) AS version
WHERE i.project_code = 'DEMO'
  AND i.item_type = 'Bug'
  AND i.created_on between :timeFrom and :timeTo
GROUP BY version
order by 2 desc
