-- Where failures concentrate, along four dimensions at once.
--
-- One query rather than four: the page offers these as a toggle on a single chart, and switching
-- the toggle should redraw from data already in the browser rather than issue a round trip. The
-- shape is the same (dimension, bucket) pairing the filter vocabularies use.
--
-- Every branch reports the full outcome split, not just the failure count. A bucket with 40
-- failures out of 400 and one with 40 out of 45 are the same bar if only failures are drawn, and
-- they are not remotely the same problem.
WITH scoped AS (
    SELECT t.item_priority, t.item_type, t.executor, t.teams_set, t.execution_status
    FROM athena.mv_test_cycle_statistics t
    WHERE t.item_execution_rank = 1
      AND t.executed_on BETWEEN :timeFrom AND :timeTo
      AND (:cycle IS NULL OR t.cycle_code = :cycle)
      AND (:version IS NULL OR t.version = :version)
      AND (:project IS NULL OR t.project_code = :project)
      AND (cardinality(:team) = 0 OR jsonb_exists_any(t.teams_set, :team))
)
SELECT 'priority'::text                                         AS dimension,
       coalesce(item_priority, '(none)')::text                  AS bucket,
       count(*)                                                 AS executed,
       count(*) FILTER (WHERE execution_status = 'Pass')        AS passed,
       count(*) FILTER (WHERE execution_status ILIKE '%block%') AS blocked,
       count(*) FILTER (WHERE execution_status = 'Fail')        AS failed
FROM scoped GROUP BY 2
UNION ALL
SELECT 'type'::text, coalesce(item_type, '(none)')::text, count(*),
       count(*) FILTER (WHERE execution_status = 'Pass'),
       count(*) FILTER (WHERE execution_status ILIKE '%block%'),
       count(*) FILTER (WHERE execution_status = 'Fail')
FROM scoped GROUP BY 2
UNION ALL
SELECT 'executor'::text, coalesce(executor, '(unassigned)')::text, count(*),
       count(*) FILTER (WHERE execution_status = 'Pass'),
       count(*) FILTER (WHERE execution_status ILIKE '%block%'),
       count(*) FILTER (WHERE execution_status = 'Fail')
FROM scoped GROUP BY 2
UNION ALL
-- teams_set is a jsonb array, so a test on two teams counts toward both. The bars therefore sum
-- to more than the execution count, which is correct for "who is affected" and would be wrong
-- for "how many ran" - the chart says so rather than leaving the reader to discover it.
SELECT 'team'::text, team.value::text, count(*),
       count(*) FILTER (WHERE execution_status = 'Pass'),
       count(*) FILTER (WHERE execution_status ILIKE '%block%'),
       count(*) FILTER (WHERE execution_status = 'Fail')
FROM scoped,
     LATERAL jsonb_array_elements_text(
       CASE WHEN jsonb_typeof(teams_set) = 'array' THEN teams_set ELSE '[]'::jsonb END
     ) AS team(value)
GROUP BY 2
ORDER BY 1, 6 DESC, 3 DESC, 2
