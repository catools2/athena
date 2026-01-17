# Specify Framework for Athena

Specify is a structured feature development framework that helps teams plan, design, and implement features with
consistency and quality.

## 🎯 Quick Start

### 1. Create a New Feature

```bash
# Start a new feature branch with Specify scaffolding
.specify/scripts/bash/create-new-feature.sh "your-feature-name"
```

This creates:

- Feature branch: `feature/your-feature-name`
- Spec directory: `.specify/specs/your-feature-name/`
- Template files ready for you to fill out

### 2. Write Your Feature Specification

Edit `.specify/specs/your-feature-name/spec.md`:

```markdown
# Feature: Your Feature Name

## Goal
What problem does this solve? Why is it important?

## Requirements

### Functional Requirements
- [ ] Requirement 1
- [ ] Requirement 2

### Non-Functional Requirements
- [ ] Performance: Response time < 200ms
- [ ] Scalability: Handle 1000 concurrent users
- [ ] Security: JWT authentication required

## Technical Context

### Affected Modules
- athena-boot-core (entity changes)
- athena-boot-git (new service)
- athena-common (shared utilities)

### Database Changes
- New table: git_blame_analysis
- Migration: V{n}__add_blame_analysis.sql

### External Dependencies
- Git API for blame data
- None (using existing Feign clients)

## Success Criteria
- [ ] All tests pass (unit + integration)
- [ ] API documented with OpenAPI
- [ ] Performance tests show < 200ms response
```

### 3. Generate Implementation Plan

Use GitHub Copilot to generate your plan:

```
@workspace /speckit.plan
```

Or manually trigger:

```bash
.specify/scripts/bash/setup-plan.sh --json
```

This generates:

- **plan.md**: Full implementation plan with phases
- **research.md**: Architectural decisions and alternatives
- **data-model.md**: Entity definitions and relationships
- **contracts/**: OpenAPI specifications
- **quickstart.md**: Getting started guide

### 4. Implement According to Plan

Follow the phases in `plan.md`:

**Phase 0: Research**

- Resolve all technical unknowns
- Document architectural decisions
- Identify best practices

**Phase 1: Design**

- Define data models (entities, DTOs)
- Design API contracts (OpenAPI)
- Plan database migrations

**Phase 2: Implementation**

- Write code following the design
- Add proper logging and error handling
- Follow Athena coding standards

**Phase 3: Testing**

- Unit tests (JUnit 5)
- Integration tests (TestContainers)
- Performance tests (Locust if needed)

**Phase 4: Documentation**

- Update API documentation
- Add inline code comments
- Update README if needed

### 5. Update Agent Context

After implementation, update AI agent instructions:

```bash
.specify/scripts/bash/update-agent-context.sh copilot
```

This keeps GitHub Copilot aware of:

- New technologies you've added
- Patterns you're using
- Recent architectural decisions

## 📁 Directory Structure

```text
.specify/
├── constitution.md           # Project principles and standards
├── memory/
│   └── constitution.md       # Historical project knowledge
├── scripts/
│   └── bash/
│       ├── setup-plan.sh            # Initialize feature planning
│       ├── create-new-feature.sh    # Create feature branches
│       ├── update-agent-context.sh  # Update AI agent context
│       ├── check-prerequisites.sh   # Validate environment
│       └── common.sh               # Shared utilities
├── templates/
│   ├── spec-template.md        # Feature specification template
│   ├── plan-template.md        # Implementation plan template
│   ├── agent-file-template.md  # Agent context template
│   ├── tasks-template.md       # Task breakdown template
│   └── checklist-template.md   # Quality checklist template
├── specs/
│   └── {feature-name}/         # Created per feature
│       ├── spec.md             # Your feature specification
│       ├── plan.md             # Generated implementation plan
│       ├── research.md         # Architectural decisions
│       ├── data-model.md       # Entity definitions
│       ├── quickstart.md       # Getting started guide
│       └── contracts/          # API specifications
├── WORKFLOW.md                 # Detailed workflow guide
├── INTEGRATION.md              # Integration status and summary
└── README.md                   # This file
```

## 🏗️ Athena-Specific Workflow

### Module Selection

Specify helps you choose the right module:

| Feature Type       | Module(s)               | Considerations                      |
|--------------------|-------------------------|-------------------------------------|
| Git metrics        | `athena-boot-git/`      | Add entities, services, controllers |
| Pipeline metrics   | `athena-boot-pipeline/` | Integration with CI/CD APIs         |
| Kubernetes metrics | `athena-boot-kube/`     | K8s client integration              |
| TMS integration    | `athena-boot-tms/`      | Jira/Zephyr APIs                    |
| Spec analysis      | `athena-boot-spec/`     | OpenAPI parsing                     |
| Metric aggregation | `athena-boot-metric/`   | Cross-service data                  |
| Shared utilities   | `athena-common/`        | Used by multiple modules            |
| Test utilities     | `athena-common-test/`   | Test helpers                        |

### Database Changes

All database changes use Flyway:

1. **Create migration file**: `src/main/resources/db/migration/V{n}__{description}.sql`
2. **Follow naming convention**: Sequential version numbers
3. **Test with TestContainers**: Integration tests validate migrations
4. **Document in plan.md**: Record migration rationale

Example:

```sql
-- V25__add_blame_analysis.sql
CREATE TABLE IF NOT EXISTS blame_analysis (
    id BIGSERIAL PRIMARY KEY,
    repository_id BIGINT NOT NULL REFERENCES repository(id),
    file_path VARCHAR(500) NOT NULL,
    author VARCHAR(100) NOT NULL,
    line_count INTEGER NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (repository_id, file_path, author)
);

CREATE INDEX idx_blame_analysis_repo ON blame_analysis(repository_id);
```

### Testing Strategy

Specify plans include three test types:

**1. Unit Tests** (`src/test/java`)

- Service layer business logic
- Mapper transformations
- Utility functions
- Mock external dependencies

**2. Integration Tests** (`src/it/java`)

- Full Spring Boot context
- Real PostgreSQL via TestContainers
- End-to-end API testing
- Database migration validation

**3. Performance Tests** (Locust)

- Load testing critical endpoints
- Concurrent user simulation
- Response time validation
- Only for high-traffic features

### Feign Client Integration

If your feature needs to call other microservices:

1. **Create Feign interface** in `athena-boot-{module}-feign/`
2. **Define contracts** using OpenAPI annotations
3. **Add to main module** as dependency
4. **Enable Feign client** in Spring Boot config
5. **Test with WireMock** in integration tests

Example:

```java
@FeignClient(
    name = "git-service", 
    url = "${athena.git.url}",
    configuration = OpenFeignConfiguration.class
)
public interface GitFeignClient {
    @GetMapping("/api/repositories/{id}")
    RepositoryDto getRepository(@PathVariable Long id);
}
```

## 🔍 Constitution-Based Development

Every feature is validated against `.specify/constitution.md`:

### Core Principles Enforced:

✅ Java 21 requirement  
✅ Microservices architecture  
✅ Spring Boot best practices  
✅ Database migrations via Flyway  
✅ TestContainers for integration tests  
✅ Module dependency rules  
✅ Package structure conventions  
✅ Code quality standards (JaCoCo, SonarCloud)

### Decision Framework:

1. Does it maintain separation of concerns?
2. Does it follow microservices best practices?
3. Can it be tested effectively?
4. Does it integrate with metrics collection?
5. Is it consistent with existing patterns?
6. Does it maintain backwards compatibility?

## 🤖 AI Agent Integration

Specify automatically maintains context for:

- **GitHub Copilot**: `.github/agents/copilot-instructions.md`
- **Claude**: `CLAUDE.md` (if used)
- **Cursor**: `.cursor/rules/specify-rules.mdc` (if used)
- **Other agents**: Configurable via update script

What gets tracked:

- Technology stack and versions
- Project structure and modules
- Build/test/run commands
- Code style conventions
- Recent features and changes
- Manual additions (preserved)

## 📋 Example Workflow

Let's walk through adding Git blame analysis:

```bash
# 1. Start feature
.specify/scripts/bash/create-new-feature.sh "git-blame-analysis"

# 2. Fill spec.md
vim .specify/specs/git-blame-analysis/spec.md
```

Spec content:

```markdown
# Feature: Git Blame Analysis

## Goal
Track code ownership and contribution patterns by analyzing git blame data.

## Requirements
- Collect blame data for all files in repository
- Store author, line count, and file path
- Expose REST API for querying blame data
- Support bulk analysis of repositories

## Technical Context
- Module: athena-boot-git
- Database: New table blame_analysis
- Integration: Git CLI commands via ProcessBuilder
- Metrics: Lines per author, file ownership distribution
```

```bash
# 3. Generate plan with Copilot
# In IDE: @workspace /speckit.plan

# 4. Review generated files
ls -la .specify/specs/git-blame-analysis/
# plan.md - Implementation strategy
# research.md - Git blame best practices
# data-model.md - BlameAnalysis entity definition
# contracts/blame-api.yaml - OpenAPI spec

# 5. Implement according to plan
cd athena-boot-git

# Phase 1: Data Model
# - Create entity: BlameAnalysis.java
# - Create DTO: BlameAnalysisDto.java
# - Create mapper: BlameAnalysisMapper.java
# - Add migration: V25__add_blame_analysis.sql

# Phase 2: Business Logic
# - Create service: BlameAnalysisService.java
# - Add repository: BlameAnalysisRepository.java
# - Implement blame collection logic

# Phase 3: API Layer
# - Create controller: BlameAnalysisController.java
# - Add OpenAPI documentation
# - Implement error handling

# Phase 4: Testing
# - Unit tests: BlameAnalysisServiceTest.java
# - Integration tests: BlameAnalysisControllerIT.java
# - Test data generators

# 6. Build and verify
cd ..
./mvnw clean verify -pl athena-boot-git

# 7. Update agent context
.specify/scripts/bash/update-agent-context.sh copilot

# 8. Commit and push
git add .
git commit -m "feat(git): add blame analysis for code ownership tracking"
git push origin feature/git-blame-analysis
```

## 🛠️ Scripts Reference

### create-new-feature.sh

Creates a new feature branch and initializes Specify scaffolding.

```bash
.specify/scripts/bash/create-new-feature.sh "feature-name"
```

### setup-plan.sh

Initializes or updates the implementation plan for current feature.

```bash
.specify/scripts/bash/setup-plan.sh --json
```

### update-agent-context.sh

Updates AI agent context files with latest technology and patterns.

```bash
# Update specific agent
.specify/scripts/bash/update-agent-context.sh copilot

# Update all agent files
.specify/scripts/bash/update-agent-context.sh
```

### check-prerequisites.sh

Validates that environment is ready for Specify workflow.

```bash
.specify/scripts/bash/check-prerequisites.sh
```

## 🐛 Troubleshooting

### "Not on a feature branch"

**Error**: `ERROR: Not on a feature branch. Current branch: main`

**Solution**: Create a feature branch first

```bash
git checkout -b feature/your-feature-name
```

### Scripts not executable

**Error**: `Permission denied: .specify/scripts/bash/setup-plan.sh`

**Solution**: Make scripts executable

```bash
chmod +x .specify/scripts/bash/*.sh
```

### Missing plan.md

**Error**: Agent context update fails to find plan

**Solution**: Ensure you've generated the plan

```bash
# In IDE: @workspace /speckit.plan
# Or manually:
.specify/scripts/bash/setup-plan.sh --json
```

### Constitution violations

**Error**: Plan generation reports violations

**Solution**:

1. Review `.specify/constitution.md`
2. Align your spec with project standards
3. Document exceptions in spec if justified

### Maven build failures

**Error**: Compilation errors after implementing

**Solution**:

```bash
# Clean and rebuild
./mvnw clean install -DskipTests

# Check specific module
./mvnw clean verify -pl athena-boot-{module}
```

## 📚 Additional Resources

- **[Detailed Workflow](.specify/WORKFLOW.md)**: Step-by-step guide
- **[Integration Status](.specify/INTEGRATION.md)**: Setup summary
- **[Constitution](.specify/constitution.md)**: Project principles
- **[Copilot Instructions](../.github/agents/copilot-instructions.md)**: AI context

External:

- [Specify Framework](https://github.com/specify-project/specify)
- [Maven Multi-Module Guide](https://maven.apache.org/guides/mini/guide-multiple-modules.html)
- [Spring Boot Reference](https://docs.spring.io/spring-boot/reference/)
- [TestContainers Documentation](https://testcontainers.com/)

## 💡 Tips

1. **Start small**: Try a simple feature first to learn the workflow
2. **Read generated plans**: They contain valuable architectural insights
3. **Keep specs focused**: One feature per spec, single responsibility
4. **Test early**: Write tests alongside implementation
5. **Update context**: Run update-agent-context.sh after major changes
6. **Review constitution**: Ensure your design aligns with project principles
7. **Document decisions**: Use research.md to explain why, not just what

## 🤝 Contributing to Specify

If you find issues with the Specify integration:

1. Check `.specify/constitution.md` for project-specific rules
2. Review `.specify/templates/` for customizable templates
3. Update scripts in `.specify/scripts/bash/` if needed
4. Share improvements with the team

---

**Last Updated**: December 22, 2024  
**Specify Version**: Custom integration for Athena  
**Status**: ✅ Fully Integrated and Ready to Use

