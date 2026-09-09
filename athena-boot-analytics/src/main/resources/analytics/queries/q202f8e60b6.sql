SELECT version AS version,
                  sum(automated) AS "Automated",
                  sum(sme) AS "SME",
                  sum(manual) AS "Manual",
                  sum(baseline) AS "Baseline"
FROM athena.mv_regression_automation_to_manual_ratio
Where project_code = 'DEMO' and automated > 100
GROUP BY version
ORDER BY "Automated"
