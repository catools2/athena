WITH categorized_cycles AS (
  SELECT
    DISTINCT tcs.item_id,
    tcs.version,
    tcs.teams_set,
    CASE
      WHEN tcs.cycle_short_name LIKE '%SME%' THEN 1
      ELSE 0
    END AS sme,
    CASE
      WHEN tcs.cycle_short_name LIKE '%Baseline%' THEN 1
      ELSE 0
    END AS baseline,
    CASE
      WHEN tcs.cycle_short_name LIKE '%Playwright%' THEN 1
      ELSE 0
    END AS playwright,
    CASE
      WHEN tcs.cycle_short_name LIKE '%Automated%'
      and tcs.cycle_short_name not LIKE '%Baseline%' THEN 1
      ELSE 0
    END AS automated,
    CASE
      WHEN (cycle_name LIKE '%Manual%' Or cycle_name LIKE '%Final%') and tcs.cycle_short_name not LIKE '%SME%' THEN 1
      ELSE 0
    END AS manual,
    1 AS total
  FROM
    athena.mv_test_cycle_statistics tcs
  WHERE
    tcs.cycle_name LIKE '%Regression%'
    AND tcs.project_code = 'DEMO'
    AND tcs.version = :versions
)
SELECT
  t.version,
  SUM(t.automated + t.playwright + t.baseline) * 100.0 / sum(total) AS "Automated"
FROM
  categorized_cycles t
GROUP BY
  t.version
