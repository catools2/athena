WITH daily AS (
    SELECT
        DATE_TRUNC('day', occurred) AS day,
        SUM(manual + automation_candidate + automation_in_progress + playwright_automation_candidate + playwright_automation_in_progress + dev_automation_candidate + dev_automation_in_progress) AS quality_debt_delta
    FROM athena.mv_inventory_trend
    Where item_key ~~* 'demo-t%' and item_type = 'Test' and occurred between :timeFrom and :timeTo
    GROUP BY DATE_TRUNC('day', occurred)
)
SELECT
    day AS "time",

    SUM(quality_debt_delta) OVER (
        ORDER BY day
        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    ) AS "Quality Debt"

FROM daily
ORDER BY day;
