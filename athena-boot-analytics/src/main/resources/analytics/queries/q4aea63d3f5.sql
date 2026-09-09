WITH ranked AS (
    SELECT
        m.*,
        ROW_NUMBER() OVER (
            PARTITION BY m.item_key
            ORDER BY m.executed_on DESC, m.item_execution_rank DESC
        ) AS rn
    FROM athena.mv_test_cycle_statistics m
    WHERE m.version          = :version
      AND m.cycle_short_name LIKE '%Automated%'
      AND (m.cycle_name {{cycle_type}} '%Regression%' OR m.cycle_name LIKE '%Automated Team%')
    --   AND m.item_status      NOT IN ('Obsolete', 'Archived')
      AND EXISTS (
            SELECT 1
            FROM jsonb_array_elements_text(m.teams_set) AS team
            WHERE team.value = ANY(:team)
      )
),
item_component AS (
    SELECT DISTINCT ON (item_key)
        item_key,
        CASE
            -- Internal API
            WHEN package_name LIKE 'com.example.automation.demo.api.internal.accounts.%'
              OR package_name LIKE 'com.example.automation.demo.api.internal.activations.%'
              OR package_name LIKE 'com.example.automation.demo.api.internal.billingAutomation.%'
              OR package_name LIKE 'com.example.automation.demo.api.internal.card.%'
              OR package_name LIKE 'com.example.automation.demo.api.internal.catalog.%'
              OR package_name LIKE 'com.example.automation.demo.api.internal.company.%'
              OR package_name LIKE 'com.example.automation.demo.api.internal.documents.%'
              OR package_name LIKE 'com.example.automation.demo.api.internal.enrollment.%'
              OR package_name LIKE 'com.example.automation.demo.api.internal.fee.%'
              OR package_name LIKE 'com.example.automation.demo.api.internal.invoicing.%'
              OR package_name LIKE 'com.example.automation.demo.api.internal.mfa.%'
              OR package_name LIKE 'com.example.automation.demo.api.internal.payer.%'
              OR package_name LIKE 'com.example.automation.demo.api.internal.payment.%'
              OR package_name LIKE 'com.example.automation.demo.api.internal.reports.%'
              OR package_name LIKE 'com.example.automation.demo.api.internal.users.%'
              OR package_name LIKE 'com.example.automation.demo.api.internal.vendor.%'
              OR package_name LIKE 'com.example.automation.demo.api.rest.%'
                THEN 'Internal API'
            -- External API
            WHEN package_name LIKE 'com.example.automation.demo.api.external.accounts.%'
              OR package_name LIKE 'com.example.automation.demo.api.external.card.%'
              OR package_name LIKE 'com.example.automation.demo.api.external.dictionary.%'
              OR package_name LIKE 'com.example.automation.demo.api.external.documents.%'
              OR package_name LIKE 'com.example.automation.demo.api.external.payer.%'
              OR package_name LIKE 'com.example.automation.demo.api.external.payment.%'
              OR package_name LIKE 'com.example.automation.demo.api.external.reports.%'
              OR package_name LIKE 'com.example.automation.demo.api.external.users.%'
              OR package_name LIKE 'com.example.automation.demo.api.external.vendor.%'
                THEN 'External API'
            -- JTXS
            WHEN package_name LIKE 'com.example.automation.demo.batch.acknowledgment.%'
              OR package_name LIKE 'com.example.automation.demo.batch.amex.%'
              OR package_name LIKE 'com.example.automation.demo.batch.baml.%'
              OR package_name LIKE 'com.example.automation.demo.batch.bnymellon.%'
              OR package_name LIKE 'com.example.automation.demo.batch.btdirect2.%'
              OR package_name LIKE 'com.example.automation.demo.batch.btdirectnexus.%'
              OR package_name LIKE 'com.example.automation.demo.batch.card.%'
              OR package_name LIKE 'com.example.automation.demo.batch.citizens.%'
              OR package_name LIKE 'com.example.automation.demo.batch.citizens_db.%'
              OR package_name LIKE 'com.example.automation.demo.batch.eastern.%'
              OR package_name LIKE 'com.example.automation.demo.batch.feemanager.%'
              OR package_name LIKE 'com.example.automation.demo.batch.fifththird.%'
              OR package_name LIKE 'com.example.automation.demo.batch.huntington.%'
              OR package_name LIKE 'com.example.automation.demo.batch.jpmorgan.%'
              OR package_name LIKE 'com.example.automation.demo.batch.onphase.%'
              OR package_name LIKE 'com.example.automation.demo.batch.regions.%'
              OR package_name LIKE 'com.example.automation.demo.batch.tdbank.%'
              OR package_name LIKE 'com.example.automation.demo.batch.trustmark.%'
              OR package_name LIKE 'com.example.automation.demo.batch.umb.%'
              OR package_name LIKE 'com.example.automation.demo.batch.usbank.%'
                THEN 'JTXS'
            -- Account
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.accountmanagementModern.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.accountmanagment.bankaccount.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.accountmanagment.mfa.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.accountmanagment.paymodexaccount.%'
                THEN 'Account'
            -- Billing
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.billingAutomation.accountAnalysisFile.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.billingAutomation.baWidget.%'
                THEN 'Billing'
            -- Card
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.card.%'
                THEN 'Card'
            -- Catalog
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.catalog.%'
                THEN 'Catalog'
            -- Enrollment ← back as its own separate component
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.enrollment.%'
                THEN 'Enrollment'
            -- Invoicing
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.invoicing.%'
                THEN 'Invoicing'
            -- Nexus
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.nexus.%'
                THEN 'Nexus'
            -- Reports
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.reports.channel.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.reports.external.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.reports.internal.%'
                THEN 'Reports'
            -- Batch
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.batch.%'
                THEN 'Batch'
            -- User
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.user.%'
                THEN 'User'
            -- Vendors
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.vendors.%'
                THEN 'Vendors'
            -- Web
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.arm.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.channelSetup.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.checks.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.commissionpayment.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.configuration.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.dataExtractor.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.dividendsettings.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.enp.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.erp.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.healthcareReports.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.internalUtilities.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.login.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.membership.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.merchant.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.mfa.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.mobile.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.ofac.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.paymentconfiguration.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.paymentmanagement.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.pciencryption.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.privileges.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.settlementAccount.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.sftpSetup.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.sso.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.trading.%'
              OR package_name LIKE 'com.example.automation.demo.web.testcases.vendorView.%'
                THEN 'Web'
            -- AWS
            WHEN package_name LIKE 'com.example.automation.demo.aws.sns.%'
                THEN 'AWS'
            ELSE 'Other'
        END AS component
    FROM athena.mv_pipeline_execution_info
    WHERE version_name = :version
      AND package_name IS NOT NULL
    ORDER BY item_key,
        CASE
            WHEN package_name LIKE 'com.example.automation.demo.api.internal.%'                          THEN 1
            WHEN package_name LIKE 'com.example.automation.demo.api.external.%'                          THEN 2
            WHEN package_name LIKE 'com.example.automation.demo.batch.%'                                  THEN 3
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.accountmanagementModern.%' THEN 4
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.accountmanagment.%'        THEN 4
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.billingAutomation.%'       THEN 5
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.card.%'                    THEN 6
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.catalog.%'                 THEN 7
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.enrollment.%'              THEN 8
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.invoicing.%'               THEN 9
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.nexus.%'                   THEN 10
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.reports.%'                 THEN 11
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.batch.%'                     THEN 12
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.user.%'                    THEN 13
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.vendors.%'                 THEN 14
            WHEN package_name LIKE 'com.example.automation.demo.web.testcases.%'                         THEN 15
            WHEN package_name LIKE 'com.example.automation.demo.aws.%'                                   THEN 16
            ELSE 99
        END ASC
)
SELECT
    ic.component,
    SUM(CASE WHEN r.pass       > 0 THEN 1 ELSE 0 END)  AS "Passed",
    SUM(CASE WHEN r.fail       > 0 THEN 1 ELSE 0 END)  AS "Failed",
    SUM(CASE WHEN r.unexecuted > 0 THEN 1 ELSE 0 END)  AS "Not Executed",
    SUM(CASE WHEN r.skipped    > 0 THEN 1 ELSE 0 END)  AS "Skipped",
    COUNT(DISTINCT r.item_key)                          AS "Total"
FROM ranked r
JOIN item_component ic
    ON ic.item_key = r.item_key
WHERE r.rn = 1
GROUP BY ic.component
ORDER BY ic.component;
