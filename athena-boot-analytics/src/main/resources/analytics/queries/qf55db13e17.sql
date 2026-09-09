SELECT
  DATE_TRUNC('day', i.created_on) AS time,
  teams_set ->>0 AS metric,
  COUNT(DISTINCT i.item_id) AS value
FROM athena.mv_items i
Left Join athena.vw_core_normalized_users vcnu on vcnu.id = i.created_by 
WHERE i.project_code = 'DEMO'
  AND i.item_type = 'Bug'
  AND i.created_on between :timeFrom and :timeTo
AND (cardinality(:reporters) = 0 OR vcnu.username = ANY(:reporters))
AND (cardinality(:team) = 0 OR jsonb_exists_any(i.teams_set, :team))
AND (cardinality(:rcas) = 0 OR jsonb_exists_any(i.rca_set, :rcas))
AND (cardinality(:affected_versions) = 0 OR jsonb_exists_any(i.affected_version_set, :affected_versions))
GROUP BY teams_set ->>0, DATE_TRUNC('day', i.created_on)
ORDER BY time ASC
