-- Tests failing most often in the window. The shortlist a QA lead opens the tool to get.
SELECT t.item_key,
       t.name,
       count(*) FILTER (WHERE t.execution_status = 'Fail') AS failures,
       count(*)                                            AS runs,
       max(t.executed_on)                                  AS last_run
FROM athena.mv_test_cycle_statistics t
WHERE t.executed_on BETWEEN :timeFrom AND :timeTo
  AND (:version IS NULL OR t.version = :version)
  AND (:project IS NULL OR t.project_code = :project)
  AND (cardinality(:team) = 0 OR jsonb_exists_any(t.teams_set, :team))
GROUP BY t.item_key, t.name
HAVING count(*) FILTER (WHERE t.execution_status = 'Fail') > 0
ORDER BY count(*) FILTER (WHERE t.execution_status = 'Fail') DESC, t.item_key
LIMIT 10
