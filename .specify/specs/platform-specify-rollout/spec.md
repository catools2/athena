# Feature Spec: platform-specify-rollout

## Summary

- Problem: Athena documents a Specify workflow in the README, but the repo had no runnable `.specify` scaffold, no slash prompts, and no seeded feature portfolio.
- Outcome: Establish a working specification-first development framework that supports humans and agents across all Athena services.
- Service domains: Platform workflow, repository governance, Copilot prompt surface, all domain services by convention.

## User Value

- Primary user or system: Athena maintainers and AI coding agents.
- Trigger or workflow: New feature planning, maintenance planning, and cross-service refactoring.
- Business value: Reduces planning drift, creates traceability, and standardizes how new work is decomposed and executed.

## Scope

- In scope: `.specify` governance files, feature scaffolding scripts, prompt commands, seeded feature packages, and README alignment.
- Out of scope: Production feature implementation inside the boot services.

## Acceptance Criteria

1. Athena contains a working `.specify` scaffold with constitution, workflow guide, reusable templates, and scripts. ✓
2. Workspace prompt files exist for research, data-modeling, planning, and execution against `.specify/specs/<feature-slug>`. ✓
3. At least one feature package exists for each major Athena domain plus a platform rollout package. ✓
4. A GitHub Actions workflow enforces spec linkage in PR descriptions or branch names.
5. A PR template guides contributors to link their implementation to a feature spec.

## API and Contract Impact

- REST endpoints affected: None directly.
- Request or response changes: None.
- Backward compatibility notes: This feature affects repository workflow only.

## Data Model Impact

- DTOs: None.
- Entities: None.
- Database or Flyway changes: None.

## Service and Integration Impact

- Boot modules: No runtime changes expected.
- Feign modules: No runtime changes expected.
- Gateway or frontend impact: None.
- External systems impacted: GitHub Copilot prompt discovery only.

## Risks and Open Questions

- Risks: Workflow files may diverge from actual service conventions if not maintained alongside implementation.
- Questions: Should PR templates or CI checks later enforce spec linkage automatically?

## Validation Strategy

- Unit tests: Script smoke checks for feature creation and agent-context refresh. ✓
- Integration tests: Not applicable.
- Manual verification: Confirm prompt files exist, feature packages are seeded, README references valid paths, CI workflow runs on test PR, and PR template appears when creating a PR. ✓

## How to Use the Workflow

1. **Create a feature**: `.specify/scripts/bash/create-new-feature.sh "my-feature-name"`
2. **Write the spec** in `.specify/specs/my-feature-name/spec.md`
3. **Refine with prompts** (in chat): `/speckit.research`, `/speckit.data-model`, `/speckit.plan`
4. **Implement**: Follow the plan and reference the spec in your PR description or branch name
5. **Refresh agent context** (before merging): `.specify/scripts/bash/update-agent-context.sh copilot`
