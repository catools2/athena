SELECT distinct class_name
FROM athena.vw_pipeline_product_monitoring_statistic
WHERE start_date BETWEEN :timeFrom AND :timeTo
