select distinct jsonb_array_elements_text(rca_set) from athena.mv_items
WHERE project_code = 'DEMO'
AND created_on between :timeFrom and :timeTo
order by 1 desc
