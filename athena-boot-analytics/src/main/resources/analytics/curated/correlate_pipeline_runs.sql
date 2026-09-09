-- Pipeline executions in the window, with pass/fail counts per pipeline run.
SELECT p.name        AS pipeline,
       p.number      AS run,
       p.start_date,
       p.end_date,
       count(e.id)                                           AS executions,
       count(*) FILTER (WHERE s.name = 'SUCCESS')            AS passed,
       count(*) FILTER (WHERE s.name <> 'SUCCESS')           AS failed,
       round(extract(epoch FROM (p.end_date - p.start_date))) AS duration_sec
FROM athena_pipeline.pipeline p
LEFT JOIN athena_pipeline.execution e ON e.pipeline_id = p.id
LEFT JOIN athena_pipeline.status s ON s.id = e.status_id
WHERE p.start_date BETWEEN :timeFrom AND :timeTo
GROUP BY p.name, p.number, p.start_date, p.end_date
ORDER BY p.start_date DESC
LIMIT 200
