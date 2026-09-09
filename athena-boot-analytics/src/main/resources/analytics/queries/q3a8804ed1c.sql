WITH daily AS (
    SELECT
        DATE_TRUNC('day', occurred) AS day,
        SUM(playwright_automated) AS playwright_automated_delta,
        SUM(playwright_automation_in_progress) AS playwright_automation_in_progress_delta
    FROM athena.mv_inventory_trend
    Where item_key ~~* 'demo-t%' and item_type = 'Test' and occurred between :timeFrom and :timeTo 
    GROUP BY DATE_TRUNC('day', occurred)
)
SELECT
    day AS "time",
    SUM(playwright_automated_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Playwright Automated",
    SUM(playwright_automation_in_progress_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Playwright Automation In Progress"
FROM daily
ORDER BY day;
