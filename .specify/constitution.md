# Athena Project Constitution

## Project Identity

**Name**: Athena  
**Purpose**: A sophisticated software solution that systematically collects quality metrics throughout the Software
Development Life Cycle (SDLC) to proactively identify and address quality-related issues early in the development
process.

**Mission**: Reduce costs, improve efficiency, and enhance overall product quality by providing comprehensive quality
metrics collection and analysis across the entire SDLC.

## Core Principles

### 1. Architectural Principles

#### Microservices Architecture

- Follow a modular microservices architecture with clear domain boundaries
- Each microservice focuses on a specific domain (core, git, kube, pipeline, tms, spec, metric)
- Services communicate via REST APIs and Feign clients
- Gateway pattern for API routing and centralization

#### Separation of Concerns

- **athena-boot-*** - Spring Boot microservices for domain logic
- **athena-boot-*-feign** - Feign client modules (separate from core)
- **athena-common** - Shared utilities and common code
- **athena-common-test** - Shared test utilities
- **athena-gateway** - API Gateway for routing
- **athena-boot-parent/athena-parent** - Parent POMs with dependency management

#### Module Dependencies

- Core modules MUST NOT depend on Feign client modules
- Feign client modules SHOULD depend on their corresponding core modules
- Common functionality MUST go in `athena-common`
- Test utilities MUST go in `athena-common-test`

### 2. Technology Standards

#### Required Versions

- **Java**: JDK 21 (mandatory)
- **Maven**: 3.8.6+ using Maven Wrapper (`./mvnw`)
- **Spring Boot**: Latest stable with Spring Data JPA, Spring Data REST, Spring Cloud OpenFeign
- **Database**: PostgreSQL with Flyway migrations
- **Container Runtime**: Docker (required for builds and integration tests)

#### Testing Framework

- **Unit Tests**: JUnit 5
- **Integration Tests**: TestContainers with PostgreSQL
- **Coverage**: JaCoCo with SonarCloud integration

### 3. Code Quality Principles

#### General Practices

- Use Lombok annotations (`@UtilityClass`, `@Data`, etc.) to reduce boilerplate
- Follow Spring Boot best practices and conventions
- Use `@RestController` for REST endpoints
- Use Spring Data JPA repositories with proper naming conventions
- Implement proper exception handling with custom exceptions in `athena-common`

#### Package Structure (Mandatory)

```
src/main/java/org/catools/athena/{module}/
├── entity/          # JPA entities
├── model/           # DTOs and data models
├── mapper/          # MapStruct mappers (excluded from Sonar)
├── repository/      # Spring Data repositories
├── service/         # Business logic
├── controller/      # REST controllers (or rest/)
├── config/          # Spring configuration (excluded from Sonar)
└── exception/       # Custom exceptions (excluded from Sonar)
```

#### Code Quality Tools

- **SonarCloud**: Mandatory integration for code quality analysis
- **JaCoCo**: Required for test coverage reporting
- **Coverage Exclusions**: Application classes, config classes, models, entities, mappers, exceptions, test code

### 4. Database Principles

#### Migration Strategy

- **Flyway**: Mandatory for all database migrations
- Version-controlled SQL scripts in `src/main/resources/db/migration`
- No manual database changes in production
- All schema changes MUST go through Flyway migrations

#### Data Layer

- Use Spring Data JPA for data access
- Repository interfaces extend appropriate Spring Data interfaces
- Use proper JPA annotations on entities
- Ensure proper cascade and fetch strategies

### 5. API Design Principles

#### REST Conventions

- Use **SpringDoc OpenAPI** for API documentation (mandatory)
- Follow REST conventions (proper HTTP methods, status codes)
- Use Spring Data REST where appropriate for CRUD operations
- Implement validation using `@Valid` and constraint annotations
- Return appropriate DTOs, not entities

#### API Versioning

- Consider versioning strategy for public APIs
- Document breaking changes clearly

### 6. Testing Principles

#### Test Pyramid

1. **Unit Tests**: Test business logic in isolation
2. **Integration Tests**: Test with real database via TestContainers
3. **Performance Tests**: Locust scenarios for critical paths

#### TestContainers Strategy

- Tests use TestContainers to provide real PostgreSQL instances
- Golden database images pulled from Docker Registry
- Database containers automatically started/stopped during tests
- Docker MUST be running for integration tests

#### Test Execution Flow

1. TestContainer starts golden database
2. Spring Boot app starts with database
3. Execute functional tests (JUnit)
4. Restart with new golden database
5. Execute performance tests (Locust)
6. Clean up containers

### 7. Build and Deployment Principles

#### Build Commands

```shell
# Compile
./mvnw clean compile -U

# Install without tests
./mvnw install -DskipTests

# Build Docker images
./mvnw package docker:build -DskipTests

# Run tests
./mvnw test

# Run integration tests
./mvnw verify
```

#### Docker Strategy

- Docker images built using `docker-maven-plugin`
- Each microservice has its own Docker configuration
- Images follow consistent naming conventions
- Multi-stage builds for efficiency

#### Continuous Integration

- All tests MUST pass before merge
- SonarCloud quality gate MUST pass
- Docker images built automatically in CI/CD

### 8. Dependency Management Principles

#### Centralized Management

- Update appropriate parent POM (`athena-parent` or `athena-boot-parent`)
- Version management centralized in parent POMs
- Use Spring Boot's dependency management where possible
- Feign clients MUST be in separate modules (`*-feign`)

#### Version Control

- Use Renovate for dependency updates
- Review and test dependency updates before merging
- Document breaking changes from dependency updates

### 9. Data Source Integration Principles

Athena collects metrics from multiple sources:

- **CI/CD Pipelines**: Pipeline execution metrics
- **Git Repositories**: Code quality and activity metrics
- **Kubernetes**: Infrastructure and deployment metrics
- **Task Management**: Jira/Zephyr integration for test metrics
- **OpenAPI**: API specification and documentation metrics

Each integration MUST:

- Have its own microservice module
- Include proper error handling
- Support retry mechanisms
- Log integration activities
- Provide health check endpoints

### 10. Security Principles

- Never commit sensitive data (credentials, tokens) to version control
- Use environment variables or Spring Cloud Config for secrets
- Implement proper authentication/authorization where needed
- Keep dependencies up to date for security patches

## Non-Negotiables

1. **All code MUST use JDK 21** - no exceptions
2. **All database changes MUST use Flyway migrations** - no manual changes
3. **All tests MUST pass before merge** - no skipping
4. **All new code MUST have test coverage** - enforce quality gates
5. **Maven Wrapper (`./mvnw`) MUST be used** - ensure build consistency
6. **Docker MUST be available** - required for builds and tests
7. **Spring Boot applications MUST extend athena-boot-parent** - ensure consistency
8. **Package structure MUST follow conventions** - maintain organization

## Decision Making Framework

When making architectural or design decisions, consider:

1. **Does it maintain separation of concerns?**
2. **Does it follow microservices best practices?**
3. **Can it be tested effectively with our test strategy?**
4. **Does it integrate with our quality metrics collection?**
5. **Is it consistent with existing patterns in the codebase?**
6. **Does it maintain backwards compatibility?**

## Evolution of This Constitution

This constitution is a living document. Changes should be:

- Proposed via pull request
- Discussed with the team
- Documented with rationale
- Applied consistently across all modules

Last Updated: December 3, 2025

