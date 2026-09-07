// ==========================================================
// Métis schema — GENERATED from metis_mcp/ontology/labels.py
// Do not hand-edit: regenerate with
//     python3 -m metis_mcp.ontology.schema --write
// Hand-edits are drift, and test_ontology.py will fail on them.
// ==========================================================


// ---- EDITION: community ----
// Property-existence constraints are an ENTERPRISE-only feature. Under
// Community (spec C1/DD-2) they cannot be created, so required-property
// enforcement lives in metis_mcp/ontology/validation.py instead.
//
// This is the same split spec ONT-012 already makes for enum membership: the
// database enforces what it can, the application gate enforces the rest, and
// both are required. Verified against a real Neo4j 5 Community instance --
// attempting them there fails with "requires Neo4j Enterprise Edition".

// ---- Part 1: node constraints and indexes ----

// AcceptanceCriterion — One atomic, testable condition
CREATE CONSTRAINT acceptance_criterion_id_unique IF NOT EXISTS FOR (n:AcceptanceCriterion) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT acceptance_criterion_source_episode_id_required IF NOT EXISTS FOR (n:AcceptanceCriterion) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT acceptance_criterion_name_required IF NOT EXISTS FOR (n:AcceptanceCriterion) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT acceptance_criterion_revision_required IF NOT EXISTS FOR (n:AcceptanceCriterion) REQUIRE n.revision IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT acceptance_criterion_search_text_required IF NOT EXISTS FOR (n:AcceptanceCriterion) REQUIRE n.search_text IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT acceptance_criterion_valid_from_required IF NOT EXISTS FOR (n:AcceptanceCriterion) REQUIRE n.valid_from IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT acceptance_criterion_valid_to_required IF NOT EXISTS FOR (n:AcceptanceCriterion) REQUIRE n.valid_to IS NOT NULL;
CREATE INDEX acceptance_criterion_lifecycle_state_lookup IF NOT EXISTS FOR (n:AcceptanceCriterion) ON (n.lifecycle_state);
CREATE INDEX acceptance_criterion_provenance_lookup IF NOT EXISTS FOR (n:AcceptanceCriterion) ON (n.provenance);
CREATE INDEX acceptance_criterion_valid_to_lookup IF NOT EXISTS FOR (n:AcceptanceCriterion) ON (n.valid_to);
CREATE INDEX acceptance_criterion_m_project_lookup IF NOT EXISTS FOR (n:AcceptanceCriterion) ON (n.m_project);
CREATE INDEX acceptance_criterion_source_episode_id_lookup IF NOT EXISTS FOR (n:AcceptanceCriterion) ON (n.source_episode_id);
//   AcceptanceCriterion.provenance ∈ {code_derived, human_confirmed, independently_authored} — enforced by ontology.validation, not by Neo4j
//   AcceptanceCriterion.lifecycle_state ∈ {Quarantine, Approved, Disputed, Rejected, Deprecated} — enforced by ontology.validation, not by Neo4j

// Action — An affordance a person can invoke — the thing a click lands on
CREATE CONSTRAINT action_id_unique IF NOT EXISTS FOR (n:Action) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT action_source_episode_id_required IF NOT EXISTS FOR (n:Action) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT action_name_required IF NOT EXISTS FOR (n:Action) REQUIRE n.name IS NOT NULL;
CREATE INDEX action_m_project_lookup IF NOT EXISTS FOR (n:Action) ON (n.m_project);
CREATE INDEX action_lifecycle_state_lookup IF NOT EXISTS FOR (n:Action) ON (n.lifecycle_state);
CREATE INDEX action_source_episode_id_lookup IF NOT EXISTS FOR (n:Action) ON (n.source_episode_id);

// ApiCall — A Transition on the api surface: one call and its outcome
CREATE CONSTRAINT api_call_id_unique IF NOT EXISTS FOR (n:ApiCall) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT api_call_source_episode_id_required IF NOT EXISTS FOR (n:ApiCall) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT api_call_name_required IF NOT EXISTS FOR (n:ApiCall) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT api_call_c_trigger_required IF NOT EXISTS FOR (n:ApiCall) REQUIRE n.c_trigger IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT api_call_b_guard_expression_required IF NOT EXISTS FOR (n:ApiCall) REQUIRE n.b_guard_expression IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT api_call_b_implementation_status_required IF NOT EXISTS FOR (n:ApiCall) REQUIRE n.b_implementation_status IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT api_call_b_surface_required IF NOT EXISTS FOR (n:ApiCall) REQUIRE n.b_surface IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT api_call_model_id_required IF NOT EXISTS FOR (n:ApiCall) REQUIRE n.model_id IS NOT NULL;
CREATE INDEX api_call_m_project_lookup IF NOT EXISTS FOR (n:ApiCall) ON (n.m_project);
CREATE INDEX api_call_lifecycle_state_lookup IF NOT EXISTS FOR (n:ApiCall) ON (n.lifecycle_state);
CREATE INDEX api_call_source_episode_id_lookup IF NOT EXISTS FOR (n:ApiCall) ON (n.source_episode_id);
//   ApiCall.b_surface ∈ {api} — enforced by ontology.validation, not by Neo4j

// BusinessArea — One business domain grouping entities and requirements
CREATE CONSTRAINT business_area_id_unique IF NOT EXISTS FOR (n:BusinessArea) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT business_area_source_episode_id_required IF NOT EXISTS FOR (n:BusinessArea) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT business_area_name_required IF NOT EXISTS FOR (n:BusinessArea) REQUIRE n.name IS NOT NULL;
CREATE INDEX business_area_name_lookup IF NOT EXISTS FOR (n:BusinessArea) ON (n.name);
CREATE INDEX business_area_m_project_lookup IF NOT EXISTS FOR (n:BusinessArea) ON (n.m_project);
CREATE INDEX business_area_lifecycle_state_lookup IF NOT EXISTS FOR (n:BusinessArea) ON (n.lifecycle_state);
CREATE INDEX business_area_source_episode_id_lookup IF NOT EXISTS FOR (n:BusinessArea) ON (n.source_episode_id);

// BusinessEntity — One business noun: what it is, and what acting on it changes
CREATE CONSTRAINT business_entity_id_unique IF NOT EXISTS FOR (n:BusinessEntity) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT business_entity_source_episode_id_required IF NOT EXISTS FOR (n:BusinessEntity) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT business_entity_name_required IF NOT EXISTS FOR (n:BusinessEntity) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT business_entity_description_required IF NOT EXISTS FOR (n:BusinessEntity) REQUIRE n.description IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT business_entity_search_text_required IF NOT EXISTS FOR (n:BusinessEntity) REQUIRE n.search_text IS NOT NULL;
CREATE INDEX business_entity_name_lookup IF NOT EXISTS FOR (n:BusinessEntity) ON (n.name);
CREATE INDEX business_entity_m_project_lookup IF NOT EXISTS FOR (n:BusinessEntity) ON (n.m_project);
CREATE INDEX business_entity_lifecycle_state_lookup IF NOT EXISTS FOR (n:BusinessEntity) ON (n.lifecycle_state);
CREATE INDEX business_entity_source_episode_id_lookup IF NOT EXISTS FOR (n:BusinessEntity) ON (n.source_episode_id);

// Check — One condition evaluated on a path — a guard's own evidence
CREATE CONSTRAINT check_id_unique IF NOT EXISTS FOR (n:Check) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT check_source_episode_id_required IF NOT EXISTS FOR (n:Check) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT check_name_required IF NOT EXISTS FOR (n:Check) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT check_expression_required IF NOT EXISTS FOR (n:Check) REQUIRE n.expression IS NOT NULL;
CREATE INDEX check_dimension_class_lookup IF NOT EXISTS FOR (n:Check) ON (n.dimension_class);
CREATE INDEX check_order_lookup IF NOT EXISTS FOR (n:Check) ON (n.order);
CREATE INDEX check_m_project_lookup IF NOT EXISTS FOR (n:Check) ON (n.m_project);
CREATE INDEX check_lifecycle_state_lookup IF NOT EXISTS FOR (n:Check) ON (n.lifecycle_state);
CREATE INDEX check_source_episode_id_lookup IF NOT EXISTS FOR (n:Check) ON (n.source_episode_id);

// Class — One declared type: a controller, a service, or a payload schema
CREATE CONSTRAINT class_id_unique IF NOT EXISTS FOR (n:Class) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT class_source_episode_id_required IF NOT EXISTS FOR (n:Class) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT class_name_required IF NOT EXISTS FOR (n:Class) REQUIRE n.name IS NOT NULL;
CREATE INDEX class_package_lookup IF NOT EXISTS FOR (n:Class) ON (n.package);
CREATE INDEX class_m_project_lookup IF NOT EXISTS FOR (n:Class) ON (n.m_project);
CREATE INDEX class_lifecycle_state_lookup IF NOT EXISTS FOR (n:Class) ON (n.lifecycle_state);
CREATE INDEX class_source_episode_id_lookup IF NOT EXISTS FOR (n:Class) ON (n.source_episode_id);

// CodeItem — Evidence anchor for one analysed source tree at one revision
CREATE CONSTRAINT code_item_id_unique IF NOT EXISTS FOR (n:CodeItem) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT code_item_source_episode_id_required IF NOT EXISTS FOR (n:CodeItem) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT code_item_name_required IF NOT EXISTS FOR (n:CodeItem) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT code_item_repo_id_required IF NOT EXISTS FOR (n:CodeItem) REQUIRE n.repo_id IS NOT NULL;
CREATE INDEX code_item_repo_id_lookup IF NOT EXISTS FOR (n:CodeItem) ON (n.repo_id);
CREATE INDEX code_item_revision_lookup IF NOT EXISTS FOR (n:CodeItem) ON (n.revision);
CREATE INDEX code_item_m_project_lookup IF NOT EXISTS FOR (n:CodeItem) ON (n.m_project);
CREATE INDEX code_item_lifecycle_state_lookup IF NOT EXISTS FOR (n:CodeItem) ON (n.lifecycle_state);
CREATE INDEX code_item_source_episode_id_lookup IF NOT EXISTS FOR (n:CodeItem) ON (n.source_episode_id);

// Component — One deployable component at one commit (spec D-6)
CREATE CONSTRAINT component_id_unique IF NOT EXISTS FOR (n:Component) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT component_source_episode_id_required IF NOT EXISTS FOR (n:Component) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT component_name_required IF NOT EXISTS FOR (n:Component) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT component_journey_required IF NOT EXISTS FOR (n:Component) REQUIRE n.journey IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT component_surface_required IF NOT EXISTS FOR (n:Component) REQUIRE n.surface IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT component_version_required IF NOT EXISTS FOR (n:Component) REQUIRE n.version IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT component_component_required IF NOT EXISTS FOR (n:Component) REQUIRE n.component IS NOT NULL;
CREATE INDEX component_component_lookup IF NOT EXISTS FOR (n:Component) ON (n.component);
CREATE INDEX component_journey_lookup IF NOT EXISTS FOR (n:Component) ON (n.journey);
CREATE INDEX component_surface_lookup IF NOT EXISTS FOR (n:Component) ON (n.surface);
CREATE INDEX component_version_lookup IF NOT EXISTS FOR (n:Component) ON (n.version);
CREATE INDEX component_commit_sha_lookup IF NOT EXISTS FOR (n:Component) ON (n.commit_sha);
CREATE INDEX component_m_project_lookup IF NOT EXISTS FOR (n:Component) ON (n.m_project);
CREATE INDEX component_lifecycle_state_lookup IF NOT EXISTS FOR (n:Component) ON (n.lifecycle_state);
CREATE INDEX component_source_episode_id_lookup IF NOT EXISTS FOR (n:Component) ON (n.source_episode_id);
//   Component.surface ∈ {ui, api} — enforced by ontology.validation, not by Neo4j

// ConfluenceItem — Evidence anchor for one Confluence page
CREATE CONSTRAINT confluence_item_id_unique IF NOT EXISTS FOR (n:ConfluenceItem) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT confluence_item_source_episode_id_required IF NOT EXISTS FOR (n:ConfluenceItem) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT confluence_item_name_required IF NOT EXISTS FOR (n:ConfluenceItem) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT confluence_item_page_id_required IF NOT EXISTS FOR (n:ConfluenceItem) REQUIRE n.page_id IS NOT NULL;
CREATE INDEX confluence_item_page_id_lookup IF NOT EXISTS FOR (n:ConfluenceItem) ON (n.page_id);
CREATE INDEX confluence_item_space_lookup IF NOT EXISTS FOR (n:ConfluenceItem) ON (n.space);
CREATE INDEX confluence_item_m_project_lookup IF NOT EXISTS FOR (n:ConfluenceItem) ON (n.m_project);
CREATE INDEX confluence_item_lifecycle_state_lookup IF NOT EXISTS FOR (n:ConfluenceItem) ON (n.lifecycle_state);
CREATE INDEX confluence_item_source_episode_id_lookup IF NOT EXISTS FOR (n:ConfluenceItem) ON (n.source_episode_id);

// DeclaredOutcome — One observable result of an entry point, as recovered
CREATE CONSTRAINT declared_outcome_id_unique IF NOT EXISTS FOR (n:DeclaredOutcome) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT declared_outcome_source_episode_id_required IF NOT EXISTS FOR (n:DeclaredOutcome) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT declared_outcome_name_required IF NOT EXISTS FOR (n:DeclaredOutcome) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT declared_outcome_signature_required IF NOT EXISTS FOR (n:DeclaredOutcome) REQUIRE n.signature IS NOT NULL;
CREATE INDEX declared_outcome_status_lookup IF NOT EXISTS FOR (n:DeclaredOutcome) ON (n.status);
CREATE INDEX declared_outcome_link_lookup IF NOT EXISTS FOR (n:DeclaredOutcome) ON (n.link);
CREATE INDEX declared_outcome_discriminator_lookup IF NOT EXISTS FOR (n:DeclaredOutcome) ON (n.discriminator);
CREATE INDEX declared_outcome_m_project_lookup IF NOT EXISTS FOR (n:DeclaredOutcome) ON (n.m_project);
CREATE INDEX declared_outcome_lifecycle_state_lookup IF NOT EXISTS FOR (n:DeclaredOutcome) ON (n.lifecycle_state);
CREATE INDEX declared_outcome_source_episode_id_lookup IF NOT EXISTS FOR (n:DeclaredOutcome) ON (n.source_episode_id);

// Endpoint — One HTTP entry point as recovered from code (Layer 2)
CREATE CONSTRAINT endpoint_id_unique IF NOT EXISTS FOR (n:Endpoint) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT endpoint_source_episode_id_required IF NOT EXISTS FOR (n:Endpoint) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT endpoint_name_required IF NOT EXISTS FOR (n:Endpoint) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT endpoint_http_method_required IF NOT EXISTS FOR (n:Endpoint) REQUIRE n.http_method IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT endpoint_path_required IF NOT EXISTS FOR (n:Endpoint) REQUIRE n.path IS NOT NULL;
CREATE INDEX endpoint_http_method_lookup IF NOT EXISTS FOR (n:Endpoint) ON (n.http_method);
CREATE INDEX endpoint_path_lookup IF NOT EXISTS FOR (n:Endpoint) ON (n.path);
CREATE INDEX endpoint_handler_type_lookup IF NOT EXISTS FOR (n:Endpoint) ON (n.handler_type);
CREATE INDEX endpoint_validated_lookup IF NOT EXISTS FOR (n:Endpoint) ON (n.validated);
CREATE INDEX endpoint_m_project_lookup IF NOT EXISTS FOR (n:Endpoint) ON (n.m_project);
CREATE INDEX endpoint_lifecycle_state_lookup IF NOT EXISTS FOR (n:Endpoint) ON (n.lifecycle_state);
CREATE INDEX endpoint_source_episode_id_lookup IF NOT EXISTS FOR (n:Endpoint) ON (n.source_episode_id);

// EntityDocument — One rendered business-entity specification
CREATE CONSTRAINT entity_document_id_unique IF NOT EXISTS FOR (n:EntityDocument) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT entity_document_source_episode_id_required IF NOT EXISTS FOR (n:EntityDocument) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT entity_document_name_required IF NOT EXISTS FOR (n:EntityDocument) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT entity_document_body_markdown_required IF NOT EXISTS FOR (n:EntityDocument) REQUIRE n.body_markdown IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT entity_document_content_hash_required IF NOT EXISTS FOR (n:EntityDocument) REQUIRE n.content_hash IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT entity_document_rendered_at_required IF NOT EXISTS FOR (n:EntityDocument) REQUIRE n.rendered_at IS NOT NULL;
CREATE INDEX entity_document_content_hash_lookup IF NOT EXISTS FOR (n:EntityDocument) ON (n.content_hash);
CREATE INDEX entity_document_lifecycle_state_lookup IF NOT EXISTS FOR (n:EntityDocument) ON (n.lifecycle_state);
CREATE INDEX entity_document_m_project_lookup IF NOT EXISTS FOR (n:EntityDocument) ON (n.m_project);
CREATE INDEX entity_document_source_episode_id_lookup IF NOT EXISTS FOR (n:EntityDocument) ON (n.source_episode_id);
//   EntityDocument.lifecycle_state ∈ {Quarantine, Approved, Disputed, Rejected, Deprecated} — enforced by ontology.validation, not by Neo4j

// Enum — A declared type whose instances are a closed set of named constants — its `constants` ARE its equivalence partitions
CREATE CONSTRAINT enum_id_unique IF NOT EXISTS FOR (n:Enum) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT enum_source_episode_id_required IF NOT EXISTS FOR (n:Enum) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT enum_name_required IF NOT EXISTS FOR (n:Enum) REQUIRE n.name IS NOT NULL;
CREATE INDEX enum_package_lookup IF NOT EXISTS FOR (n:Enum) ON (n.package);
CREATE INDEX enum_m_project_lookup IF NOT EXISTS FOR (n:Enum) ON (n.m_project);
CREATE INDEX enum_lifecycle_state_lookup IF NOT EXISTS FOR (n:Enum) ON (n.lifecycle_state);
CREATE INDEX enum_source_episode_id_lookup IF NOT EXISTS FOR (n:Enum) ON (n.source_episode_id);

// Episode — Immutable record of one ingested unit; everything derived points here
CREATE CONSTRAINT episode_id_unique IF NOT EXISTS FOR (n:Episode) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT episode_t_recorded_required IF NOT EXISTS FOR (n:Episode) REQUIRE n.t_recorded IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT episode_source_connector_required IF NOT EXISTS FOR (n:Episode) REQUIRE n.source_connector IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT episode_job_id_required IF NOT EXISTS FOR (n:Episode) REQUIRE n.job_id IS NOT NULL;
CREATE INDEX episode_source_connector_lookup IF NOT EXISTS FOR (n:Episode) ON (n.source_connector);
CREATE INDEX episode_job_id_lookup IF NOT EXISTS FOR (n:Episode) ON (n.job_id);
CREATE INDEX episode_checkpoint_status_lookup IF NOT EXISTS FOR (n:Episode) ON (n.checkpoint_status);
CREATE INDEX episode_m_project_lookup IF NOT EXISTS FOR (n:Episode) ON (n.m_project);

// ExceptionMapping — An @ExceptionHandler's exception → status mapping
CREATE CONSTRAINT exception_mapping_id_unique IF NOT EXISTS FOR (n:ExceptionMapping) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT exception_mapping_source_episode_id_required IF NOT EXISTS FOR (n:ExceptionMapping) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT exception_mapping_name_required IF NOT EXISTS FOR (n:ExceptionMapping) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT exception_mapping_exception_type_required IF NOT EXISTS FOR (n:ExceptionMapping) REQUIRE n.exception_type IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT exception_mapping_status_required IF NOT EXISTS FOR (n:ExceptionMapping) REQUIRE n.status IS NOT NULL;
CREATE INDEX exception_mapping_exception_type_lookup IF NOT EXISTS FOR (n:ExceptionMapping) ON (n.exception_type);
CREATE INDEX exception_mapping_status_lookup IF NOT EXISTS FOR (n:ExceptionMapping) ON (n.status);
CREATE INDEX exception_mapping_m_project_lookup IF NOT EXISTS FOR (n:ExceptionMapping) ON (n.m_project);
CREATE INDEX exception_mapping_lifecycle_state_lookup IF NOT EXISTS FOR (n:ExceptionMapping) ON (n.lifecycle_state);
CREATE INDEX exception_mapping_source_episode_id_lookup IF NOT EXISTS FOR (n:ExceptionMapping) ON (n.source_episode_id);

// Feature — One user-facing capability, grouping the scenarios that show it
CREATE CONSTRAINT feature_id_unique IF NOT EXISTS FOR (n:Feature) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT feature_source_episode_id_required IF NOT EXISTS FOR (n:Feature) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT feature_name_required IF NOT EXISTS FOR (n:Feature) REQUIRE n.name IS NOT NULL;
CREATE INDEX feature_lifecycle_state_lookup IF NOT EXISTS FOR (n:Feature) ON (n.lifecycle_state);
CREATE INDEX feature_m_project_lookup IF NOT EXISTS FOR (n:Feature) ON (n.m_project);
CREATE INDEX feature_source_episode_id_lookup IF NOT EXISTS FOR (n:Feature) ON (n.source_episode_id);
//   Feature.lifecycle_state ∈ {Quarantine, Approved, Disputed, Rejected, Deprecated} — enforced by ontology.validation, not by Neo4j

// Finding — A divergence, gap, unverifiable guard, or drift item
CREATE CONSTRAINT finding_id_unique IF NOT EXISTS FOR (n:Finding) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT finding_source_episode_id_required IF NOT EXISTS FOR (n:Finding) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT finding_name_required IF NOT EXISTS FOR (n:Finding) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT finding_finding_type_required IF NOT EXISTS FOR (n:Finding) REQUIRE n.finding_type IS NOT NULL;
CREATE INDEX finding_finding_type_lookup IF NOT EXISTS FOR (n:Finding) ON (n.finding_type);
CREATE INDEX finding_severity_lookup IF NOT EXISTS FOR (n:Finding) ON (n.severity);
CREATE INDEX finding_resolution_lookup IF NOT EXISTS FOR (n:Finding) ON (n.resolution);
CREATE INDEX finding_m_project_lookup IF NOT EXISTS FOR (n:Finding) ON (n.m_project);
CREATE INDEX finding_lifecycle_state_lookup IF NOT EXISTS FOR (n:Finding) ON (n.lifecycle_state);
CREATE INDEX finding_source_episode_id_lookup IF NOT EXISTS FOR (n:Finding) ON (n.source_episode_id);

// Intent — One stated need, before anybody has specified how it behaves
CREATE CONSTRAINT intent_id_unique IF NOT EXISTS FOR (n:Intent) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT intent_source_episode_id_required IF NOT EXISTS FOR (n:Intent) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT intent_name_required IF NOT EXISTS FOR (n:Intent) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT intent_statement_required IF NOT EXISTS FOR (n:Intent) REQUIRE n.statement IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT intent_search_text_required IF NOT EXISTS FOR (n:Intent) REQUIRE n.search_text IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT intent_valid_from_required IF NOT EXISTS FOR (n:Intent) REQUIRE n.valid_from IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT intent_valid_to_required IF NOT EXISTS FOR (n:Intent) REQUIRE n.valid_to IS NOT NULL;
CREATE INDEX intent_lifecycle_state_lookup IF NOT EXISTS FOR (n:Intent) ON (n.lifecycle_state);
CREATE INDEX intent_valid_to_lookup IF NOT EXISTS FOR (n:Intent) ON (n.valid_to);
CREATE INDEX intent_m_project_lookup IF NOT EXISTS FOR (n:Intent) ON (n.m_project);
CREATE INDEX intent_source_episode_id_lookup IF NOT EXISTS FOR (n:Intent) ON (n.source_episode_id);
//   Intent.lifecycle_state ∈ {Quarantine, Approved, Disputed, Rejected, Deprecated} — enforced by ontology.validation, not by Neo4j

// JiraItem — Evidence anchor for one Jira issue; survives its Requirement being rejected
CREATE CONSTRAINT jira_item_id_unique IF NOT EXISTS FOR (n:JiraItem) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT jira_item_source_episode_id_required IF NOT EXISTS FOR (n:JiraItem) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT jira_item_name_required IF NOT EXISTS FOR (n:JiraItem) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT jira_item_jira_key_required IF NOT EXISTS FOR (n:JiraItem) REQUIRE n.jira_key IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT jira_item_issue_type_required IF NOT EXISTS FOR (n:JiraItem) REQUIRE n.issue_type IS NOT NULL;
CREATE INDEX jira_item_jira_key_lookup IF NOT EXISTS FOR (n:JiraItem) ON (n.jira_key);
CREATE INDEX jira_item_m_project_lookup IF NOT EXISTS FOR (n:JiraItem) ON (n.m_project);
CREATE INDEX jira_item_lifecycle_state_lookup IF NOT EXISTS FOR (n:JiraItem) ON (n.lifecycle_state);
CREATE INDEX jira_item_source_episode_id_lookup IF NOT EXISTS FOR (n:JiraItem) ON (n.source_episode_id);

// Lesson — One authored academy lesson about Métis itself
CREATE CONSTRAINT lesson_id_unique IF NOT EXISTS FOR (n:Lesson) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT lesson_source_episode_id_required IF NOT EXISTS FOR (n:Lesson) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT lesson_name_required IF NOT EXISTS FOR (n:Lesson) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT lesson_text_required IF NOT EXISTS FOR (n:Lesson) REQUIRE n.text IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT lesson_ordinal_required IF NOT EXISTS FOR (n:Lesson) REQUIRE n.ordinal IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT lesson_path_required IF NOT EXISTS FOR (n:Lesson) REQUIRE n.path IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT lesson_search_text_required IF NOT EXISTS FOR (n:Lesson) REQUIRE n.search_text IS NOT NULL;
CREATE INDEX lesson_ordinal_lookup IF NOT EXISTS FOR (n:Lesson) ON (n.ordinal);
CREATE INDEX lesson_lifecycle_state_lookup IF NOT EXISTS FOR (n:Lesson) ON (n.lifecycle_state);
CREATE INDEX lesson_m_project_lookup IF NOT EXISTS FOR (n:Lesson) ON (n.m_project);
CREATE INDEX lesson_source_episode_id_lookup IF NOT EXISTS FOR (n:Lesson) ON (n.source_episode_id);
//   Lesson.lifecycle_state ∈ {Quarantine, Approved, Disputed, Rejected, Deprecated} — enforced by ontology.validation, not by Neo4j

// NeedReview — Marker: a human still owes a decision on this node (lifecycle_state is Quarantine or Disputed)
CREATE CONSTRAINT need_review_id_unique IF NOT EXISTS FOR (n:NeedReview) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT need_review_source_episode_id_required IF NOT EXISTS FOR (n:NeedReview) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT need_review_name_required IF NOT EXISTS FOR (n:NeedReview) REQUIRE n.name IS NOT NULL;
CREATE INDEX need_review_m_project_lookup IF NOT EXISTS FOR (n:NeedReview) ON (n.m_project);
CREATE INDEX need_review_lifecycle_state_lookup IF NOT EXISTS FOR (n:NeedReview) ON (n.lifecycle_state);
CREATE INDEX need_review_source_episode_id_lookup IF NOT EXISTS FOR (n:NeedReview) ON (n.source_episode_id);

// OpenApiItem — Evidence anchor for one OpenAPI/Swagger document
CREATE CONSTRAINT open_api_item_id_unique IF NOT EXISTS FOR (n:OpenApiItem) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT open_api_item_source_episode_id_required IF NOT EXISTS FOR (n:OpenApiItem) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT open_api_item_name_required IF NOT EXISTS FOR (n:OpenApiItem) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT open_api_item_document_id_required IF NOT EXISTS FOR (n:OpenApiItem) REQUIRE n.document_id IS NOT NULL;
CREATE INDEX open_api_item_document_id_lookup IF NOT EXISTS FOR (n:OpenApiItem) ON (n.document_id);
CREATE INDEX open_api_item_api_version_lookup IF NOT EXISTS FOR (n:OpenApiItem) ON (n.api_version);
CREATE INDEX open_api_item_m_project_lookup IF NOT EXISTS FOR (n:OpenApiItem) ON (n.m_project);
CREATE INDEX open_api_item_lifecycle_state_lookup IF NOT EXISTS FOR (n:OpenApiItem) ON (n.lifecycle_state);
CREATE INDEX open_api_item_source_episode_id_lookup IF NOT EXISTS FOR (n:OpenApiItem) ON (n.source_episode_id);

// Passage — One section of a document, embedded on its own
CREATE CONSTRAINT passage_id_unique IF NOT EXISTS FOR (n:Passage) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT passage_source_episode_id_required IF NOT EXISTS FOR (n:Passage) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT passage_name_required IF NOT EXISTS FOR (n:Passage) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT passage_text_required IF NOT EXISTS FOR (n:Passage) REQUIRE n.text IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT passage_ordinal_required IF NOT EXISTS FOR (n:Passage) REQUIRE n.ordinal IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT passage_search_text_required IF NOT EXISTS FOR (n:Passage) REQUIRE n.search_text IS NOT NULL;
CREATE INDEX passage_ordinal_lookup IF NOT EXISTS FOR (n:Passage) ON (n.ordinal);
CREATE INDEX passage_m_project_lookup IF NOT EXISTS FOR (n:Passage) ON (n.m_project);
CREATE INDEX passage_lifecycle_state_lookup IF NOT EXISTS FOR (n:Passage) ON (n.lifecycle_state);
CREATE INDEX passage_source_episode_id_lookup IF NOT EXISTS FOR (n:Passage) ON (n.source_episode_id);

// Requirement — One requirement statement
CREATE CONSTRAINT requirement_id_unique IF NOT EXISTS FOR (n:Requirement) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT requirement_source_episode_id_required IF NOT EXISTS FOR (n:Requirement) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT requirement_name_required IF NOT EXISTS FOR (n:Requirement) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT requirement_ears_pattern_required IF NOT EXISTS FOR (n:Requirement) REQUIRE n.ears_pattern IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT requirement_revision_required IF NOT EXISTS FOR (n:Requirement) REQUIRE n.revision IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT requirement_search_text_required IF NOT EXISTS FOR (n:Requirement) REQUIRE n.search_text IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT requirement_valid_from_required IF NOT EXISTS FOR (n:Requirement) REQUIRE n.valid_from IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT requirement_valid_to_required IF NOT EXISTS FOR (n:Requirement) REQUIRE n.valid_to IS NOT NULL;
CREATE INDEX requirement_ears_pattern_lookup IF NOT EXISTS FOR (n:Requirement) ON (n.ears_pattern);
CREATE INDEX requirement_lifecycle_state_lookup IF NOT EXISTS FOR (n:Requirement) ON (n.lifecycle_state);
CREATE INDEX requirement_valid_to_lookup IF NOT EXISTS FOR (n:Requirement) ON (n.valid_to);
CREATE INDEX requirement_m_project_lookup IF NOT EXISTS FOR (n:Requirement) ON (n.m_project);
CREATE INDEX requirement_source_episode_id_lookup IF NOT EXISTS FOR (n:Requirement) ON (n.source_episode_id);

// RestServer — A Component serving an API surface
CREATE CONSTRAINT rest_server_id_unique IF NOT EXISTS FOR (n:RestServer) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT rest_server_source_episode_id_required IF NOT EXISTS FOR (n:RestServer) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT rest_server_name_required IF NOT EXISTS FOR (n:RestServer) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT rest_server_journey_required IF NOT EXISTS FOR (n:RestServer) REQUIRE n.journey IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT rest_server_surface_required IF NOT EXISTS FOR (n:RestServer) REQUIRE n.surface IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT rest_server_version_required IF NOT EXISTS FOR (n:RestServer) REQUIRE n.version IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT rest_server_component_required IF NOT EXISTS FOR (n:RestServer) REQUIRE n.component IS NOT NULL;
CREATE INDEX rest_server_m_project_lookup IF NOT EXISTS FOR (n:RestServer) ON (n.m_project);
CREATE INDEX rest_server_lifecycle_state_lookup IF NOT EXISTS FOR (n:RestServer) ON (n.lifecycle_state);
CREATE INDEX rest_server_source_episode_id_lookup IF NOT EXISTS FOR (n:RestServer) ON (n.source_episode_id);
//   RestServer.b_surface ∈ {api} — enforced by ontology.validation, not by Neo4j

// Scenario — One covering walk: setup plus a single validated transition
CREATE CONSTRAINT scenario_id_unique IF NOT EXISTS FOR (n:Scenario) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT scenario_source_episode_id_required IF NOT EXISTS FOR (n:Scenario) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT scenario_name_required IF NOT EXISTS FOR (n:Scenario) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT scenario_criterion_required IF NOT EXISTS FOR (n:Scenario) REQUIRE n.criterion IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT scenario_generator_version_required IF NOT EXISTS FOR (n:Scenario) REQUIRE n.generator_version IS NOT NULL;
CREATE INDEX scenario_criterion_lookup IF NOT EXISTS FOR (n:Scenario) ON (n.criterion);
CREATE INDEX scenario_m_project_lookup IF NOT EXISTS FOR (n:Scenario) ON (n.m_project);
CREATE INDEX scenario_lifecycle_state_lookup IF NOT EXISTS FOR (n:Scenario) ON (n.lifecycle_state);
CREATE INDEX scenario_source_episode_id_lookup IF NOT EXISTS FOR (n:Scenario) ON (n.source_episode_id);

// SecurityScheme — One declared security requirement on an endpoint: the scheme, the declaration verbatim, and the roles it demands
CREATE CONSTRAINT security_scheme_id_unique IF NOT EXISTS FOR (n:SecurityScheme) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT security_scheme_source_episode_id_required IF NOT EXISTS FOR (n:SecurityScheme) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT security_scheme_name_required IF NOT EXISTS FOR (n:SecurityScheme) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT security_scheme_scheme_required IF NOT EXISTS FOR (n:SecurityScheme) REQUIRE n.scheme IS NOT NULL;
CREATE INDEX security_scheme_scheme_lookup IF NOT EXISTS FOR (n:SecurityScheme) ON (n.scheme);
CREATE INDEX security_scheme_source_lookup IF NOT EXISTS FOR (n:SecurityScheme) ON (n.source);
CREATE INDEX security_scheme_m_project_lookup IF NOT EXISTS FOR (n:SecurityScheme) ON (n.m_project);
CREATE INDEX security_scheme_lifecycle_state_lookup IF NOT EXISTS FOR (n:SecurityScheme) ON (n.lifecycle_state);
CREATE INDEX security_scheme_source_episode_id_lookup IF NOT EXISTS FOR (n:SecurityScheme) ON (n.source_episode_id);
//   SecurityScheme.source ∈ {annotation, filter-chain, } — enforced by ontology.validation, not by Neo4j

// SpecDocument — One rendered journey specification (§18)
CREATE CONSTRAINT spec_document_id_unique IF NOT EXISTS FOR (n:SpecDocument) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT spec_document_source_episode_id_required IF NOT EXISTS FOR (n:SpecDocument) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT spec_document_name_required IF NOT EXISTS FOR (n:SpecDocument) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT spec_document_body_markdown_required IF NOT EXISTS FOR (n:SpecDocument) REQUIRE n.body_markdown IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT spec_document_content_hash_required IF NOT EXISTS FOR (n:SpecDocument) REQUIRE n.content_hash IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT spec_document_rendered_at_required IF NOT EXISTS FOR (n:SpecDocument) REQUIRE n.rendered_at IS NOT NULL;
CREATE INDEX spec_document_content_hash_lookup IF NOT EXISTS FOR (n:SpecDocument) ON (n.content_hash);
CREATE INDEX spec_document_lifecycle_state_lookup IF NOT EXISTS FOR (n:SpecDocument) ON (n.lifecycle_state);
CREATE INDEX spec_document_m_project_lookup IF NOT EXISTS FOR (n:SpecDocument) ON (n.m_project);
CREATE INDEX spec_document_source_episode_id_lookup IF NOT EXISTS FOR (n:SpecDocument) ON (n.source_episode_id);
//   SpecDocument.lifecycle_state ∈ {Quarantine, Approved, Disputed, Rejected, Deprecated} — enforced by ontology.validation, not by Neo4j

// Specification — One specified behaviour — where intent and code meet
CREATE CONSTRAINT specification_id_unique IF NOT EXISTS FOR (n:Specification) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT specification_source_episode_id_required IF NOT EXISTS FOR (n:Specification) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT specification_name_required IF NOT EXISTS FOR (n:Specification) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT specification_statement_required IF NOT EXISTS FOR (n:Specification) REQUIRE n.statement IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT specification_provenance_required IF NOT EXISTS FOR (n:Specification) REQUIRE n.provenance IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT specification_search_text_required IF NOT EXISTS FOR (n:Specification) REQUIRE n.search_text IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT specification_valid_from_required IF NOT EXISTS FOR (n:Specification) REQUIRE n.valid_from IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT specification_valid_to_required IF NOT EXISTS FOR (n:Specification) REQUIRE n.valid_to IS NOT NULL;
CREATE INDEX specification_provenance_lookup IF NOT EXISTS FOR (n:Specification) ON (n.provenance);
CREATE INDEX specification_lifecycle_state_lookup IF NOT EXISTS FOR (n:Specification) ON (n.lifecycle_state);
CREATE INDEX specification_valid_to_lookup IF NOT EXISTS FOR (n:Specification) ON (n.valid_to);
CREATE INDEX specification_m_project_lookup IF NOT EXISTS FOR (n:Specification) ON (n.m_project);
CREATE INDEX specification_source_episode_id_lookup IF NOT EXISTS FOR (n:Specification) ON (n.source_episode_id);
//   Specification.provenance ∈ {code_derived, human_confirmed, independently_authored} — enforced by ontology.validation, not by Neo4j
//   Specification.lifecycle_state ∈ {Quarantine, Approved, Disputed, Rejected, Deprecated} — enforced by ontology.validation, not by Neo4j

// State — One observable situation on one surface (spec M-3)
CREATE CONSTRAINT state_id_unique IF NOT EXISTS FOR (n:State) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT state_source_episode_id_required IF NOT EXISTS FOR (n:State) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT state_name_required IF NOT EXISTS FOR (n:State) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT state_b_surface_required IF NOT EXISTS FOR (n:State) REQUIRE n.b_surface IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT state_model_id_required IF NOT EXISTS FOR (n:State) REQUIRE n.model_id IS NOT NULL;
CREATE INDEX state_b_surface_lookup IF NOT EXISTS FOR (n:State) ON (n.b_surface);
CREATE INDEX state_lifecycle_state_lookup IF NOT EXISTS FOR (n:State) ON (n.lifecycle_state);
CREATE INDEX state_functional_areas_lookup IF NOT EXISTS FOR (n:State) ON (n.functional_areas);
CREATE INDEX state_x_name_tier_lookup IF NOT EXISTS FOR (n:State) ON (n.x_name_tier);
CREATE INDEX state_model_id_lookup IF NOT EXISTS FOR (n:State) ON (n.model_id);
CREATE INDEX state_m_project_lookup IF NOT EXISTS FOR (n:State) ON (n.m_project);
CREATE INDEX state_source_episode_id_lookup IF NOT EXISTS FOR (n:State) ON (n.source_episode_id);
//   State.b_surface ∈ {ui, api} — enforced by ontology.validation, not by Neo4j
//   State.lifecycle_state ∈ {Quarantine, Approved, Disputed, Rejected, Deprecated} — enforced by ontology.validation, not by Neo4j

// TestCase — One rendered, human-executable artefact
CREATE CONSTRAINT test_case_id_unique IF NOT EXISTS FOR (n:TestCase) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT test_case_source_episode_id_required IF NOT EXISTS FOR (n:TestCase) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT test_case_name_required IF NOT EXISTS FOR (n:TestCase) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT test_case_content_hash_required IF NOT EXISTS FOR (n:TestCase) REQUIRE n.content_hash IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT test_case_steps_json_required IF NOT EXISTS FOR (n:TestCase) REQUIRE n.steps_json IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT test_case_expected_result_required IF NOT EXISTS FOR (n:TestCase) REQUIRE n.expected_result IS NOT NULL;
CREATE INDEX test_case_content_hash_lookup IF NOT EXISTS FOR (n:TestCase) ON (n.content_hash);
CREATE INDEX test_case_published_id_lookup IF NOT EXISTS FOR (n:TestCase) ON (n.published_id);
CREATE INDEX test_case_published_status_lookup IF NOT EXISTS FOR (n:TestCase) ON (n.published_status);
CREATE INDEX test_case_level_lookup IF NOT EXISTS FOR (n:TestCase) ON (n.level);
CREATE INDEX test_case_m_project_lookup IF NOT EXISTS FOR (n:TestCase) ON (n.m_project);
CREATE INDEX test_case_lifecycle_state_lookup IF NOT EXISTS FOR (n:TestCase) ON (n.lifecycle_state);
CREATE INDEX test_case_source_episode_id_lookup IF NOT EXISTS FOR (n:TestCase) ON (n.source_episode_id);
//   TestCase.level ∈ {unit, integration, api_functional, web_functional, e2e, performance} — enforced by ontology.validation, not by Neo4j

// Topic — A subject shared by documents that cover the same ground
CREATE CONSTRAINT topic_id_unique IF NOT EXISTS FOR (n:Topic) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT topic_source_episode_id_required IF NOT EXISTS FOR (n:Topic) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT topic_name_required IF NOT EXISTS FOR (n:Topic) REQUIRE n.name IS NOT NULL;
CREATE INDEX topic_name_lookup IF NOT EXISTS FOR (n:Topic) ON (n.name);
CREATE INDEX topic_m_project_lookup IF NOT EXISTS FOR (n:Topic) ON (n.m_project);
CREATE INDEX topic_lifecycle_state_lookup IF NOT EXISTS FOR (n:Topic) ON (n.lifecycle_state);
CREATE INDEX topic_source_episode_id_lookup IF NOT EXISTS FOR (n:Topic) ON (n.source_episode_id);

// Transition — One interaction: trigger, guard, source and target state
CREATE CONSTRAINT transition_id_unique IF NOT EXISTS FOR (n:Transition) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT transition_source_episode_id_required IF NOT EXISTS FOR (n:Transition) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT transition_name_required IF NOT EXISTS FOR (n:Transition) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT transition_c_trigger_required IF NOT EXISTS FOR (n:Transition) REQUIRE n.c_trigger IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT transition_b_guard_expression_required IF NOT EXISTS FOR (n:Transition) REQUIRE n.b_guard_expression IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT transition_b_implementation_status_required IF NOT EXISTS FOR (n:Transition) REQUIRE n.b_implementation_status IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT transition_b_surface_required IF NOT EXISTS FOR (n:Transition) REQUIRE n.b_surface IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT transition_model_id_required IF NOT EXISTS FOR (n:Transition) REQUIRE n.model_id IS NOT NULL;
CREATE INDEX transition_b_surface_lookup IF NOT EXISTS FOR (n:Transition) ON (n.b_surface);
CREATE INDEX transition_lifecycle_state_lookup IF NOT EXISTS FOR (n:Transition) ON (n.lifecycle_state);
CREATE INDEX transition_b_implementation_status_lookup IF NOT EXISTS FOR (n:Transition) ON (n.b_implementation_status);
CREATE INDEX transition_x_extraction_method_lookup IF NOT EXISTS FOR (n:Transition) ON (n.x_extraction_method);
CREATE INDEX transition_functional_areas_lookup IF NOT EXISTS FOR (n:Transition) ON (n.functional_areas);
CREATE INDEX transition_model_id_lookup IF NOT EXISTS FOR (n:Transition) ON (n.model_id);
CREATE INDEX transition_x_source_state_unresolved_lookup IF NOT EXISTS FOR (n:Transition) ON (n.x_source_state_unresolved);
CREATE INDEX transition_c_outcome_status_lookup IF NOT EXISTS FOR (n:Transition) ON (n.c_outcome_status);
CREATE INDEX transition_x_outcome_source_lookup IF NOT EXISTS FOR (n:Transition) ON (n.x_outcome_source);
CREATE INDEX transition_x_guard_claim_lookup IF NOT EXISTS FOR (n:Transition) ON (n.x_guard_claim);
CREATE INDEX transition_c_response_body_lookup IF NOT EXISTS FOR (n:Transition) ON (n.c_response_body);
CREATE INDEX transition_x_name_tier_lookup IF NOT EXISTS FOR (n:Transition) ON (n.x_name_tier);
CREATE INDEX transition_x_guard_tier_lookup IF NOT EXISTS FOR (n:Transition) ON (n.x_guard_tier);
CREATE INDEX transition_m_project_lookup IF NOT EXISTS FOR (n:Transition) ON (n.m_project);
CREATE INDEX transition_source_episode_id_lookup IF NOT EXISTS FOR (n:Transition) ON (n.source_episode_id);
//   Transition.b_surface ∈ {ui, api} — enforced by ontology.validation, not by Neo4j
//   Transition.b_implementation_status ∈ {implemented, planned} — enforced by ontology.validation, not by Neo4j
//   Transition.x_extraction_method ∈ {hand_authored, static_analysis, ac_mined, declared_contract} — enforced by ontology.validation, not by Neo4j
//   Transition.lifecycle_state ∈ {Quarantine, Approved, Disputed, Rejected, Deprecated} — enforced by ontology.validation, not by Neo4j
//   Transition.x_outcome_source ∈ {constructed, declared} — enforced by ontology.validation, not by Neo4j

// UiAction — A Transition on the ui surface: one interaction or observation
CREATE CONSTRAINT ui_action_id_unique IF NOT EXISTS FOR (n:UiAction) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT ui_action_source_episode_id_required IF NOT EXISTS FOR (n:UiAction) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT ui_action_name_required IF NOT EXISTS FOR (n:UiAction) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT ui_action_c_trigger_required IF NOT EXISTS FOR (n:UiAction) REQUIRE n.c_trigger IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT ui_action_b_guard_expression_required IF NOT EXISTS FOR (n:UiAction) REQUIRE n.b_guard_expression IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT ui_action_b_implementation_status_required IF NOT EXISTS FOR (n:UiAction) REQUIRE n.b_implementation_status IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT ui_action_b_surface_required IF NOT EXISTS FOR (n:UiAction) REQUIRE n.b_surface IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT ui_action_model_id_required IF NOT EXISTS FOR (n:UiAction) REQUIRE n.model_id IS NOT NULL;
CREATE INDEX ui_action_m_project_lookup IF NOT EXISTS FOR (n:UiAction) ON (n.m_project);
CREATE INDEX ui_action_lifecycle_state_lookup IF NOT EXISTS FOR (n:UiAction) ON (n.lifecycle_state);
CREATE INDEX ui_action_source_episode_id_lookup IF NOT EXISTS FOR (n:UiAction) ON (n.source_episode_id);
//   UiAction.b_surface ∈ {ui} — enforced by ontology.validation, not by Neo4j

// WebServer — A Component serving a web surface
CREATE CONSTRAINT web_server_id_unique IF NOT EXISTS FOR (n:WebServer) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT web_server_source_episode_id_required IF NOT EXISTS FOR (n:WebServer) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT web_server_name_required IF NOT EXISTS FOR (n:WebServer) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT web_server_journey_required IF NOT EXISTS FOR (n:WebServer) REQUIRE n.journey IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT web_server_surface_required IF NOT EXISTS FOR (n:WebServer) REQUIRE n.surface IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT web_server_version_required IF NOT EXISTS FOR (n:WebServer) REQUIRE n.version IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT web_server_component_required IF NOT EXISTS FOR (n:WebServer) REQUIRE n.component IS NOT NULL;
CREATE INDEX web_server_m_project_lookup IF NOT EXISTS FOR (n:WebServer) ON (n.m_project);
CREATE INDEX web_server_lifecycle_state_lookup IF NOT EXISTS FOR (n:WebServer) ON (n.lifecycle_state);
CREATE INDEX web_server_source_episode_id_lookup IF NOT EXISTS FOR (n:WebServer) ON (n.source_episode_id);
//   WebServer.b_surface ∈ {ui} — enforced by ontology.validation, not by Neo4j

// ZephyrItem — Evidence anchor for one Zephyr Scale item
CREATE CONSTRAINT zephyr_item_id_unique IF NOT EXISTS FOR (n:ZephyrItem) REQUIRE n.id IS UNIQUE;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT zephyr_item_source_episode_id_required IF NOT EXISTS FOR (n:ZephyrItem) REQUIRE n.source_episode_id IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT zephyr_item_name_required IF NOT EXISTS FOR (n:ZephyrItem) REQUIRE n.name IS NOT NULL;
// [enterprise-only, enforced by ontology/validation.py] CREATE CONSTRAINT zephyr_item_zephyr_key_required IF NOT EXISTS FOR (n:ZephyrItem) REQUIRE n.zephyr_key IS NOT NULL;
CREATE INDEX zephyr_item_zephyr_key_lookup IF NOT EXISTS FOR (n:ZephyrItem) ON (n.zephyr_key);
CREATE INDEX zephyr_item_item_type_lookup IF NOT EXISTS FOR (n:ZephyrItem) ON (n.item_type);
CREATE INDEX zephyr_item_m_project_lookup IF NOT EXISTS FOR (n:ZephyrItem) ON (n.m_project);
CREATE INDEX zephyr_item_lifecycle_state_lookup IF NOT EXISTS FOR (n:ZephyrItem) ON (n.lifecycle_state);
CREATE INDEX zephyr_item_source_episode_id_lookup IF NOT EXISTS FOR (n:ZephyrItem) ON (n.source_episode_id);

// Free-text search (Lucene, Community edition). Replaces substring
// matching: `CONTAINS` cannot rank, cannot tokenise, and cannot tell a
// title match from a body match.
CREATE FULLTEXT INDEX metis_search IF NOT EXISTS
FOR (n:AcceptanceCriterion|BusinessEntity|Intent|Lesson|Passage|Requirement|Specification)
ON EACH [n.description, n.name, n.search_text, n.statement, n.text]
// The `english` analyzer, not the default `standard` one. Measured: with
// the default, searching `lock` returned NOTHING for a criterion whose
// text says "the account is locked" — standard tokenises and lowercases
// but does not stem, so it beats CONTAINS on ranking and loses to it on
// the word-form matching that is half the reason to want full text.
OPTIONS {indexConfig: {`fulltext.analyzer`: 'english'}};

// Semantic search. Inert until `embedding` is populated — an unembedded
// node is absent from its index rather than wrong in it.
//
// One index PER LABEL: Neo4j accepts the multi-label form for a full-text
// index and rejects it for a vector index.
CREATE VECTOR INDEX metis_vector_acceptance_criterion IF NOT EXISTS
FOR (n:AcceptanceCriterion)
ON (n.embedding)
OPTIONS {indexConfig: {
  `vector.dimensions`: 256,
  `vector.similarity_function`: 'cosine'
}};
CREATE VECTOR INDEX metis_vector_business_entity IF NOT EXISTS
FOR (n:BusinessEntity)
ON (n.embedding)
OPTIONS {indexConfig: {
  `vector.dimensions`: 256,
  `vector.similarity_function`: 'cosine'
}};
CREATE VECTOR INDEX metis_vector_intent IF NOT EXISTS
FOR (n:Intent)
ON (n.embedding)
OPTIONS {indexConfig: {
  `vector.dimensions`: 256,
  `vector.similarity_function`: 'cosine'
}};
CREATE VECTOR INDEX metis_vector_lesson IF NOT EXISTS
FOR (n:Lesson)
ON (n.embedding)
OPTIONS {indexConfig: {
  `vector.dimensions`: 256,
  `vector.similarity_function`: 'cosine'
}};
CREATE VECTOR INDEX metis_vector_passage IF NOT EXISTS
FOR (n:Passage)
ON (n.embedding)
OPTIONS {indexConfig: {
  `vector.dimensions`: 256,
  `vector.similarity_function`: 'cosine'
}};
CREATE VECTOR INDEX metis_vector_requirement IF NOT EXISTS
FOR (n:Requirement)
ON (n.embedding)
OPTIONS {indexConfig: {
  `vector.dimensions`: 256,
  `vector.similarity_function`: 'cosine'
}};
CREATE VECTOR INDEX metis_vector_specification IF NOT EXISTS
FOR (n:Specification)
ON (n.embedding)
OPTIONS {indexConfig: {
  `vector.dimensions`: 256,
  `vector.similarity_function`: 'cosine'
}};
