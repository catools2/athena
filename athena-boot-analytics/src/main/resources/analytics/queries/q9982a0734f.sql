SELECT class_name, sucess_rate
FROM athena.vw_pipeline_product_monitoring_statistic
WHERE start_date BETWEEN :timeFrom AND :timeTo 
  AND class_name = ANY(:name) 
  AND class_name NOT LIKE '%Batch%'
  AND environment = :environment
  AND sucess_rate >= 80
ORDER BY sucess_rate ASC;
