SELECT
   distinct vcnu.username as reporter
FROM athena.mv_items i
left join athena.vw_core_normalized_users vcnu on vcnu.id = i.created_by
WHERE project_code = 'DEMO'
AND created_on between :timeFrom and :timeTo
order by 1 desc
