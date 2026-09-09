SELECT
    m.item_key,
    MIN(m."name") AS name,
    STRING_AGG(DISTINCT m.cycle_name, ', ') AS cycle_names,
    COUNT(m.fail) AS fail_count
FROM athena.mv_test_cycle_statistics m
WHERE m.version = :version
  AND m.cycle_short_name LIKE '%Automated%'
  AND (m.cycle_name {{cycle_type}} '%Regression%' OR m.cycle_name LIKE '%Automated Team%')
  AND jsonb_exists(m.teams_set::jsonb, :team)
  AND m.item_status NOT IN ('Obsolete', 'Archived')
  AND NOT EXISTS (
        SELECT 1
        FROM athena.mv_test_cycle_statistics p
        WHERE p.item_key = m.item_key
          AND p.version = :version
          AND p.cycle_short_name LIKE '%Automated%'
          AND (p.cycle_name {{cycle_type}} '%Regression%' OR p.cycle_name LIKE '%Automated Team%')
          AND (p.pass = 1)
    )
GROUP BY 
    m.item_key;
