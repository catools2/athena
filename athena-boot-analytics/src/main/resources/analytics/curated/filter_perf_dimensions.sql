-- Filter vocabulary for the performance pages. Same one-round-trip, unwindowed contract as
-- filter_test_dimensions.
--
-- Action names are absent on purpose: there are potentially thousands, and the page already
-- offers a substring search plus a drill-down table for finding one. A select with thousands of
-- options is a worse instrument than a text box.
SELECT 'environment' AS dimension, e.code AS value
FROM athena_metric.metric m
JOIN athena_core.environment e ON e.id = m.environment_id
UNION
SELECT 'project', p.code
FROM athena_metric.metric m
JOIN athena_core.project p ON p.id = m.project_id
UNION
SELECT 'action_type', a.type
FROM athena_metric.action a
WHERE a.type IS NOT NULL
ORDER BY 1, 2
