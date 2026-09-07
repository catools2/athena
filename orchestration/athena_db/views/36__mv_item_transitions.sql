-- athena.mv_item_transitions
-- type      : MATERIALIZED VIEW
-- dep level : 5
-- depends on: mvw_item_transitions
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP MATERIALIZED VIEW IF EXISTS athena.mv_item_transitions CASCADE;

CREATE MATERIALIZED VIEW athena.mv_item_transitions
TABLESPACE pg_default
AS SELECT project_code,
    item_key,
    created_by,
    created_on,
    name,
    updated_by,
    updated_on,
    item_priority,
    item_status,
    item_type,
    rca_set,
    affected_version_set,
    blocks_documentation_set,
    blocks_set,
    bonfire_testing_set,
    cloner_set,
    component_version_set,
    component_set,
    cr_to_sop_set,
    dependency_set,
    depends_on_set,
    duplicate_set,
    epic_dependency_set,
    epics_set,
    fix_version_set,
    folder_set,
    functional_area_set,
    issue_split_set,
    issue_set,
    item_version_set,
    known_problem_sop_set,
    label_set,
    origin_to_cr_set,
    parent_set,
    problem_incident_set,
    reference_set,
    regression_suite_set,
    remediating_product_ticket_set,
    requirement_set,
    story_dependency_set,
    story_set,
    sub_task_set,
    subcomponent_set,
    teams_set,
    test_set,
    original_issue_key_set,
    ai_genrated_set,
    ai_assisted_set,
    item_id,
    project_id,
    priority_id,
    status_id,
    type_id,
    occurred,
    author,
    from_status,
    to_status,
    environment_set
   FROM athena.mvw_item_transitions
WITH DATA;
