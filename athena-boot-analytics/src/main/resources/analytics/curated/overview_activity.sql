-- Daily volume across domains, on one time axis.
--
-- A generated day series rather than a join between the three sources: a day with commits but no
-- test runs is exactly the kind of gap this chart exists to make visible, and an inner join would
-- hide it by dropping the day entirely.
--
-- version and project reach tests and pipeline runs (a pipeline carries a version, and a version
-- belongs to a project) but not commits, which have neither. team reaches tests only. The chart
-- says so when one of those filters is active, because a chart where one line is scoped and the
-- others silently are not is worse than no filter at all.
WITH days AS (
    SELECT generate_series(
             date_trunc('day', :timeFrom::timestamptz),
             date_trunc('day', :timeTo::timestamptz),
             interval '1 day') AS bucket
),
tests AS (
    SELECT date_trunc('day', executed_on) AS bucket, count(*) AS n
    FROM athena.mv_test_cycle_statistics
    WHERE executed_on BETWEEN :timeFrom AND :timeTo AND item_execution_rank = 1
      AND (:version IS NULL OR version = :version)
      AND (:project IS NULL OR project_code = :project)
      AND (cardinality(:team) = 0 OR jsonb_exists_any(teams_set, :team))
    GROUP BY 1
),
commits AS (
    SELECT date_trunc('day', commit_time) AS bucket, count(*) AS n
    FROM athena_git.commit
    WHERE commit_time BETWEEN :timeFrom AND :timeTo
    GROUP BY 1
),
runs AS (
    SELECT date_trunc('day', p.start_date) AS bucket, count(*) AS n
    FROM athena_pipeline.pipeline p
    LEFT JOIN athena_core.app_version v ON v.id = p.version_id
    LEFT JOIN athena_core.project pr ON pr.id = v.project_id
    WHERE p.start_date BETWEEN :timeFrom AND :timeTo
      AND (:version IS NULL OR v.name = :version)
      AND (:project IS NULL OR pr.code = :project)
    GROUP BY 1
)
SELECT d.bucket,
       coalesce(t.n, 0) AS test_executions,
       coalesce(c.n, 0) AS commits,
       coalesce(r.n, 0) AS pipeline_runs
FROM days d
LEFT JOIN tests t ON t.bucket = d.bucket
LEFT JOIN commits c ON c.bucket = d.bucket
LEFT JOIN runs r ON r.bucket = d.bucket
ORDER BY d.bucket
