SELECT class_name, sucess_rate
FROM athena. vw_pipeline_product_monitoring_statistic
WHERE start_date BETWEEN :timeFrom AND :timeTo 
  AND class_name LIKE '%Batch%'
  AND environment = :environment
ORDER BY sucess_rate ASC;
