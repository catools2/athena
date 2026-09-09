WITH daily AS (
    SELECT
        DATE_TRUNC('day', occurred) AS day,
        SUM(need_review) AS need_review_delta,
        SUM(reviewed) AS reviewed_delta,
        SUM(review_in_progress) AS review_in_progress_delta,
        SUM(review_passed) AS review_passed_delta,
        SUM(rework_required) AS rework_required_delta,
        SUM(rework_required_automated) AS rework_required_automated_delta,
        SUM(manual) AS manual_delta,
        SUM(automated) AS automated_delta,
        SUM(automation_candidate) AS automation_candidate_delta,
        SUM(automation_in_progress) AS automation_in_progress_delta,
        SUM(playwright_automated) AS playwright_automated_delta,
        SUM(playwright_automation_candidate) AS playwright_automation_candidate_delta,
        SUM(playwright_automation_in_progress) AS playwright_automation_in_progress_delta,
        SUM(dev_automated) AS dev_automated_delta,
        SUM(dev_automation_candidate) AS dev_automation_candidate_delta
    FROM athena.mv_inventory_trend
    Where item_key ~~* 'demo-t%' and item_type = 'Test' and occurred between :timeFrom and :timeTo 
    GROUP BY DATE_TRUNC('day', occurred)
)
SELECT
    day AS "time",

    SUM(need_review_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Need Review",

    SUM(reviewed_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Reviewed",

    SUM(review_in_progress_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Review In Progress",

    SUM(review_passed_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Review Passed",

    SUM(rework_required_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Rework Required",

    SUM(rework_required_automated_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Rework Required Automated",

    SUM(manual_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Manual",

    SUM(automated_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Automated",

    SUM(automation_candidate_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Automation Candidate",

    SUM(playwright_automated_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Playwright Automated",

    SUM(automation_in_progress_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Automation In Progress",

    SUM(playwright_automation_candidate_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Playwright Automation Candidate",

    SUM(playwright_automation_in_progress_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Playwright Automation In Progress",

    SUM(dev_automated_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Dev Automated",

    SUM(dev_automation_candidate_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Dev Automation Candidate"

FROM daily
ORDER BY day;
