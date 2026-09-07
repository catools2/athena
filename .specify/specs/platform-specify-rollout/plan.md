# Implementation Plan: platform-specify-rollout

## Goal

- Deliver the feature described in [spec.md](./spec.md) by making Athena's documented spec-driven workflow runnable and consistent.

## Work Breakdown

1. Create `.specify` structure, templates, constitution, and workflow files.
2. Add scaffold and agent-context scripts under `.specify/scripts/bash`.
3. Add chat prompt files under `.github/prompts`.
4. Seed the initial feature portfolio for platform and domain services.
5. Align README and Copilot instructions with the new workflow.

## Module Touchpoints

- Shared model: None.
- Service module: None.
- Feign module: None.
- Other modules: `.specify`, `.github/prompts`, `.github/agents/copilot-instructions.md`, `.github/copilot-instructions.md`, `README.md`.

## Delivery Phases

1. Workflow foundation and templates ✓
2. Prompt and script enablement ✓
3. Seed feature packages ✓
4. Documentation and validation ✓
5. Governance and PR enforcement ✓

## Dependencies

- Blocking dependencies: Existing README workflow references.
- External dependencies: VS Code Copilot prompt file support.

## Done Criteria

- Spec acceptance criteria met. ✓
- Both shell scripts run successfully. ✓
- Seed feature packages exist for all target domains. ✓
- GitHub Actions workflow and PR template are deployed and discoverable. ✓
