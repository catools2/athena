-- athena.mvw_item_metadata
-- type      : VIEW
-- dep level : 0
-- depends on: (base tables only)
--
-- Generated from orchestration/athena_db/views.sql. Files are numbered in
-- dependency order; apply them in ascending numeric order.

DROP VIEW IF EXISTS athena.mvw_item_metadata CASCADE;

CREATE OR REPLACE VIEW athena.mvw_item_metadata
AS WITH metadata_values AS (
         SELECT t.id,
            t.name,
            t.value
           FROM ( SELECT metadata.id,
                    metadata.name,
                    TRIM(BOTH '[]" '::text FROM unnest(regexp_split_to_array(metadata.value::text,
                      '[,\[\]]'::text))) AS value
                   FROM athena_tms.metadata
                  WHERE metadata.name::text = ANY (ARRAY['Affected Version/s'::character varying::text,
                    'Affected Version'::character varying::text,
                    'AffectedVersion'::character varying::text,
                    'Functional Area'::character varying::text])) t
          WHERE length(t.value) > 0
        UNION
         SELECT metadata.id,
            metadata.name,
            metadata.value
           FROM athena_tms.metadata
          WHERE metadata.name::text <> ALL (ARRAY['Affected Version/s'::character varying::text,
            'Affected Version'::character varying::text,
            'AffectedVersion'::character varying::text,
            'Functional Area'::character varying::text])
), src AS (
         -- Deduplicate ONCE, after normalisation, instead of running 37 independent
         -- array_agg(DISTINCT ...) sorts. Each of those forces a per-group tuplesort
         -- and blocks hash aggregation outright, so the whole join output had to be
         -- sorted first. Every metadata name maps to exactly one normalisation, so
         -- folding them into a CASE preserves "dedupe on the transformed value".
         SELECT imm.item_id,
            m.name,
            CASE
              WHEN m.name::text = ANY (ARRAY['Epic Link'::character varying::text,
                'IssueLink'::character varying::text,
                'Link-Blocks'::character varying::text,
                'Link-Blocks Documentation'::character varying::text,
                'Link-Bonfire Testing'::character varying::text,
                'Link-CR to SOP'::character varying::text,
                'Link-Cloners'::character varying::text,
                'Link-Dependency'::character varying::text,
                'Link-Depends On'::character varying::text,
                'Link-Duplicate'::character varying::text,
                'Link-Epic Dependency'::character varying::text,
                'Link-Issue split'::character varying::text,
                'Link-Known Problem SOP(s)'::character varying::text,
                'Link-Origin to CR'::character varying::text,
                'Link-Problem/Incident'::character varying::text,
                'Link-Reference'::character varying::text,
                'Link-Regression Suite'::character varying::text,
                'Link-Remediating Product Ticket'::character varying::text,
                'Link-Requirement'::character varying::text,
                'Link-Story Dependency'::character varying::text,
                'Link-Test'::character varying::text,
                'Original Issue Key'::character varying::text,
                'Parent'::character varying::text,
                'Parent Link'::character varying::text]) THEN replace(replace(m.value, '"'::text, ''::text),
                'https://jira.example.com/browse/'::text,
                ''::text)
              WHEN m.name::text = ANY (ARRAY['Scrum Team'::character varying::text,
                'Team'::character varying::text]) THEN replace(m.value,
                'Legacy '::text,
                ''::text)
              ELSE m.value
            END AS value
           FROM athena_tms.item_metadata_mid imm
             LEFT JOIN metadata_values m ON m.id = imm.metadata_id
          GROUP BY 1, 2, 3
), traced AS (
         -- Resolve every IssueLink to the type of the item it points at. Without this every link
         -- an item carries -- the story it implements, the bug it verifies, the epic it sits under
         -- -- collapses into a single untyped issue_set, so "what does this test cover" and "what
         -- does this test regress" cannot be told apart.
         --
         -- Type alone is not enough for tests: athena_tms.type 'Test' covers both Jira Test issues
         -- (DEMO-####) and Zephyr Scale test cases (DEMO-T####), which are different things. The key
         -- shape is what separates them.
         SELECT s.item_id,
            CASE
              WHEN li.id IS NULL THEN 'unresolved'::text
              WHEN lt.name::text = 'Test'::text AND s.value ~ '^[A-Z]+-T[0-9]+$'::text
                THEN 'scale_test'::text
              WHEN lt.name::text = 'Test'::text THEN 'jira_test'::text
              ELSE lower(replace(replace(lt.name::text, ' '::text, '_'::text), '-'::text, '_'::text))
            END AS linked_type,
            s.value
           FROM src s
             LEFT JOIN athena_tms.item li ON li.code::text = s.value
             LEFT JOIN athena_tms.type lt ON lt.id = li.type_id
          WHERE s.name::text = 'IssueLink'::text AND s.value IS NOT NULL
), traced_agg AS (
         -- Emitted as flat traced_<type>_set keys built from the data rather than a hard-coded
         -- list, so a new Jira issue type shows up without another view change.
         SELECT g.item_id,
            jsonb_object_agg('traced_' || g.linked_type || '_set', to_jsonb(g.vals)) AS traced
           FROM ( SELECT traced.item_id,
                    traced.linked_type,
                    array_agg(DISTINCT traced.value) AS vals
                   FROM traced
                  GROUP BY 1, 2) g
          GROUP BY g.item_id
)
 SELECT base.item_id,
    base.metadata || COALESCE(ta.traced, '{}'::jsonb) AS metadata
   FROM ( SELECT src.item_id,
    jsonb_build_object(
      'rca_set', array_agg(src.value) FILTER (WHERE src.name::text = 'RCA'::text),
      'affected_version_set', array_agg(src.value) FILTER (WHERE src.name::text = ANY (ARRAY['Affected Version'::character varying::text, 'Affected Version/s'::character varying::text, 'AffectedVersion'::character varying::text])),
      'blocks_documentation_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-Blocks Documentation'::text),
      'blocks_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-Blocks'::text),
      'bonfire_testing_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-Bonfire Testing'::text),
      'cloner_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-Cloners'::text),
      'component_version_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Component Version'::text),
      'environment_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Environment'::text),
      'component_set', array_agg(src.value) FILTER (WHERE src.name::text = ANY (ARRAY['Component'::character varying::text, 'Components'::character varying::text])),
      'cr_to_sop_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-CR to SOP'::text),
      'dependency_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-Dependency'::text),
      'depends_on_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-Depends On'::text),
      'duplicate_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-Duplicate'::text),
      'epic_dependency_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-Epic Dependency'::text),
      'epics_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Epic Link'::text),
      'fix_version_set', array_agg(src.value) FILTER (WHERE src.name::text = ANY (ARRAY['FixVersion'::character varying::text, 'Fix Version/s'::character varying::text])),
      'folder_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Folder'::text),
      'functional_area_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Functional Area'::text),
      'issue_split_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-Issue split'::text),
      'issue_set', array_agg(src.value) FILTER (WHERE src.name::text = 'IssueLink'::text),
      'item_version_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Version'::text),
      'known_problem_sop_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-Known Problem SOP(s)'::text),
      'label_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Label'::text),
      'origin_to_cr_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-Origin to CR'::text),
      'parent_set', array_agg(src.value) FILTER (WHERE src.name::text = ANY (ARRAY['Parent'::character varying::text, 'Parent Link'::character varying::text])),
      'problem_incident_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-Problem/Incident'::text),
      'reference_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-Reference'::text),
      'regression_suite_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-Regression Suite'::text),
      'remediating_product_ticket_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-Remediating Product Ticket'::text),
      'requirement_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-Requirement'::text),
      'story_dependency_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-Story Dependency'::text),
      'story_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Story'::text),
      'sub_task_set', array_agg(src.value) FILTER (WHERE src.name::text = 'SubTask'::text),
      'subcomponent_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Subcomponent'::text),
      'teams_set', array_agg(src.value) FILTER (WHERE src.name::text = ANY (ARRAY['Scrum Team'::character varying::text, 'Team'::character varying::text])),
      'test_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Link-Test'::text),
      'original_issue_key', array_agg(src.value) FILTER (WHERE src.name::text = 'Original Issue Key'::text),
      'resolution', max(src.value) FILTER (WHERE src.name::text = 'Resolution'::text),
      'epic_name', max(src.value) FILTER (WHERE src.name::text = 'Epic Name'::text),
      'assignee', max(src.value) FILTER (WHERE src.name::text = 'Assignee'::text)
    ) ||
    jsonb_build_object(
      'reporter', max(src.value) FILTER (WHERE src.name::text = 'Reporter'::text),
      'owner', max(src.value) FILTER (WHERE src.name::text = 'Owner'::text),
      'created_by', max(src.value) FILTER (WHERE src.name::text = 'CreatedBy'::text),
      'original_component', max(src.value) FILTER (WHERE src.name::text = 'Original Component'::text),
      'time_spent', max(src.value) FILTER (WHERE src.name::text = 'Time Spent'::text),
      'team_submitting', max(src.value) FILTER (WHERE src.name::text = 'Team Submitting'::text),
      'actual_story_points', max(src.value) FILTER (WHERE src.name::text = 'Actual Story Points'::text),
      'completed_story_points', max(src.value) FILTER (WHERE src.name::text = 'Completed Story Points'::text),
      'story_points', max(src.value) FILTER (WHERE src.name::text = 'Story Points'::text),
      'baseline_story_points', max(src.value) FILTER (WHERE src.name::text = 'Baseline Story Points'::text),
      'last_test_result_status', max(src.value) FILTER (WHERE src.name::text = 'LastTestResultStatus'::text),
      'qaa', max(src.value) FILTER (WHERE src.name::text = 'QAA'::text),
      'test_case_category', max(src.value) FILTER (WHERE src.name::text = 'Test Case Category'::text),
      'automated', max(src.value) FILTER (WHERE src.name::text = 'Automated / Not Automated'::text),
      'supporting_team', max(src.value) FILTER (WHERE src.name::text = 'Supporting Team'::text),
      'supporting_pod', max(src.value) FILTER (WHERE src.name::text = 'Supporting Pod'::text),
      'sprint', max(src.value) FILTER (WHERE src.name::text = 'Sprint'::text),
      'automation_project', max(src.value) FILTER (WHERE src.name::text = 'Automation Project'::text),
      'epic_status', max(src.value) FILTER (WHERE src.name::text = 'Epic Status'::text),
      'environment', max(src.value) FILTER (WHERE src.name::text = 'Environment'::text),
      'regression_test', max(src.value) FILTER (WHERE src.name::text = 'Regression Test'::text),
      'functional_area', max(src.value) FILTER (WHERE src.name::text = 'Functional Area'::text),
      'production_support_origin', max(src.value) FILTER (WHERE src.name::text = 'Production Support Origin'::text),
      'rca_required', max(src.value) FILTER (WHERE src.name::text = 'RCA Required'::text),
      'rca_status', max(src.value) FILTER (WHERE src.name::text = 'RCA Status'::text),
      'risk_score', max(src.value) FILTER (WHERE src.name::text = 'Risk Score'::text),
      'ai_generated', COALESCE(bool_or(src.name::text = 'Label'::text
        AND regexp_replace(lower(src.value), '[^a-z0-9]'::text, ''::text, 'g'::text) = ANY (ARRAY['aigenerated'::text, 'genaigenerated'::text])), false),
      'ai_reviewed', COALESCE(bool_or(src.name::text = 'Label'::text
        AND regexp_replace(lower(src.value), '[^a-z0-9]'::text, ''::text, 'g'::text) = ANY (ARRAY['aireviewed'::text, 'genaireviewed'::text, 'aireviewd'::text])), false),
      'ai_assisted', COALESCE(bool_or(src.name::text = 'Label'::text
        AND regexp_replace(lower(src.value), '[^a-z0-9]'::text, ''::text, 'g'::text) = ANY (ARRAY['aiassisted'::text, 'genaiassisted'::text])), false),
      'ai_fixed', COALESCE(bool_or(src.name::text = 'Label'::text
        AND regexp_replace(lower(src.value), '[^a-z0-9]'::text, ''::text, 'g'::text) = ANY (ARRAY['aifixed'::text, 'genaifixed'::text])), false),
      'ai_label_set', array_agg(src.value) FILTER (WHERE src.name::text = 'Label'::text
        AND regexp_replace(lower(src.value), '[^a-z0-9]'::text, ''::text, 'g'::text) ~ '^(gen)?ai(generated|reviewed|reviewd|assisted|fixed)$'::text),
      'ai_generated_on', max(src.value) FILTER (WHERE src.name::text = 'AIGeneratedOn'::text),
      'ai_reviewed_on', max(src.value) FILTER (WHERE src.name::text = 'AIReviewedOn'::text),
      'ai_assisted_on', max(src.value) FILTER (WHERE src.name::text = 'AIAssistedOn'::text),
      'ai_fixed_on', max(src.value) FILTER (WHERE src.name::text = 'AIFixedOn'::text)
    ) AS metadata
           FROM src
          GROUP BY src.item_id) base
     LEFT JOIN traced_agg ta ON ta.item_id = base.item_id;
