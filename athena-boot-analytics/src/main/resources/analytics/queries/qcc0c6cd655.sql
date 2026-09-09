select distinct i.item_key, i.name, i.rca_set ->>0 as rca, area as functional_area, i.item_version_set as versions, i.item_status, i.created_on, i.updated_on, i.teams_set as team, vcnu.username as reporter
FROM athena.mv_items i
CROSS JOIN LATERAL jsonb_array_elements_text(functional_area_set) AS area
Left Join athena.vw_core_normalized_users vcnu on vcnu.id = i.created_by 
WHERE i.project_code = 'DEMO'
  AND i.item_type = 'Bug'
  AND i.created_on between :timeFrom and :timeTo
