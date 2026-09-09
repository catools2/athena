SELECT
    version as version,
    count(distinct item_key) AS total
FROM athena.mv_items i
CROSS JOIN LATERAL jsonb_array_elements_text(item_version_set) AS version
Where project_code = 'DEMO' and item_type = 'Bug'
AND i.created_on between :timeFrom and :timeTo
AND EXISTS (
  SELECT 1
  FROM jsonb_array_elements_text(teams_set) AS team
  WHERE team.value = ANY(:team)
)
GROUP BY version
order by 2 desc
