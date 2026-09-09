WITH daily AS (
    SELECT
        DATE_TRUNC('day', occurred) AS day,
        SUM(manual) AS manual_delta,
        SUM(automated) AS automated_delta,
        SUM(automation_candidate) AS automation_candidate_delta,
        SUM(automation_in_progress) AS automation_in_progress_delta,
        SUM(playwright_automated) AS playwright_automated_delta,
        SUM(playwright_automation_candidate) AS playwright_automation_candidate_delta,
        SUM(playwright_automation_in_progress) AS playwright_automation_in_progress_delta,
        SUM(dev_automated) AS dev_automated_delta,
        SUM(dev_automation_candidate) AS dev_automation_candidate_delta,
        SUM(dev_automation_in_progress) AS dev_automation_in_progress_delta
    FROM athena.mv_inventory_trend
    Where item_key ~~* 'demo-t%' and item_type = 'Test' and occurred between :timeFrom and :timeTo and jsonb_exists_any(teams_set, :team)
    GROUP BY DATE_TRUNC('day', occurred)
)
SELECT
    day AS "time",

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

    SUM(automation_in_progress_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Automation In Progress",

    SUM(playwright_automated_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Playwright Automated",

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
    ) AS "Dev Automation Candidate",


    SUM(dev_automation_in_progress_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Dev Automation In Progress"

FROM daily
ORDER BY day;
