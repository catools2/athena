-- athena.mvw_inventory_trend
-- type      : VIEW
-- dep level : 6
-- depends on: mv_item_transitions
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.mvw_inventory_trend CASCADE;

CREATE OR REPLACE VIEW athena.mvw_inventory_trend
AS WITH transitions AS (
         SELECT i.project_code,
            i.item_id,
            i.item_key,
            i.name,
            i.item_type,
            i.item_status,
            i.created_on,
            i.item_priority,
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
            i.occurred,
            st.status_name,
            st.direction
           FROM athena.mv_item_transitions i
             CROSS JOIN LATERAL ( VALUES (i.to_status,'added'::text),
               (i.from_status,'removed'::text)) st(status_name,
               direction)
        )
 SELECT project_code,
    item_id,
    item_key,
    name,
    item_type,
    item_status,
    created_on,
    item_priority,
    affected_version_set,
    blocks_documentation_set,
    blocks_set,
    bonfire_testing_set,
    cloner_set,
    component_version_set,
    component_set,
    cr_to_sop_set,
    environment_set,
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
    occurred,
        CASE
            WHEN status_name::text = 'Acceptance Review'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS acceptance_review,
        CASE
            WHEN status_name::text = 'Accepted'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS accepted,
        CASE
            WHEN status_name::text = 'Analyze'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS "analyze",
        CASE
            WHEN status_name::text = 'Archived'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS archived,
        CASE
            WHEN status_name::text = 'Automated'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS automated,
        CASE
            WHEN status_name::text = 'Automation Candidate'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS automation_candidate,
        CASE
            WHEN status_name::text = 'Automation In Progress'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS automation_in_progress,
        CASE
            WHEN status_name::text = 'Awaiting Approval'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS awaiting_approval,
        CASE
            WHEN status_name::text = 'Awaiting CAB Approval'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS awaiting_cab_approval,
        CASE
            WHEN status_name::text = 'Awaiting Product Validation'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS awaiting_product_validation,
        CASE
            WHEN status_name::text = 'Awaiting Review'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS awaiting_review,
        CASE
            WHEN status_name::text = 'Backlog'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS backlog,
        CASE
            WHEN status_name::text = 'Blocked'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS blocked,
        CASE
            WHEN status_name::text = 'Breakdown'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS breakdown,
        CASE
            WHEN status_name::text = 'Closed'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS closed,
        CASE
            WHEN status_name::text = 'Closed Not Implemented'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS closed_not_implemented,
        CASE
            WHEN status_name::text = 'Completed'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS completed,
        CASE
            WHEN status_name::text = 'Confirm Approval'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS confirm_approval,
        CASE
            WHEN status_name::text = 'Converted'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS converted,
        CASE
            WHEN status_name::text = 'Deferred'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS deferred,
        CASE
            WHEN status_name::text = 'Delivery Candidate'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS delivery_candidate,
        CASE
            WHEN status_name::text = 'Delivery Planning'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS delivery_planning,
        CASE
            WHEN status_name::text = 'Detailed Requirements'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS detailed_requirements,
        CASE
            WHEN status_name::text = 'Dev Automated'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS dev_automated,
        CASE
            WHEN status_name::text = 'Dev Automation Candidate'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS dev_automation_candidate,
        CASE
            WHEN status_name::text = 'Dev Automation In Progress'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS dev_automation_in_progress,
        CASE
            WHEN status_name::text = 'Dev Complete'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS dev_complete,
        CASE
            WHEN status_name::text = 'Dev In Progress'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS dev_in_progress,
        CASE
            WHEN status_name::text = 'Disabled'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS disabled,
        CASE
            WHEN status_name::text = 'Discovery'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS discovery,
        CASE
            WHEN status_name::text = 'Done'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS done,
        CASE
            WHEN status_name::text = ANY (ARRAY['TESTCASE.STATUS.DEPRECATED'::character varying::text,
              'Draft'::character varying::text]) THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS draft,
        CASE
            WHEN status_name::text = 'Duplicate'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS duplicate,
        CASE
            WHEN status_name::text = 'Estimation'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS estimation,
        CASE
            WHEN status_name::text = 'Failed'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS failed,
        CASE
            WHEN status_name::text = 'Final Review'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS final_review,
        CASE
            WHEN status_name::text = 'First Review'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS first_review,
        CASE
            WHEN status_name::text = 'Fixed'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS fixed,
        CASE
            WHEN status_name::text = 'Fix Identified'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS fix_identified,
        CASE
            WHEN status_name::text = 'Grooming'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS grooming,
        CASE
            WHEN status_name::text = 'Grooming Ready'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS grooming_ready,
        CASE
            WHEN status_name::text = 'HC Review'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS hc_review,
        CASE
            WHEN status_name::text = 'Implementation'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS implementation,
        CASE
            WHEN status_name::text = 'In Dev'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS in_dev,
        CASE
            WHEN status_name::text = 'In Peer Review'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS in_peer_review,
        CASE
            WHEN status_name::text = 'In Planning'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS in_planning,
        CASE
            WHEN status_name::text = 'In Progress'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS in_progress,
        CASE
            WHEN status_name::text = 'Inputs Awaited'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS inputs_awaited,
        CASE
            WHEN status_name::text = 'In QA'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS in_qa,
        CASE
            WHEN status_name::text = 'In Queue'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS in_queue,
        CASE
            WHEN status_name::text = 'Invalid'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS invalid,
        CASE
            WHEN status_name::text = 'Investigating/Scoping'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS investigating_scoping,
        CASE
            WHEN status_name::text = 'Investigation In Progress'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS investigation_in_progress,
        CASE
            WHEN status_name::text = 'Manual'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS manual,
        CASE
            WHEN status_name::text = 'Need Review'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS need_review,
        CASE
            WHEN status_name::text = 'Needs More Info'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS needs_more_info,
        CASE
            WHEN status_name::text = 'New'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS new,
        CASE
            WHEN status_name::text = 'Obsolete'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS obsolete,
        CASE
            WHEN status_name::text = 'On Hold'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS on_hold,
        CASE
            WHEN status_name::text = 'Open'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS open,
        CASE
            WHEN status_name::text = 'Open in Sprint'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS open_in_sprint,
        CASE
            WHEN status_name::text = 'Pending'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS pending,
        CASE
            WHEN status_name::text = 'Pending Deferred'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS pending_deferred,
        CASE
            WHEN status_name::text = 'Pending Verification'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS pending_verification,
        CASE
            WHEN status_name::text = 'Perf Automated'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS perf_automated,
        CASE
            WHEN status_name::text = 'Playwright Automated'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS playwright_automated,
        CASE
            WHEN status_name::text = 'Playwright Automation Candidate'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS playwright_automation_candidate,
        CASE
            WHEN status_name::text = 'Playwright Automation In Progress'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS playwright_automation_in_progress,
        CASE
            WHEN status_name::text = 'PO Verify'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS po_verify,
        CASE
            WHEN status_name::text = 'Preparing'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS preparing,
        CASE
            WHEN status_name::text = 'Prioritization/Scheduling'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS prioritization_scheduling,
        CASE
            WHEN status_name::text = 'Production Ready'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS production_ready,
        CASE
            WHEN status_name::text = 'QA in Progress'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS qa_in_progress,
        CASE
            WHEN status_name::text = 'QA Ready'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS qa_ready,
        CASE
            WHEN status_name::text = 'Ready'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS ready,
        CASE
            WHEN status_name::text = 'Ready for Deploy'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS ready_for_deploy,
        CASE
            WHEN status_name::text = 'Ready For QA'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS ready_for_qa,
        CASE
            WHEN status_name::text = 'Ready for Smoke Test'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS ready_for_smoke_test,
        CASE
            WHEN status_name::text = 'Ready to Groom'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS ready_to_groom,
        CASE
            WHEN status_name::text = 'Refinement Ready'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS refinement_ready,
        CASE
            WHEN status_name::text = 'Rejected'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS rejected,
        CASE
            WHEN status_name::text = 'Released To QA'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS released_to_qa,
        CASE
            WHEN status_name::text = 'Release Planning Ready'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS release_planning_ready,
        CASE
            WHEN status_name::text = 'Reopened'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS reopened,
        CASE
            WHEN status_name::text = 'Requirements Gathering'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS requirements_gathering,
        CASE
            WHEN status_name::text = 'Requirements in Progress'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS requirements_in_progress,
        CASE
            WHEN status_name::text = 'Requires Investigation'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS requires_investigation,
        CASE
            WHEN status_name::text = 'Resolved'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS resolved,
        CASE
            WHEN status_name::text = 'Review'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS review,
        CASE
            WHEN status_name::text = 'Reviewed'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS reviewed,
        CASE
            WHEN status_name::text = 'Review in Progress'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS review_in_progress,
        CASE
            WHEN status_name::text = 'Review Passed'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS review_passed,
        CASE
            WHEN status_name::text = 'Rework Required'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS rework_required,
        CASE
            WHEN status_name::text = 'Rework Required (Automated)'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS rework_required_automated,
        CASE
            WHEN status_name::text = 'Scheduled'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS scheduled,
        CASE
            WHEN status_name::text = 'Smoke Testing'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS smoke_testing,
        CASE
            WHEN status_name::text = 'Sprint Ready'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS sprint_ready,
        CASE
            WHEN status_name::text = 'Staging Ready'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS staging_ready,
        CASE
            WHEN status_name::text = 'Success'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS success,
        CASE
            WHEN status_name::text = 'Test'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS test,
        CASE
            WHEN status_name::text = 'TESTCASE.STATUS.DRAFT'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS status_draft,
        CASE
            WHEN status_name::text = 'Testing in Progress'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS testing_in_progress,
        CASE
            WHEN status_name::text = 'Test Passed'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS test_passed,
        CASE
            WHEN status_name::text = 'To Do'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS to_do,
        CASE
            WHEN status_name::text = 'Under Review'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS under_review,
        CASE
            WHEN status_name::text = 'Unresolved'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS unresolved,
        CASE
            WHEN status_name::text = 'UNSET'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS unset,
        CASE
            WHEN status_name::text = 'Validated'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS validated,
        CASE
            WHEN status_name::text = 'Verification Complete'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS verification_complete,
        CASE
            WHEN status_name::text = 'Verification in Progress'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS verification_in_progress,
        CASE
            WHEN status_name::text = 'Verify'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS verify,
        CASE
            WHEN status_name::text = 'Waiting for Assignee'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS waiting_for_assignee,
        CASE
            WHEN status_name::text = 'Waiting on Requester'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS waiting_on_requester,
        CASE
            WHEN status_name::text = 'Waiting on Update'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS waiting_on_update,
        CASE
            WHEN status_name::text = 'Work Complete'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS work_complete,
        CASE
            WHEN status_name::text = 'Working'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS working,
        CASE
            WHEN status_name::text = 'Work in Progress'::text THEN
            CASE
                WHEN direction = 'added'::text THEN 1
                ELSE '-1'::integer
            END
            ELSE 0
        END AS work_in_progress
   FROM transitions;
