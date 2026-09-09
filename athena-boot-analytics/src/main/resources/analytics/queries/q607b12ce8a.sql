SELECT
    vcnu.username as reporter,
    count(distinct item_key) AS total
FROM athena.mv_items i
left join athena.vw_core_normalized_users vcnu on vcnu.id = i.created_by
Where project_code = 'DEMO' and item_type = 'Bug'
AND i.created_on between :timeFrom and :timeTo
AND (cardinality(:reporters) = 0 OR vcnu.username = ANY(:reporters))
AND (cardinality(:team) = 0 OR jsonb_exists_any(i.teams_set, :team))
AND (cardinality(:rcas) = 0 OR jsonb_exists_any(i.rca_set, :rcas))
AND (cardinality(:affected_versions) = 0 OR jsonb_exists_any(i.affected_version_set, :affected_versions))
GROUP BY vcnu.username
order by 2 desc
