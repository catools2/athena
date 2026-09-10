-- One row of headline numbers for the landing page.
--
-- Quality and timing live in different schemas, so each figure is its own scalar subquery
-- against the same window. That keeps a missing domain from nulling the whole row: if nothing
-- has reported timings, the test figures still render.
--
-- Not every filter reaches every figure, because not every dimension exists in every schema:
-- version and team are properties of a test item, project reaches timing through
-- athena_metric.project_id, and a git commit carries none of the three. The page states this
-- under the tiles rather than letting a filtered figure look scoped when it is not.
SELECT
  (SELECT count(*) FROM athena.mv_test_cycle_statistics t
    WHERE t.item_execution_rank = 1
      AND t.executed_on BETWEEN :timeFrom AND :timeTo
      AND (:version IS NULL OR t.version = :version)
      AND (:project IS NULL OR t.project_code = :project)
      AND (cardinality(:team) = 0 OR jsonb_exists_any(t.teams_set, :team)))          AS executed,
  (SELECT count(*) FROM athena.mv_test_cycle_statistics t
    WHERE t.item_execution_rank = 1
      AND t.executed_on BETWEEN :timeFrom AND :timeTo
      AND t.execution_status = 'Fail'
      AND (:version IS NULL OR t.version = :version)
      AND (:project IS NULL OR t.project_code = :project)
      AND (cardinality(:team) = 0 OR jsonb_exists_any(t.teams_set, :team)))          AS failed,
  (SELECT count(DISTINCT t.cycle_code) FROM athena.mv_test_cycle_statistics t
    WHERE t.executed_on BETWEEN :timeFrom AND :timeTo
      AND (:version IS NULL OR t.version = :version)
      AND (:project IS NULL OR t.project_code = :project)
      AND (cardinality(:team) = 0 OR jsonb_exists_any(t.teams_set, :team)))          AS cycles,
  (SELECT round((100.0 * count(*) FILTER (WHERE t.execution_status = 'Pass')
                 / nullif(count(*), 0))::numeric, 1)
     FROM athena.mv_test_cycle_statistics t
    WHERE t.item_execution_rank = 1
      AND t.executed_on BETWEEN :timeFrom AND :timeTo
      AND (:version IS NULL OR t.version = :version)
      AND (:project IS NULL OR t.project_code = :project)
      AND (cardinality(:team) = 0 OR jsonb_exists_any(t.teams_set, :team)))          AS pass_rate,
  (SELECT round(percentile_cont(0.95) WITHIN GROUP (ORDER BY m.duration))
     FROM athena_metric.metric m
     LEFT JOIN athena_core.project p ON p.id = m.project_id
    WHERE m.action_time BETWEEN :timeFrom AND :timeTo
      AND (:project IS NULL OR p.code = :project))                                   AS p95_ms,
  (SELECT count(*) FROM athena_git.commit c
    WHERE c.commit_time BETWEEN :timeFrom AND :timeTo)                               AS commits
