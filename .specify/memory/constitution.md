<!--
SYNC IMPACT REPORT
==================
Version Change: TEMPLATE → 1.0.0
Modified Principles: All (Initial Constitution Creation)
Added Sections:
  - Core Principles (7 principles defined)
  - Architectural Standards
  - Development Workflow & Quality Gates
  - Governance
Removed Sections: None (initial creation)
Templates Requiring Updates:
  ✅ plan-template.md - Constitution Check section aligned with 7 principles
  ✅ spec-template.md - Requirements section aligned with testing standards
  ✅ tasks-template.md - Task categorization aligned with modular architecture
Follow-up TODOs: None
==================
-->

# Athena Constitution

## Core Principles

### I. Modular Microservices Architecture

**Principle**: Athena MUST maintain a clean separation of concerns through domain-specific microservices.

- Each microservice (`athena-boot-*`) MUST represent a single SDLC data collection domain (core, git, kube, pipeline,
  tms, spec, metric)
- Every microservice MUST have a corresponding Feign client module (`athena-boot-*-feign`) for inter-service
  communication
- Feign client modules MUST NOT contain business logic; they exist solely for API contract definition
- Core microservices MUST NOT depend on Feign client modules
- Cross-cutting concerns MUST reside in `athena-common`; test utilities MUST reside in `athena-common-test`
- The API Gateway (`athena-gateway`) MUST be the single entry point for external clients

**Rationale**: This separation enables independent deployment, testing, and scaling of each SDLC data collection domain
while maintaining contract clarity through dedicated Feign clients.

### II. Database-First with Migration Control

**Principle**: All database schema changes MUST be version-controlled and applied through Flyway migrations.

- Every schema change MUST be defined in a numbered Flyway migration script
- Migrations MUST be idempotent and support both forward and rollback scenarios
- TestContainers MUST use golden database images for integration tests
- Production databases MUST NEVER be modified manually; all changes through migration scripts only
- JPA entities MUST reflect the database schema, not drive it

**Rationale**: Flyway migrations ensure reproducible deployments, auditable schema changes, and consistent environments
across development, testing, and production.

### III. Test-First with TestContainers (NON-NEGOTIABLE)

**Principle**: Testing MUST follow a comprehensive, container-based approach.

- **Integration Tests MUST**:
    - Use TestContainers to spin up real PostgreSQL instances
    - Execute against golden database images from Docker Registry
    - Test complete user journeys, not just individual units
- **Performance Tests MUST**:
    - Use Locust scenarios executed after functional tests
    - Measure against defined SLAs for each microservice
    - Run with fresh golden database instances
- **Test Sequence MUST follow**: Golden DB start → Spring Boot app start → Functional tests (JUnit) → DB restart with
  new golden image → Performance tests (Locust) → Cleanup
- **All microservices MUST** achieve minimum test coverage thresholds enforced by JaCoCo and SonarCloud

**Rationale**: TestContainers provide production-like testing environments, catching integration issues early. The
golden database pattern ensures consistent, repeatable test data.

### IV. API-First Design with OpenAPI

**Principle**: All REST APIs MUST be documented with SpringDoc OpenAPI (Swagger) and follow REST conventions.

- Every `@RestController` endpoint MUST be documented with OpenAPI annotations
- API contracts MUST use proper HTTP methods (GET, POST, PUT, DELETE, PATCH) and status codes
- DTOs MUST use validation annotations (`@Valid`, constraint annotations)
- Spring Data REST MUST be used for standard CRUD operations where appropriate
- Breaking API changes MUST follow semantic versioning rules

**Rationale**: API-first design ensures contract clarity, enables automated client generation, and facilitates
integration with external systems.

### V. Quality Gates with SonarCloud

**Principle**: Code quality MUST be continuously monitored and enforced through automated gates.

- **SonarCloud integration is MANDATORY** for all modules
- **JaCoCo coverage reports MUST** be generated for all modules except:
    - Application main classes (`*Application.java`)
    - Configuration classes (`config/*.java`)
    - DTOs and entities (`model/*`, `entity/*`)
    - MapStruct mappers (`mapper/*`)
    - Custom exceptions (`exception/*`)
    - Test code
- **Minimum quality gates**:
    - No new code smells introduced in PR
    - No new security vulnerabilities
    - Coverage on new code ≥ project baseline
- **All PRs MUST pass SonarCloud quality gate** before merge

**Rationale**: Automated quality gates prevent technical debt accumulation and maintain high code quality standards
across the entire platform.

### VI. Dependency Management through Parent POMs

**Principle**: All dependency versions MUST be centrally managed in parent POMs.

- `athena-parent` MUST define common dependencies and plugins for non-Boot modules
- `athena-boot-parent` MUST define Spring Boot-specific dependencies and versions
- Individual modules MUST NOT declare dependency versions; they MUST inherit from parent
- Version upgrades MUST be coordinated through parent POM updates
- Use Maven Wrapper (`./mvnw`) for consistent build tool versioning
- Renovate MUST be configured to automate dependency updates with proper testing

**Rationale**: Centralized dependency management prevents version conflicts, simplifies upgrades, and ensures
consistency across all microservices.

### VII. Observability and Operational Excellence

**Principle**: All microservices MUST be production-ready with comprehensive observability.

- **Logging MUST**:
    - Use structured logging with correlation IDs for request tracing
    - Log at appropriate levels (ERROR for failures, WARN for degraded state, INFO for key events, DEBUG for details)
    - Never log sensitive data (credentials, PII, tokens)
- **Metrics MUST**:
    - Expose Spring Boot Actuator endpoints for health checks
    - Track business metrics specific to each SDLC domain
    - Support Prometheus/Grafana integration patterns
- **Error Handling MUST**:
    - Use custom exceptions from `athena-common` for domain errors
    - Return proper HTTP status codes and error payloads
    - Include correlation IDs in error responses
- **Docker Images MUST**:
    - Be buildable via `docker-maven-plugin`
    - Follow multi-stage build patterns for minimal image size
    - Include health check definitions

**Rationale**: Comprehensive observability enables rapid troubleshooting in production and proactive issue
identification across the SDLC metrics collection platform.

## Architectural Standards

### Package Structure (MANDATORY)

Every `athena-boot-*` microservice MUST follow this structure:

```
src/main/java/org/catools/athena/[domain]/
├── entity/          # JPA entities (excluded from Sonar coverage)
├── model/           # DTOs and data transfer objects (excluded from Sonar coverage)
├── mapper/          # MapStruct mappers (excluded from Sonar coverage)
├── repository/      # Spring Data JPA repositories
├── service/         # Business logic layer
├── controller/      # REST controllers (@RestController)
├── config/          # Spring configuration classes (excluded from Sonar coverage)
└── exception/       # Custom exceptions (excluded from Sonar coverage)
```

### Technology Stack (MANDATORY)

- **Java**: JDK 21 (non-negotiable)
- **Build Tool**: Maven 3.8.6+ with Maven Wrapper (`./mvnw`)
- **Framework**: Spring Boot 3.x with:
    - Spring Data JPA (database access)
    - Spring Data REST (CRUD endpoints)
    - Spring Cloud OpenFeign (inter-service communication)
- **Database**: PostgreSQL with Flyway migrations
- **Testing**: JUnit 5, TestContainers, Locust
- **API Documentation**: SpringDoc OpenAPI
- **Code Quality**: SonarCloud + JaCoCo
- **Code Simplification**: Lombok (`@Data`, `@UtilityClass`, etc.)

### Module Naming Conventions (MANDATORY)

- **Microservices**: `athena-boot-[domain]` (e.g., `athena-boot-git`, `athena-boot-pipeline`)
- **Feign Clients**: `athena-boot-[domain]-feign` (e.g., `athena-boot-git-feign`)
- **Shared Libraries**: `athena-common`, `athena-common-test`
- **Parent POMs**: `athena-parent`, `athena-boot-parent`
- **Gateway**: `athena-gateway`

## Development Workflow & Quality Gates

### Build and Test Workflow

1. **Local Development**:
   ```bash
   ./mvnw clean compile -U              # Compile with dependency updates
   ./mvnw install -DskipTests           # Install without tests
   ./mvnw test                          # Run unit + integration tests
   ./mvnw verify                        # Run full test suite including Locust
   ./mvnw package docker:build -DskipTests  # Build Docker images
   ```

2. **Pre-Commit Requirements**:
    - Code MUST compile without errors
    - Lombok annotations MUST be used to reduce boilerplate
    - Spring Boot conventions MUST be followed

3. **Pre-PR Requirements**:
    - All tests MUST pass (`./mvnw verify`)
    - SonarCloud quality gate MUST be green
    - Docker images MUST build successfully
    - API documentation MUST be up-to-date

4. **Integration Test Execution**:
    - TestContainer starts golden PostgreSQL database
    - Spring Boot app starts with test database
    - JUnit functional tests execute
    - DB restarts with fresh golden image
    - Locust performance tests execute
    - All containers cleaned up

### Code Review Standards

- **Every PR MUST**:
    - Pass all automated tests (unit, integration, performance)
    - Pass SonarCloud quality gate
    - Follow package structure conventions
    - Include proper API documentation
    - Update Flyway migrations if schema changes
    - Update corresponding Feign client if API changes
    - Follow Lombok best practices

- **Reviewers MUST verify**:
    - Adherence to Core Principles I-VII
    - Proper exception handling with custom exceptions
    - Structured logging with appropriate levels
    - Test coverage meets minimum thresholds
    - No hardcoded configuration; use Spring Boot properties

### Breaking Change Protocol

When introducing breaking changes:

1. **Document the change** in migration notes
2. **Update semantic version** appropriately (MAJOR bump)
3. **Update all dependent Feign clients** in the same PR
4. **Provide migration guide** for consuming services
5. **Coordinate deployment** across dependent services

## Governance

### Constitutional Authority

- This Constitution **supersedes all other development practices** and conventions
- All code, PRs, and architectural decisions **MUST be validated against these principles**
- Violations **MUST be explicitly justified** in implementation plans (Complexity Tracking section)
- Unjustified violations **MUST be rejected** in code review

### Amendment Procedure

1. **Proposal**: Document proposed change with rationale and impact analysis
2. **Review**: Technical leads review against project goals and current architecture
3. **Approval**: Requires consensus from core maintainers
4. **Migration Plan**: Document how existing code will be brought into compliance
5. **Version Bump**: Update constitution version following semantic versioning:
    - **MAJOR**: Backward incompatible governance/principle removals or redefinitions
    - **MINOR**: New principle/section added or materially expanded guidance
    - **PATCH**: Clarifications, wording, typo fixes, non-semantic refinements
6. **Propagation**: Update all dependent templates and documentation

### Compliance Review

- **Quarterly review** of codebase against constitution principles
- **Automated checks** via SonarCloud rules aligned with principles
- **Onboarding materials** MUST reference this constitution
- **All new team members** MUST read and acknowledge constitution principles

### Living Document

- Constitution version and amendments tracked in this document
- Runtime development guidance available in `.github/copilot-instructions.md`
- Template alignment tracked in Sync Impact Report (HTML comment at top of file)

**Version**: 1.0.0 | **Ratified**: 2025-12-03 | **Last Amended**: 2025-12-03
