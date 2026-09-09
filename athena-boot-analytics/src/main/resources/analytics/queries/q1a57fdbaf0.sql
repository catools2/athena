SELECT
    teams_set ->>0,
    COUNT(DISTINCT item_id) AS total
FROM athena.mv_items i
Left Join athena.vw_core_normalized_users vcnu on vcnu.id = i.created_by 
WHERE i.project_code = 'DEMO'
  AND i.item_type = 'Bug'
  AND i.created_on between :timeFrom and :timeTo
GROUP BY teams_set ->>0
Order by 2 desc
