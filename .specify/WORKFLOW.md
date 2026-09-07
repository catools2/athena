# Athena Specify Workflow

## 1. Create The Feature Package

Run:

```bash
.specify/scripts/bash/create-new-feature.sh "feature-name"
```

This creates a folder under `.specify/specs/<feature-slug>` with the required artifacts.

## 2. Write The Spec

Start in `spec.md` and define:

- the user or system problem
- the desired outcome
- acceptance criteria
- API, DTO, entity, and migration impact
- risks and validation strategy

Do not move to implementation planning while the acceptance criteria are vague.

## 3. Run Research And Planning Prompts

Use the workspace prompts in chat:

- `/speckit.research`
- `/speckit.data-model`
- `/speckit.plan`
- `/speckit.execute`

Point each prompt at the target feature folder and update the corresponding artifact in place.

## 4. Validate Readiness

Before code changes begin, confirm:

- the feature has a clear owner
- module touchpoints are listed
- dependency order is understood
- validation obligations are realistic for the change

## 5. Implement In Phases

Follow the sequence in `plan.md`:

1. shared contracts and model changes
2. persistence and business logic
3. API and integration wiring
4. tests and verification

If implementation reveals a missing requirement, update the feature package before expanding code scope.

## 6. Refresh Agent Context

Run:

```bash
.specify/scripts/bash/update-agent-context.sh copilot
```

This updates the generated summary block inside `.github/agents/copilot-instructions.md` with the currently tracked feature packages.

## 7. Review And Merge

Every pull request should reference the feature package path and confirm whether the spec, plan, and validation strategy were updated.
