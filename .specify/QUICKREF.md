# Specify Quick Reference Card

## 🚀 Common Commands

```bash
# Start new feature
.specify/scripts/bash/create-new-feature.sh "feature-name"

# Generate implementation plan (use Copilot)
@workspace /speckit.plan

# Update AI agent context
.specify/scripts/bash/update-agent-context.sh copilot

# Check environment
.specify/scripts/bash/check-prerequisites.sh
```

## 📁 Key Files

| File                                     | Purpose                             |
|------------------------------------------|-------------------------------------|
| `.specify/specs/{feature}/spec.md`       | Your feature requirements           |
| `.specify/specs/{feature}/plan.md`       | Generated implementation plan       |
| `.specify/specs/{feature}/research.md`   | Architectural decisions             |
| `.specify/specs/{feature}/data-model.md` | Entity definitions                  |
| `.specify/constitution.md`               | Project principles (DO NOT VIOLATE) |

## 🏗️ Athena Module Guide

| What You're Building | Use This Module         | Also Consider              |
|----------------------|-------------------------|----------------------------|
| Git metrics          | `athena-boot-git/`      | Add Feign client if needed |
| Pipeline metrics     | `athena-boot-pipeline/` | CI/CD API integration      |
| K8s metrics          | `athena-boot-kube/`     | K8s client setup           |
| Test metrics         | `athena-boot-tms/`      | Jira/Zephyr APIs           |
| API spec analysis    | `athena-boot-spec/`     | OpenAPI parsing            |
| Metric aggregation   | `athena-boot-metric/`   | Cross-service data         |
| Shared utility       | `athena-common/`        | Used by all modules        |
| Test helper          | `athena-common-test/`   | TestContainers, mocks      |

## 📝 Spec Template Quick Fill

```markdown
# Feature: {Name}

## Goal

{One sentence: what problem does this solve?}

## Requirements

- [ ] {Functional requirement 1}
- [ ] {Functional requirement 2}
- [ ] {Non-functional: performance, security, etc.}

## Technical Context

- Module: athena-boot-{module}
- Database: {New table? Migration needed?}
- Integration: {External APIs? Feign clients?}
- Metrics: {What will this measure?}

## Success Criteria

- [ ] Tests pass (unit + integration)
- [ ] API documented
- [ ] Performance validated
```

## 🧪 Test Strategy

| Test Type   | Location        | Purpose        | Tools                            |
|-------------|-----------------|----------------|----------------------------------|
| Unit        | `src/test/java` | Business logic | JUnit 5, Mockito                 |
| Integration | `src/it/java`   | Full stack     | TestContainers, Spring Boot Test |

## 🗄️ Database Migration Checklist

- [ ] Create `V{n}__{description}.sql` in `src/main/resources/db/migration/`
- [ ] Use sequential version number
- [ ] Test with TestContainers
- [ ] Document in plan.md
- [ ] Add rollback notes if complex

## 🔒 Constitution Non-Negotiables

✅ **MUST** use Java 21  
✅ **MUST** use Flyway for database changes  
✅ **MUST** pass all tests before merge  
✅ **MUST** have test coverage  
✅ **MUST** use Maven Wrapper (`./mvnw`)  
✅ **MUST** follow package structure conventions  
✅ **MUST** have Docker available  
✅ **MUST** extend athena-boot-parent for Spring Boot apps

## 🏃 Development Workflow

```
1. Create feature branch
   ↓
2. Write spec.md
   ↓
3. Generate plan with @workspace /speckit.plan
   ↓
4. Review plan.md, research.md, data-model.md
   ↓
5. Implement following phases
   ↓
6. Write tests (unit + integration)
   ↓
7. Build and verify: ./mvnw verify
   ↓
8. Update agent context
   ↓
9. Commit and create PR
```

## 🐛 Quick Fixes

| Problem                  | Solution                              |
|--------------------------|---------------------------------------|
| "Not on feature branch"  | `git checkout -b feature/name`        |
| "Permission denied"      | `chmod +x .specify/scripts/bash/*.sh` |
| "Constitution violation" | Review `.specify/constitution.md`     |
| Maven build fails        | `./mvnw clean install -DskipTests`    |
| Tests fail               | Check TestContainers/Docker running   |

## 📦 Maven Commands

```bash
# Compile
./mvnw clean compile -U

# Install without tests
./mvnw install -DskipTests

# Run tests
./mvnw test

# Run integration tests
./mvnw verify

# Test specific module
./mvnw test -pl athena-boot-{module}

# Build Docker images
./mvnw package docker:build -DskipTests
```

## 🎯 Quick Wins

**For simple CRUD features:**

1. Add entity in `entity/`
2. Add repository extending `JpaRepository`
3. Add service with `@Service`
4. Add controller with `@RestController`
5. Add Flyway migration
6. Write tests

**For external integrations:**

1. Create Feign client in `-feign` module
2. Add service to consume client
3. Add error handling
4. Add retry logic
5. Write integration tests with WireMock

**For shared utilities:**

1. Add to `athena-common/`
2. Document in JavaDoc
3. Write comprehensive tests
4. Update constitution if it's a pattern

## 📞 Getting Help

1. Read the full guide: `.specify/WORKFLOW.md`
2. Check integration status: `.specify/INTEGRATION.md`
3. Review constitution: `.specify/constitution.md`
4. Ask GitHub Copilot (context is up-to-date)
5. Check existing similar features in codebase

---

**Keep this card handy!** Print it, bookmark it, or keep it open in a tab.

