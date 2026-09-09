SELECT
  ROUND(
    100.0 * COALESCE(SUM(total_sucess), 0)
    / NULLIF(COALESCE(SUM(total_execution), 0), 0),
    2
  ) AS PASS,
  ROUND(
    100.0 * COALESCE(SUM(total_failure + total_skip), 0)
    / NULLIF(COALESCE(SUM(total_execution), 0), 0),
    2
  ) AS FAIL
FROM athena.vw_pipeline_product_monitoring_statistic
WHERE start_date BETWEEN :timeFrom AND :timeTo
  AND environment = :environment;
