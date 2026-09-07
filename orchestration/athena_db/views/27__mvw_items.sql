-- athena.mvw_items
-- type      : VIEW
-- dep level : 2
-- depends on: mv_item_metadata
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.mvw_items CASCADE;

CREATE OR REPLACE VIEW athena.mvw_items
AS WITH item_versions AS (
         SELECT ivm.item_id,
            jsonb_agg(av.name) AS item_version
           FROM athena_tms.item_version_mid ivm
             LEFT JOIN athena_core.app_version av ON av.id = ivm.version_id
          GROUP BY ivm.item_id
        )
 SELECT DISTINCT p.code AS project_code,
    i.code AS item_key,
    i.created_by,
    i.created_on,
    i.name,
    i.updated_by,
    i.updated_on,
    p2.name AS item_priority,
    TRIM(BOTH FROM s.name) AS item_status,
    it.name AS item_type,
    COALESCE(NULLIF(mc.metadata -> 'rca_set'::text, 'null'::jsonb), '[]'::jsonb) AS rca_set,
    COALESCE(NULLIF(mc.metadata -> 'affected_version_set'::text, 'null'::jsonb), '[]'::jsonb) AS affected_version_set,
    COALESCE(NULLIF(mc.metadata -> 'blocks_documentation_set'::text, 'null'::jsonb), '[]'::jsonb) AS blocks_documentation_set,
    COALESCE(NULLIF(mc.metadata -> 'blocks_set'::text, 'null'::jsonb), '[]'::jsonb) AS blocks_set,
    COALESCE(NULLIF(mc.metadata -> 'bonfire_testing_set'::text, 'null'::jsonb), '[]'::jsonb) AS bonfire_testing_set,
    COALESCE(NULLIF(mc.metadata -> 'cloner_set'::text, 'null'::jsonb), '[]'::jsonb) AS cloner_set,
    COALESCE(NULLIF(mc.metadata -> 'component_version_set'::text, 'null'::jsonb), '[]'::jsonb) AS component_version_set,
    COALESCE(NULLIF(mc.metadata -> 'component_set'::text, 'null'::jsonb), '[]'::jsonb) AS component_set,
    COALESCE(NULLIF(mc.metadata -> 'cr_to_sop_set'::text, 'null'::jsonb), '[]'::jsonb) AS cr_to_sop_set,
    COALESCE(NULLIF(mc.metadata -> 'dependency_set'::text, 'null'::jsonb), '[]'::jsonb) AS dependency_set,
    COALESCE(NULLIF(mc.metadata -> 'depends_on_set'::text, 'null'::jsonb), '[]'::jsonb) AS depends_on_set,
    COALESCE(NULLIF(mc.metadata -> 'duplicate_set'::text, 'null'::jsonb), '[]'::jsonb) AS duplicate_set,
    COALESCE(NULLIF(mc.metadata -> 'epic_dependency_set'::text, 'null'::jsonb), '[]'::jsonb) AS epic_dependency_set,
    COALESCE(NULLIF(mc.metadata -> 'epics_set'::text, 'null'::jsonb), '[]'::jsonb) AS epics_set,
    COALESCE(NULLIF(mc.metadata -> 'fix_version_set'::text, 'null'::jsonb), '[]'::jsonb) AS fix_version_set,
    COALESCE(NULLIF(mc.metadata -> 'folder_set'::text, 'null'::jsonb), '[]'::jsonb) AS folder_set,
    COALESCE(NULLIF(mc.metadata -> 'functional_area_set'::text, 'null'::jsonb), '[]'::jsonb) AS functional_area_set,
    COALESCE(NULLIF(mc.metadata -> 'issue_split_set'::text, 'null'::jsonb), '[]'::jsonb) AS issue_split_set,
    COALESCE(NULLIF(mc.metadata -> 'issue_set'::text, 'null'::jsonb), '[]'::jsonb) AS issue_set,
    iv.item_version || COALESCE(NULLIF(mc.metadata -> 'item_version_set'::text, 'null'::jsonb), '[]'::jsonb) AS item_version_set,
    COALESCE(NULLIF(mc.metadata -> 'known_problem_sop_set'::text, 'null'::jsonb), '[]'::jsonb) AS known_problem_sop_set,
    COALESCE(NULLIF(mc.metadata -> 'label_set'::text, 'null'::jsonb), '[]'::jsonb) AS label_set,
    COALESCE(NULLIF(mc.metadata -> 'origin_to_cr_set'::text, 'null'::jsonb), '[]'::jsonb) AS origin_to_cr_set,
    COALESCE(NULLIF(mc.metadata -> 'parent_set'::text, 'null'::jsonb), '[]'::jsonb) AS parent_set,
    COALESCE(NULLIF(mc.metadata -> 'problem_incident_set'::text, 'null'::jsonb), '[]'::jsonb) AS problem_incident_set,
    COALESCE(NULLIF(mc.metadata -> 'reference_set'::text, 'null'::jsonb), '[]'::jsonb) AS reference_set,
    COALESCE(NULLIF(mc.metadata -> 'regression_suite_set'::text, 'null'::jsonb), '[]'::jsonb) AS regression_suite_set,
    COALESCE(NULLIF(mc.metadata -> 'remediating_product_ticket_set'::text, 'null'::jsonb), '[]'::jsonb) AS remediating_product_ticket_set,
    COALESCE(NULLIF(mc.metadata -> 'requirement_set'::text, 'null'::jsonb), '[]'::jsonb) AS requirement_set,
    COALESCE(NULLIF(mc.metadata -> 'story_dependency_set'::text, 'null'::jsonb), '[]'::jsonb) AS story_dependency_set,
    COALESCE(NULLIF(mc.metadata -> 'story_set'::text, 'null'::jsonb), '[]'::jsonb) AS story_set,
    COALESCE(NULLIF(mc.metadata -> 'sub_task_set'::text, 'null'::jsonb), '[]'::jsonb) AS sub_task_set,
    COALESCE(NULLIF(mc.metadata -> 'subcomponent_set'::text, 'null'::jsonb), '[]'::jsonb) AS subcomponent_set,
    COALESCE(NULLIF(mc.metadata -> 'teams_set'::text, 'null'::jsonb), '[]'::jsonb) AS teams_set,
    COALESCE(NULLIF(mc.metadata -> 'test_set'::text, 'null'::jsonb), '[]'::jsonb) AS test_set,
    COALESCE(NULLIF(mc.metadata -> 'original_issue_key'::text, 'null'::jsonb), '[]'::jsonb) AS original_issue_key_set,
    COALESCE(NULLIF(mc.metadata -> 'ai_genrated'::text, 'null'::jsonb), 'false'::jsonb) AS ai_genrated_set,
    COALESCE(NULLIF(mc.metadata -> 'ai_assisted'::text, 'null'::jsonb), 'false'::jsonb) AS ai_assisted_set,
    i.id AS item_id,
    i.project_id,
    i.priority_id,
    i.status_id,
    i.type_id,
    COALESCE(NULLIF(mc.metadata -> 'environment_set'::text, 'null'::jsonb), '[]'::jsonb) AS environment_set
   FROM athena_tms.item i
     LEFT JOIN athena_tms.status s ON i.status_id = s.id
     LEFT JOIN athena_core.project p ON i.project_id = p.id
     LEFT JOIN athena_tms.priority p2 ON i.priority_id = p2.id
     LEFT JOIN athena_tms.type it ON it.id = i.type_id
     LEFT JOIN athena.mv_item_metadata mc ON mc.item_id = i.id
     LEFT JOIN item_versions iv ON iv.item_id = i.id;
