# Research Notes: platform-specify-rollout

## Current State

- Relevant modules: `README.md`, `.github/copilot-instructions.md`, `.github/agents/copilot-instructions.md`, `.github/prompts`.
- Existing APIs: None. This is repository workflow infrastructure.
- Existing entities or DTOs: None.

## Design Decisions

- Decision 1: Reuse the README-declared Specify workflow instead of introducing a second process.
- Decision 2: Use prompt files for slash-command discoverability and shell scripts for deterministic scaffolding.

## Constraints

- Technical constraints: The generated agent instructions file already contains manual addition markers and should be updated surgically.
- Operational constraints: The workflow must work across all services without coupling to one domain module.

## Follow-up Questions

1. Should CI later reject implementation PRs that lack a `.specify/specs/...` reference?
2. Should we add a PR template once the team is comfortable with the workflow?
