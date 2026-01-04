# Specify Workflow Guide for Athena

This document describes how to use Specify for feature development in the Athena project.

## Prerequisites

- Git installed and configured
- Maven 3.8.6+ (via Maven Wrapper `./mvnw`)
- JDK 21
- Docker (for building and testing)

## Workflow Steps

### 1. Start a New Feature

```bash
# Create a new feature branch and initialize Specify
.specify/scripts/bash/create-new-feature.sh "feature-name"

# This will:
# - Create a feature branch (feature/feature-name)
# - Set up .specify/specs/feature-name/ directory
# - Copy spec template for you to fill out
```

### 2. Write Feature Specification

Edit `.specify/specs/feature-name/spec.md` with:
- **Goal**: What problem does this solve?
- **Requirements**: Functional and non-functional requirements
- **Success Criteria**: How do we know it's done?
- **Technical Context**: Which modules are affected?

For Athena features, consider:
- Which microservice(s) need changes?
- Are Feign clients needed?
- Does this require database migrations?
- What metrics should be collected?

### 3. Generate Implementation Plan

```bash
# Use GitHub Copilot Chat with the speckit.plan command
# In your IDE, type: @workspace /speckit.plan

# Or manually run:
.specify/scripts/bash/setup-plan.sh --json
```

This will:
- Parse your spec.md
- Create plan.md with phases
- Check against constitution.md
- Generate research.md for unknowns
- Create data-model.md for entities
- Generate API contracts

### 4. Review Generated Artifacts

Check these files in `.specify/specs/feature-name/`:
- **plan.md**: Full implementation plan with phases
- **research.md**: Architectural decisions and rationale
- **data-model.md**: Entity definitions and relationships
- **contracts/**: OpenAPI/API specifications
- **quickstart.md**: Getting started guide

### 5. Implement According to Plan

Follow the phases in plan.md:
- **Phase 0**: Research and decisions
- **Phase 1**: Design (data model, contracts)
- **Phase 2**: Implementation (code the feature)
- **Phase 3**: Testing (unit, integration, performance)
- **Phase 4**: Documentation and deployment

### 6. Update Agent Context

After completing implementation:

```bash
# Update Copilot instructions with new tech/patterns
.specify/scripts/bash/update-agent-context.sh copilot

# This updates .github/agents/copilot-instructions.md
```

## Athena-Specific Considerations

### Module Organization

When your feature affects:

**Single microservice**: Work in that module (e.g., `athena-boot-git/`)
- Update entity/, repository/, service/, controller/
- Add Flyway migration if database changes
- Add tests in src/test/ and src/it/

**Multiple microservices**: Consider:
- Is this shared logic? → Put in `athena-common/`
- Is this a new integration? → Create new `athena-boot-{name}/` module
- Cross-service communication? → Use Feign clients

**API contracts**: Document in each module's OpenAPI specs
- SpringDoc annotations on controllers
- Generate openapi.yaml during build

### Testing Strategy

Your plan.md should include:
- **Unit tests**: Business logic in service layer
- **Integration tests**: Full Spring Boot context with TestContainers
- **Contract tests**: Feign client validation

### Build Verification

Before committing:

```bash
# Compile everything
./mvnw clean compile -U

# Run tests
./mvnw test

# Full verification
./mvnw verify

# Check specific module
./mvnw test -pl athena-boot-{module}
```

## Tips for Writing Good Specs

1. **Be specific about modules**: Name exact athena-boot-* modules
2. **Identify data sources**: Which SDLC data are you collecting?
3. **Define metrics clearly**: What measurements will this enable?
4. **Consider existing patterns**: Look at similar modules first
5. **Think about deployment**: How does this fit in microservices architecture?

## Example Feature Workflow

```bash
# 1. Start feature for Git blame analysis
.specify/scripts/bash/create-new-feature.sh "git-blame-metrics"

# 2. Fill out spec.md
# Technical Context:
# - Module: athena-boot-git
# - Database: New table for blame_analysis
# - Integration: Git API for blame data
# - Metrics: Lines per author, file ownership

# 3. Generate plan
# Use @workspace /speckit.plan in Copilot Chat

# 4. Implement following plan.md phases
cd athena-boot-git
# - Add BlameAnalysis entity
# - Create Flyway migration V{n}__add_blame_analysis.sql
# - Implement BlameService
# - Add REST controller
# - Write tests

# 5. Build and test
cd ..
./mvnw clean verify -pl athena-boot-git

# 6. Update agent context
.specify/scripts/bash/update-agent-context.sh copilot

# 7. Create PR
git add .
git commit -m "feat(git): add blame analysis metrics"
git push origin feature/git-blame-metrics
```

## Integration with GitHub Copilot

Specify enhances Copilot by:
- Providing structured context via agent instructions
- Maintaining project constitution and patterns
- Tracking architectural decisions in research.md
- Keeping technology stack up-to-date

Your `.github/agents/copilot-instructions.md` is automatically updated with:
- New technologies introduced
- Recent features and changes
- Project structure evolution
- Common commands for your stack

## Troubleshooting

**Script won't run**: Check execute permissions
```bash
chmod +x .specify/scripts/bash/*.sh
```

**Can't find feature branch**: Ensure you're in a feature/* branch
```bash
git checkout -b feature/your-feature-name
```

**Constitution violations**: Review `.specify/constitution.md` for project rules

**Module dependency issues**: Check parent POM dependency management in `athena-parent/pom.xml`

## Resources

- [Specify Documentation](https://github.com/specify-project/specify)
- [Athena Architecture](./.github/copilot-instructions.md)
- [Constitution](./.specify/constitution.md)
- [Maven Multi-Module Best Practices](https://maven.apache.org/guides/mini/guide-multiple-modules.html)

