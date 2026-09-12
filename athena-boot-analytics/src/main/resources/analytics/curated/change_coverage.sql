-- Which domains have ever been ingested at all, regardless of any window or filter.
--
-- This exists to tell two very different emptinesses apart. "No commit landed in the last 30
-- days" is an answer; "athena_git has never been populated" is a missing integration, and a
-- panel that reports the second as the first sends the reader hunting through time windows for
-- data that is not in the database at all.
--
-- EXISTS, not count(*): the question is only ever whether there is a single row, and the
-- planner can stop at the first one. A count would scan athena_metric.metric in full on every
-- page load for an answer no one reads.
SELECT 'commits'         AS domain, EXISTS (SELECT 1 FROM athena_git.commit)              AS has_any
UNION ALL
SELECT 'pipeline_runs',           EXISTS (SELECT 1 FROM athena_pipeline.pipeline)
UNION ALL
SELECT 'test_executions',         EXISTS (SELECT 1 FROM athena_tms.execution)
UNION ALL
SELECT 'pods',                    EXISTS (SELECT 1 FROM athena.mv_pod_basic_info)
UNION ALL
SELECT 'timing',                  EXISTS (SELECT 1 FROM athena_metric.metric)
