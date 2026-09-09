WITH ranked AS (
    SELECT
        m.*,
        ROW_NUMBER() OVER (
            PARTITION BY m.item_key
            ORDER BY m.executed_on DESC, m.item_execution_rank DESC
        ) AS rn
    FROM athena.mv_test_cycle_statistics m
    WHERE m.version          = :versions
      AND m.cycle_short_name LIKE '%Automated%'
      AND (m.cycle_name {{cycle_type}} '%Regression%' OR m.cycle_name LIKE '%Automated Team%')
),
item_component AS (
    SELECT DISTINCT ON (item_key)
        item_key,
        CASE
            -- Internal API
            WHEN regexp_like (package_name, 'com.example.automation.demo.api.*') THEN 'API'
            WHEN regexp_like (package_name, 'com.example.automation.demo.batch.*') THEN 'JTXS'
            WHEN regexp_like (package_name, 'com.example.automation.demo.aws.*') THEN 'AWS'
            WHEN regexp_like (package_name, 'com.example.automation.demo.web.testcases.enrollment.*') THEN 'Enrollment'
            WHEN regexp_like (package_name, 'com.example.automation.demo.web.testcases.batch.*') THEN 'Batch'
            WHEN regexp_like (package_name, 'com.example.automation.demo.web.testcases.accountmanag.*') THEN 'Account'
            WHEN regexp_like (package_name, 'com.example.automation.demo.web.testcases.billingAutomation.*') THEN 'Billing'
            WHEN regexp_like (package_name, 'com.example.automation.demo.web.testcases.reports.*') THEN 'Report'
            WHEN regexp_like (package_name, 'com.example.automation.demo.web.testcases.user.*') THEN 'User'
            WHEN regexp_like (package_name, 'com.example.automation.demo.web.testcases.vendors.*') THEN 'Vendors'
            WHEN regexp_like (package_name, 'com.example.automation.demo.web.testcases.nexus.*') THEN 'Nexus'
            WHEN regexp_like (package_name, 'com.example.automation.demo.web.testcases.card.*') THEN 'Card'
            WHEN regexp_like (package_name, 'com.example.automation.demo.web.testcases.invoicing.*') THEN 'Invoicing'
            ELSE 'Web'
        END AS component
    FROM athena.mv_pipeline_execution_info
    WHERE version_name = :versions
      AND package_name IS NOT NULL
)

SELECT
    ic.component AS "Component",

    (
        SUM(
            CASE
                WHEN r.pass > 0
                  OR r.fail > 0
                  OR r.skipped > 0
                  OR r.blocked > 0
                THEN 1 ELSE 0
            END
        )::float
        / COUNT(DISTINCT r.item_key)
    ) * 100 AS "Total Executed (%)"

FROM ranked r
JOIN item_component ic
    ON ic.item_key = r.item_key
WHERE r.rn = 1
GROUP BY ic.component
ORDER BY ic.component;
