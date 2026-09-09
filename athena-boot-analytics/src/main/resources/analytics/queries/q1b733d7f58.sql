SELECT
    item_status,
    COUNT(DISTINCT item_id) AS total
FROM athena.mv_items i
WHERE i.project_code = 'DEMO'
  AND i.item_type = 'Bug'
  AND EXISTS (
        SELECT 1
        FROM jsonb_array_elements_text(teams_set) AS team
        WHERE team.value = ANY(:team)
  )
  AND EXISTS (
        SELECT 1
        FROM jsonb_array_elements_text(affected_version_set) AS version
        WHERE version.value = :version
  )
GROUP BY item_status
Order by item_status
