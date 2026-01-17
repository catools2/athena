# Implementation Plan: [FEATURE]

**Branch**: `[###-feature-name]` | **Date**: [DATE] | **Spec**: [link]
**Input**: Feature specification from `/specs/[###-feature-name]/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/commands/plan.md` for the
execution workflow.

## Summary

[Extract from feature spec: primary requirement + technical approach from research]

## Technical Context

**Language/Version**: Java 21 (JDK-21, non-negotiable per Athena Constitution)  
**Framework**: Spring Boot 3.x with Spring Data JPA, Spring Data REST, Spring Cloud OpenFeign  
**Build Tool**: Maven 3.8.6+ (use Maven Wrapper: `./mvnw`)  
**Database**: PostgreSQL with Flyway migrations  
**Testing**: JUnit 5, TestContainers (PostgreSQL golden DB), Locust (performance)  
**API Documentation**: SpringDoc OpenAPI (Swagger)  
**Code Quality**: SonarCloud integration with JaCoCo coverage  
**Code Simplification**: Lombok annotations (`@Data`, `@UtilityClass`, `@Slf4j`, etc.)  
**Deployment**: Docker containers via `docker-maven-plugin`  
**Project Type**: Microservices architecture for SDLC metrics collection  
**Performance Goals
**: [Feature-specific: e.g., handle 1000 metrics/sec, <500ms API response p95, support 10k concurrent connections or NEEDS CLARIFICATION]  
**Constraints
**: [Feature-specific: e.g., must support real-time data ingestion, backward compatible API, <2GB memory per microservice or NEEDS CLARIFICATION]  
**Scale/Scope
**: [Feature-specific: e.g., ingest from 100+ repositories, store 1M+ pipeline runs, support 50+ concurrent users or NEEDS CLARIFICATION]

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

Verify compliance with Athena Constitution (v1.0.0+):

- [ ] **Principle I - Modular Microservices**: Does this feature maintain clean separation of concerns? If adding
  functionality to existing microservice, is it within the correct domain (core/git/kube/pipeline/tms/spec/metric)? If
  creating new microservice, is domain boundary justified?
- [ ] **Principle II - Database-First**: Are all schema changes defined in Flyway migrations? Are migrations idempotent
  and numbered correctly?
- [ ] **Principle III - Test-First with TestContainers**: Are integration tests defined using TestContainers with golden
  database? Are Locust performance scenarios included?
- [ ] **Principle IV - API-First Design**: Are all REST endpoints documented with SpringDoc OpenAPI annotations? Do DTOs
  use proper validation annotations?
- [ ] **Principle V - Quality Gates**: Will this feature maintain/improve SonarCloud quality metrics? Are excluded paths
  properly configured in parent POM?
- [ ] **Principle VI - Dependency Management**: Are all dependencies declared in parent POMs without version numbers in
  feature modules?
- [ ] **Principle VII - Observability**: Does this feature include structured logging with correlation IDs? Are actuator
  endpoints configured? Is error handling using custom exceptions from athena-common?

**Violations Requiring Justification
**: [List any principle violations with explicit justification in Complexity Tracking section below]

## Project Structure

### Documentation (this feature)

```text
specs/[###-feature]/
├── plan.md              # This file (/speckit.plan command output)
├── research.md          # Phase 0 output (/speckit.plan command)
├── data-model.md        # Phase 1 output (/speckit.plan command)
├── quickstart.md        # Phase 1 output (/speckit.plan command)
├── contracts/           # Phase 1 output (/speckit.plan command)
└── tasks.md             # Phase 2 output (/speckit.tasks command - NOT created by /speckit.plan)
```

### Source Code (repository root)

<!--
  ACTION REQUIRED: Specify which microservice(s) will be modified for this feature.
  Athena follows a modular microservices architecture. Identify the target module(s)
  and the package structure within each. Delete unused options below.
-->

```text
# [REMOVE IF UNUSED] Option 1: Modify existing microservice (MOST COMMON)
athena-boot-[domain]/              # e.g., athena-boot-git, athena-boot-pipeline
├── src/main/java/org/catools/athena/[domain]/
│   ├── entity/                    # JPA entities
│   ├── model/                     # DTOs and data transfer objects
│   ├── mapper/                    # MapStruct mappers
│   ├── repository/                # Spring Data JPA repositories
│   ├── service/                   # Business logic layer
│   ├── controller/                # REST controllers
│   ├── config/                    # Spring configuration
│   └── exception/                 # Custom exceptions
├── src/main/resources/
│   ├── db/migration/              # Flyway migration scripts
│   └── application.yml            # Spring Boot configuration
├── src/test/java/                 # Unit tests

athena-boot-[domain]-feign/        # Corresponding Feign client (if API changes)
└── src/main/java/org/catools/athena/[domain]/feign/
    └── [Domain]FeignClient.java   # Feign interface definition

# [REMOVE IF UNUSED] Option 2: New microservice (requires strong justification)
athena-boot-[new-domain]/          # New domain microservice
├── pom.xml                        # Inherits from athena-boot-parent
├── src/main/java/org/catools/athena/[new-domain]/
│   ├── [New-Domain]Application.java  # Spring Boot main class
│   └── [standard package structure as Option 1]
└── src/main/resources/
    └── db/migration/
        └── V1__initial_schema.sql # Initial Flyway migration

athena-boot-[new-domain]-feign/    # Feign client module
└── pom.xml                        # Inherits from athena-parent

# [REMOVE IF UNUSED] Option 3: Shared library enhancement
athena-common/                     # Cross-cutting concerns
└── src/main/java/org/catools/athena/common/
    ├── utils/                     # Utility classes (@UtilityClass)
    ├── exception/                 # Custom exception hierarchy
    └── config/                    # Shared configuration

athena-common-test/                # Shared test utilities
└── src/main/java/org/catools/athena/common/test/
    └── [test utilities]

# [REMOVE IF UNUSED] Option 4: Gateway changes
athena-gateway/                    # API Gateway routing
└── src/main/resources/
    └── application.yml            # Gateway routes configuration
```

**Structure Decision**: [Document which module(s) will be modified and why. If creating
a new microservice, justify the new domain boundary. If modifying multiple modules,
explain the dependency flow.]

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation                  | Why Needed         | Simpler Alternative Rejected Because |
|----------------------------|--------------------|--------------------------------------|
| [e.g., 4th project]        | [current need]     | [why 3 projects insufficient]        |
| [e.g., Repository pattern] | [specific problem] | [why direct DB access insufficient]  |
