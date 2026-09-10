-- Every value a quality filter can offer, as (dimension, value) pairs in one round trip.
--
-- One query rather than five: the filter bar needs all of them before it can render, and five
-- sequential fetches is five chances to show a half-populated bar.
--
-- Deliberately NOT scoped to the selected window. A filter whose options disappear as the window
-- narrows is the classic frustrating filter - you cannot broaden the search because the value you
-- want is no longer offered. The cost is that a chosen value may match nothing, which the empty
-- result state already says clearly.
SELECT 'version' AS dimension, t.version AS value
FROM athena.mv_test_cycle_statistics t
WHERE t.version IS NOT NULL
UNION
SELECT 'project', t.project_code
FROM athena.mv_test_cycle_statistics t
WHERE t.project_code IS NOT NULL
UNION
SELECT 'status', t.execution_status
FROM athena.mv_test_cycle_statistics t
WHERE t.execution_status IS NOT NULL
UNION
SELECT 'executor', t.executor
FROM athena.mv_test_cycle_statistics t
WHERE t.executor IS NOT NULL
UNION
-- teams_set is a jsonb array. The CASE is not defensive clutter: jsonb_array_elements_text
-- raises on a non-array, and WHERE is not guaranteed to run before a LATERAL expansion.
SELECT 'team', team.value
FROM athena.mv_test_cycle_statistics t,
     LATERAL jsonb_array_elements_text(
       CASE WHEN jsonb_typeof(t.teams_set) = 'array' THEN t.teams_set ELSE '[]'::jsonb END
     ) AS team(value)
WHERE team.value <> ''
ORDER BY 1, 2
