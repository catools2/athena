SELECT class_name, sucess_rate
FROM athena. vw_pipeline_product_monitoring_statistic
WHERE start_date BETWEEN :timeFrom AND :timeTo 
  AND environment = :environment
  AND sucess_rate < 70
ORDER BY sucess_rate ASC;
