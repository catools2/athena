SELECT
    version as version,
    count(distinct item_key) AS total
FROM athena.mv_items i
Left Join athena.vw_core_normalized_users vcnu on vcnu.id = i.created_by 
CROSS JOIN LATERAL jsonb_array_elements_text(item_version_set) AS version
WHERE i.project_code = 'DEMO'
  AND i.item_type = 'Bug'
  AND i.created_on between :timeFrom and :timeTo
AND (cardinality(:reporters) = 0 OR vcnu.username = ANY(:reporters))
AND (cardinality(:team) = 0 OR jsonb_exists_any(i.teams_set, :team))
AND (cardinality(:rcas) = 0 OR jsonb_exists_any(i.rca_set, :rcas))
AND (cardinality(:affected_versions) = 0 OR jsonb_exists_any(i.affected_version_set, :affected_versions))
GROUP BY version
order by 2 desc
