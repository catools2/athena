-- Filter vocabulary for the correlation page: the four dimensions its five panels are keyed by.
-- Same one-round-trip, unwindowed contract as filter_test_dimensions.
SELECT 'repository' AS dimension, r.name AS value
FROM athena_git.repository r
UNION
SELECT 'author', u.username
FROM athena_git.commit c
JOIN athena_core."user" u ON u.id = c.author_id
UNION
SELECT 'pipeline', p.name
FROM athena_pipeline.pipeline p
UNION
SELECT 'namespace', pod.namespace
FROM athena.mv_pod_basic_info pod
WHERE pod.namespace IS NOT NULL
UNION
SELECT 'app', pod.app
FROM athena.mv_pod_basic_info pod
WHERE pod.app IS NOT NULL
ORDER BY 1, 2
