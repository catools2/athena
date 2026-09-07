-- athena.mvw_item_transitions
-- type      : VIEW
-- dep level : 4
-- depends on: mv_items
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.mvw_item_transitions CASCADE;

CREATE OR REPLACE VIEW athena.mvw_item_transitions
AS WITH items_without_status_transition AS (
         SELECT i1.created_by,
            i1.created_on,
            i1.id,
            i1.priority_id,
            i1.project_id,
            i1.status_id,
            i1.type_id,
            i1.updated_by,
            i1.updated_on,
            i1.code,
            i1.name
           FROM athena_tms.item i1
          WHERE NOT (EXISTS ( SELECT 1
                   FROM athena_tms.status_transition st_1
                  WHERE st_1.item_id = i1.id))
        ), normalized_status_transition AS (
         SELECT DISTINCT iwst.id AS item_id,
            iwst.created_by AS author,
            iwst.created_on AS occurred,
            'UNSET'::text AS from_status,
            s2.name AS to_status
           FROM items_without_status_transition iwst
             LEFT JOIN athena_tms.status s2 ON s2.id = iwst.status_id
        UNION ALL
         SELECT DISTINCT st_1.item_id,
            st_1.author,
            st_1.occurred,
            s1.name AS from_status,
            s2.name AS to_status
           FROM athena_tms.status_transition st_1
             LEFT JOIN athena_tms.status s1 ON s1.id = st_1.from_status
             LEFT JOIN athena_tms.status s2 ON s2.id = st_1.to_status
          WHERE NOT (EXISTS ( SELECT 1
                   FROM items_without_status_transition iwst
                  WHERE iwst.id = st_1.item_id))
        )
 SELECT i.project_code,
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
    st.occurred,
    st.author,
    st.from_status,
    st.to_status,
    i.environment_set
   FROM athena.mv_items i
     JOIN normalized_status_transition st ON st.item_id = i.item_id;
