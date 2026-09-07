## Feature Spec

Link to the feature specification:

- `.specify/specs/<feature-slug>/`

_For example: `.specify/specs/athena-unified-web-ui-reporting/`_

### Not a feature implementation?

If this PR is infrastructure, documentation, or a fix without a corresponding feature spec, start this section with:

```
[skip-spec]
```

---

## What This PR Does

_Replace this with 1-2 sentences summarizing the change._

## Related Spec Details

- **Acceptance Criteria**: Link or copy the relevant acceptance criteria from the feature spec.
- **Modules Touched**: List the modules impacted (e.g., `athena-boot-core`, `athena-model`, `athena-boot-core-feign`).
- **Validation Completed**: Confirm which tests or manual steps from the feature spec are done.

## Checklist

- [ ] Spec linkage is in the "Feature Spec" section above
- [ ] Code changes match the acceptance criteria in the linked spec
- [ ] New or updated tests are included
- [ ] No undocumented contract drift from DTOs, Feign clients, or APIs
- [ ] If spec was outdated, it has been updated or a new spec package created

## Reviewer Notes

_Optional: add context for reviewers about decisions made during implementation._
