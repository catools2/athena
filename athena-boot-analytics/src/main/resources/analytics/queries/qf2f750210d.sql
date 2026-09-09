SELECT class_name, sucess_rate
FROM athena.vw_pipeline_product_monitoring_statistic
WHERE start_date BETWEEN :timeFrom AND :timeTo 
  AND class_name = ANY(:name) 
  AND environment = :environment
ORDER BY sucess_rate ASC
LIMIT 1
