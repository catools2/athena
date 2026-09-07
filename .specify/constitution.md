# Athena Specify Constitution

## Purpose

Athena uses specification-first development to reduce contract drift across its microservices and to make human and agent execution predictable. Every production change should be traceable to a feature package under `.specify/specs`.

## Principles

1. **Spec Before Code**
   - Every user-facing or integration-facing change starts with a `spec.md`.
   - Code may not introduce new behavior that is absent from the feature spec.

2. **Small Vertical Features**
   - Features should be thin, testable slices.
   - Avoid service-wide mega specs unless the work is purely operational.

3. **Contract Integrity**
   - API, DTO, Feign, and persistence changes must be called out explicitly.
   - Backward compatibility impact must be documented before implementation.

4. **Cross-Module Traceability**
   - Plans must identify all impacted modules, especially `athena-model`, `athena-boot-*`, `athena-boot-*-feign`, and `athena-gateway` when relevant.

5. **Validation Is Part of the Feature**
   - Each feature must define unit, integration, and manual verification expectations.
   - Work is not done until the stated validation strategy has been satisfied or explicitly deferred.

6. **Agent Execution Must Be Reviewable**
   - Agents may assist with research, planning, and implementation, but outputs must stay aligned with the spec package.
   - Prompt outputs should update files in the feature package rather than scatter ad hoc notes.

## Required Artifacts

- `spec.md`: business intent, scope, acceptance criteria, and contract impact
- `plan.md`: phased implementation sequence and module touchpoints
- `research.md`: current-state evidence and design decisions
- `data-model.md`: entity, DTO, migration, and sync considerations

## Definition Of Ready

A feature is ready for implementation when:

- The feature package exists under `.specify/specs/<feature-slug>`.
- Acceptance criteria are concrete and testable.
- Module ownership is identified.
- Known risks and open questions are recorded.

## Definition Of Done

A feature is done when:

- Implementation matches the accepted spec.
- Contract changes are documented.
- Required tests pass.
- Follow-up work is either completed or captured as a new feature package.
