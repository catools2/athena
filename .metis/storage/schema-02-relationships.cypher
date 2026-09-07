// ==========================================================
// Métis schema — GENERATED from metis_mcp/ontology/labels.py
// Do not hand-edit: regenerate with
//     python3 -m metis_mcp.ontology.schema --write
// Hand-edits are drift, and test_ontology.py will fail on them.
// ==========================================================

// ---- Part 2: relationship indexes ----

CREATE INDEX rel_r_e_p_r_e_s_e_n_t_s_t_valid IF NOT EXISTS FOR ()-[x:REPRESENTS]-() ON (x.t_valid);
CREATE INDEX rel_d_e_s_c_r_i_b_e_s_t_valid IF NOT EXISTS FOR ()-[x:DESCRIBES]-() ON (x.t_valid);
CREATE INDEX rel_c_i_t_e_s_t_valid IF NOT EXISTS FOR ()-[x:CITES]-() ON (x.t_valid);
CREATE INDEX rel_l_i_n_k_s__t_o_t_valid IF NOT EXISTS FOR ()-[x:LINKS_TO]-() ON (x.t_valid);
CREATE INDEX rel_s_p_e_c_i_f_i_e_d__b_y_t_valid IF NOT EXISTS FOR ()-[x:SPECIFIED_BY]-() ON (x.t_valid);
CREATE INDEX rel_h_a_s__a_c_t_valid IF NOT EXISTS FOR ()-[x:HAS_AC]-() ON (x.t_valid);
CREATE INDEX rel_s_p_e_c_i_f_i_e_s_t_valid IF NOT EXISTS FOR ()-[x:SPECIFIES]-() ON (x.t_valid);
CREATE INDEX rel_r_e_a_l_i_s_e_d__b_y_t_valid IF NOT EXISTS FOR ()-[x:REALISED_BY]-() ON (x.t_valid);
CREATE INDEX rel_h_a_s__s_c_e_n_a_r_i_o_t_valid IF NOT EXISTS FOR ()-[x:HAS_SCENARIO]-() ON (x.t_valid);
CREATE INDEX rel_e_x_p_o_s_e_s_t_valid IF NOT EXISTS FOR ()-[x:EXPOSES]-() ON (x.t_valid);
CREATE INDEX rel_c_o_n_t_a_i_n_s_t_valid IF NOT EXISTS FOR ()-[x:CONTAINS]-() ON (x.t_valid);
CREATE INDEX rel_i_m_p_l_e_m_e_n_t_s_t_valid IF NOT EXISTS FOR ()-[x:IMPLEMENTS]-() ON (x.t_valid);
CREATE INDEX rel_v_a_l_i_d_a_t_e_s_t_valid IF NOT EXISTS FOR ()-[x:VALIDATES]-() ON (x.t_valid);
CREATE INDEX rel_w_h_e_n_t_valid IF NOT EXISTS FOR ()-[x:WHEN]-() ON (x.t_valid);
CREATE INDEX rel_t_h_e_n_t_valid IF NOT EXISTS FOR ()-[x:THEN]-() ON (x.t_valid);
CREATE INDEX rel_t_r_i_g_g_e_r_s_t_valid IF NOT EXISTS FOR ()-[x:TRIGGERS]-() ON (x.t_valid);
CREATE INDEX rel_i_n_v_o_k_e_s_t_valid IF NOT EXISTS FOR ()-[x:INVOKES]-() ON (x.t_valid);
CREATE INDEX rel_d_e_r_i_v_e_d__f_r_o_m_t_valid IF NOT EXISTS FOR ()-[x:DERIVED_FROM]-() ON (x.t_valid);
CREATE INDEX rel_g_e_n_e_r_a_t_e_d__f_r_o_m_t_valid IF NOT EXISTS FOR ()-[x:GENERATED_FROM]-() ON (x.t_valid);
CREATE INDEX rel_c_o_v_e_r_s_t_valid IF NOT EXISTS FOR ()-[x:COVERS]-() ON (x.t_valid);
CREATE INDEX rel_p_r_o_d_u_c_e_s_t_valid IF NOT EXISTS FOR ()-[x:PRODUCES]-() ON (x.t_valid);
CREATE INDEX rel_b_e_l_o_n_g_s__t_o_t_valid IF NOT EXISTS FOR ()-[x:BELONGS_TO]-() ON (x.t_valid);
CREATE INDEX rel_r_e_f_e_r_e_n_c_e_s_t_valid IF NOT EXISTS FOR ()-[x:REFERENCES]-() ON (x.t_valid);
CREATE INDEX rel_a_b_o_u_t_t_valid IF NOT EXISTS FOR ()-[x:ABOUT]-() ON (x.t_valid);
CREATE INDEX rel_s_e_c_u_r_e_d__b_y_t_valid IF NOT EXISTS FOR ()-[x:SECURED_BY]-() ON (x.t_valid);
CREATE INDEX rel_o_f__t_y_p_e_t_valid IF NOT EXISTS FOR ()-[x:OF_TYPE]-() ON (x.t_valid);
CREATE INDEX rel_d_e_c_l_a_r_e_s_t_valid IF NOT EXISTS FOR ()-[x:DECLARES]-() ON (x.t_valid);
CREATE INDEX rel_g_u_a_r_d_e_d__b_y_t_valid IF NOT EXISTS FOR ()-[x:GUARDED_BY]-() ON (x.t_valid);
CREATE INDEX rel_r_e_q_u_i_r_e_s_t_valid IF NOT EXISTS FOR ()-[x:REQUIRES]-() ON (x.t_valid);
CREATE INDEX rel_e_x_p_e_c_t_s_t_valid IF NOT EXISTS FOR ()-[x:EXPECTS]-() ON (x.t_valid);
CREATE INDEX rel_c_o_n_s_t_r_a_i_n_e_d__b_y_t_valid IF NOT EXISTS FOR ()-[x:CONSTRAINED_BY]-() ON (x.t_valid);

// ---- Catalogue (the closed set enforced by ontology.validation) ----
//   (JiraItem)-[:REPRESENTS]->(Requirement)  — System-of-record source
//   (ConfluenceItem)-[:REPRESENTS]->(Requirement)  — System-of-record source
//   (OpenApiItem)-[:REPRESENTS]->(Requirement)  — System-of-record source
//   (ZephyrItem)-[:REPRESENTS]->(Requirement)  — System-of-record source
//   (CodeItem)-[:REPRESENTS]->(Requirement)  — System-of-record source
//   (SpecDocument)-[:DESCRIBES]->(Component)  — The component version this specification renders
//   (EntityDocument)-[:DESCRIBES]->(BusinessEntity)  — The business noun this specification defines
//   (SpecDocument)-[:CITES]->(AcceptanceCriterion)  — A rule rendered in this document
//   (EntityDocument)-[:CITES]->(AcceptanceCriterion)  — A criterion that touches this entity
//   (JiraItem)-[:LINKS_TO]->(JiraItem)  — A real Jira issue link — provenance, not traceability
//   (Intent)-[:SPECIFIED_BY]->(Specification)  — A need, once somebody has said how it behaves
//   (Specification)-[:HAS_AC]->(AcceptanceCriterion)  — The atomic conditions this specified behaviour breaks into
//   (Specification)-[:SPECIFIES]->(Requirement)  — The requirement this behaviour belongs to — kept so §7.8's chain still reaches a Requirement (A-24)
//   (Specification)-[:REALISED_BY]->(Feature)  — The capability this behaviour is part of. `feature.derive` groups SPECIFICATIONS -- on the business noun they name, or the component that implements them -- so this is the edge the grouping actually establishes. Without it the derivation planned AcceptanceCriterion edges using specification ids, and every one matched nothing
//   (AcceptanceCriterion)-[:REALISED_BY]->(Feature)  — The capability this condition is part of
//   (Requirement)-[:REALISED_BY]->(Feature)  — The capability this requirement is part of
//   (Feature)-[:HAS_SCENARIO]->(Scenario)  — The walks that demonstrate this capability
//   (RestServer)-[:EXPOSES]->(Endpoint)  — The entry points it serves
//   (RestServer)-[:CONTAINS]->(Transition)  — Its behaviour at one commit
//   (WebServer)-[:CONTAINS]->(Transition)  — Its behaviour at one commit
//   (Endpoint)-[:IMPLEMENTS]->(Specification)  — This entry point is one implementation of that behaviour
//   (Action)-[:IMPLEMENTS]->(Specification)  — This affordance is one implementation of that behaviour
//   (Requirement)-[:HAS_AC]->(AcceptanceCriterion)  — Its atomic conditions
//   (AcceptanceCriterion)-[:VALIDATES]->(Transition)  — Confirmed match (spec X-18)
//   (State)-[:WHEN]->(Transition)  — Source state — the implicit Given
//   (Transition)-[:THEN]->(State)  — Resulting target state
//   (UiAction)-[:TRIGGERS]->(ApiCall)  — This interaction starts that API flow; the UI continues (M-5a)
//   (UiAction)-[:INVOKES]->(ApiCall)  — This UI outcome rendered that API outcome (M-5a, M-5b)
//   (Transition)-[:DERIVED_FROM]->(Action)  — The control this interaction was recovered from
//   (Lesson)-[:CONTAINS]->(Passage)  — Its sections, each carrying its own vector
//   (Component)-[:CONTAINS]->(State)  — Membership of this component version
//   (Component)-[:CONTAINS]->(Transition)  — Membership of this component version
//   (Scenario)-[:GENERATED_FROM]->(Component)  — The exact version this path covers
//   (Scenario)-[:COVERS]->(Transition)  {sequence, is_validated}  — Ordered traversal — makes coverage computable
//   (Scenario)-[:PRODUCES]->(TestCase)  — The rendered artefact
//   (Lesson)-[:BELONGS_TO]->(Topic)  — The subject it covers, shared with every other document that covers it
//   (Topic)-[:BELONGS_TO]->(Topic)  — The broader subject this one sits under
//   (BusinessEntity)-[:BELONGS_TO]->(BusinessArea)  — Which domain this noun lives in
//   (Requirement)-[:BELONGS_TO]->(BusinessArea)  — Which domain this requirement governs
//   (AcceptanceCriterion)-[:REFERENCES]->(BusinessEntity)  — A business noun this criterion acts on or constrains
//   (Finding)-[:ABOUT]->(*) [any label]  — What the finding concerns
//   (Component)-[:EXPOSES]->(Endpoint)  — The entry points this deployable presents
//   (Endpoint)-[:SECURED_BY]->(SecurityScheme)  — A declared security requirement a caller must satisfy. Replaces the parallel `security_*` arrays, which could not express a scheme with more than one role
//   (Class)-[:OF_TYPE]->(Class)  — A field of this type is itself a declared type — the nested payload. Which field is on `f_<name>_type`
//   (Endpoint)-[:DECLARES]->(DeclaredOutcome)  — A result this entry point can produce
//   (DeclaredOutcome)-[:GUARDED_BY]->(Check)  — The condition selecting this outcome
//   (Transition)-[:DERIVED_FROM]->(Endpoint)  — The entry point this behaviour was recovered from
//   (Transition)-[:DERIVED_FROM]->(DeclaredOutcome)  — The recovered outcome this transition represents
//   (Transition)-[:DERIVED_FROM]->(ExceptionMapping)  — The exception→status mapping behind a derived rejection
//   (Transition)-[:REQUIRES]->(Class)  — A payload type whose field constraints a case must satisfy or violate (GD-3)
//   (Transition)-[:EXPECTS]->(Class)  — The response body a case should assert
//   (Endpoint)-[:CONSTRAINED_BY]->(Check)  — A condition recovered in this endpoint's handler that no outcome references
//   (Transition)-[:CONSTRAINED_BY]->(Check)  — The recovered condition behind this transition's guard
CREATE INDEX rel_c_o_v_e_r_s_sequence IF NOT EXISTS FOR ()-[x:COVERS]-() ON (x.sequence);
CREATE INDEX rel_c_o_v_e_r_s_is_validated IF NOT EXISTS FOR ()-[x:COVERS]-() ON (x.is_validated);
