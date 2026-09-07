-- athena.mvw_test_cycle_statistics
-- type      : VIEW
-- dep level : 4
-- depends on: mv_items, vw_core_normalized_users
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.mvw_test_cycle_statistics CASCADE;

CREATE OR REPLACE VIEW athena.mvw_test_cycle_statistics AS WITH cycle_total AS
  (SELECT c_1.code,
          count(DISTINCT e_1.item_id) AS cycle_size
   FROM athena_tms.cycle c_1
   JOIN athena_tms.execution e_1 ON e_1.cycle_id = c_1.id
   GROUP BY c_1.code)
SELECT v.name AS version,
       rank() OVER (PARTITION BY i.item_key, v.id
                    ORDER BY e.created DESC) AS item_execution_rank,
       c.code AS cycle_code,
       c.name AS cycle_name,
       "substring"(c.name::text,
                   '.*/(.*)$'::text) AS cycle_short_name,
       e.executed AS executed_on,
       cnu.username AS executor,
       i.project_code,
       i.item_key,
       i.created_by,
       i.created_on,
       i.name,
       i.updated_by,
       i.updated_on,
       i.item_priority,
       i.item_status,
       i.item_type,
       i.rca_set,
       i.affected_version_set,
       i.blocks_documentation_set,
       i.blocks_set,
       i.bonfire_testing_set,
       i.cloner_set,
       i.component_version_set,
       i.component_set,
       i.cr_to_sop_set,
       i.environment_set,
       i.dependency_set,
       i.depends_on_set,
       i.duplicate_set,
       i.epic_dependency_set,
       i.epics_set,
       i.fix_version_set,
       i.folder_set,
       i.functional_area_set,
       i.issue_split_set,
       i.issue_set,
       i.item_version_set,
       i.known_problem_sop_set,
       i.label_set,
       i.origin_to_cr_set,
       i.parent_set,
       i.problem_incident_set,
       i.reference_set,
       i.regression_suite_set,
       i.remediating_product_ticket_set,
       i.requirement_set,
       i.story_dependency_set,
       i.story_set,
       i.sub_task_set,
       i.subcomponent_set,
       i.teams_set,
       i.test_set,
       i.original_issue_key_set,
       i.ai_genrated_set,
       i.ai_assisted_set,
       i.item_id,
       i.project_id,
       i.priority_id,
       i.status_id,
       i.type_id,
       CASE
           WHEN e.executed IS NOT NULL THEN e.executed::date - i.created_on::date
           ELSE NULL::integer
       END AS created_to_executed_in_days,
       ct.cycle_size,
       CASE
           WHEN upper(s.name::text) = 'CREATED'::text THEN 'SKIPPED'::character varying
           ELSE s.name
       END AS execution_status,
       CASE
           WHEN upper(s.name::text) = 'PASS'::text THEN 1
           ELSE 0
       END AS pass,
       CASE
           WHEN upper(s.name::text) = 'IN_PROGRESS'::text THEN 1
           ELSE 0
       END AS in_progress,
       CASE
           WHEN upper(s.name::text) = 'BLOCKED'::text THEN 1
           ELSE 0
       END AS blocked,
       CASE
           WHEN upper(s.name::text) = 'FAIL'::text THEN 1
           ELSE 0
       END AS fail,
       CASE
           WHEN upper(s.name::text) = 'CREATED'::text THEN 1
           ELSE 0
       END AS skipped,
       CASE
           WHEN upper(s.name::text) = 'NOT_EXECUTED'::text THEN 1
           ELSE 0
       END AS unexecuted,
       1 AS total
FROM athena_tms.cycle c
LEFT JOIN athena_tms.execution e ON e.cycle_id = c.id
LEFT JOIN athena.vw_core_normalized_users cnu ON e.executor_id = cnu.id
LEFT JOIN athena.mv_items i ON i.item_id = e.item_id
LEFT JOIN athena_tms.status s ON s.id = e.status_id
LEFT JOIN athena_core.app_version v ON v.id = c.version_id
LEFT JOIN cycle_total ct ON ct.code::text = c.code::text;
