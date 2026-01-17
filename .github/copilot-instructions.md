# Athena - GitHub Copilot Instructions

## Project Overview

Athena is a sophisticated software solution that systematically collects quality metrics throughout the Software
Development Life Cycle (SDLC). The system aims to proactively identify and address quality-related issues early in the
development process, thereby reducing costs, improving efficiency, and enhancing overall product quality.

## Technology Stack

- **Java Version**: JDK 21
- **Build Tool**: Maven 3.8.6+ (using Maven Wrapper `./mvnw`)
- **Framework**: Spring Boot with Spring Data JPA, Spring Data REST, Spring Cloud OpenFeign
- **Database**: PostgreSQL (with Flyway migrations)
- **Testing**: JUnit 5, TestContainers, Locust (performance testing)
- **API Documentation**: SpringDoc OpenAPI (Swagger)
- **Code Quality**: SonarCloud integration with JaCoCo for coverage

## Architecture

Athena follows a modular microservices architecture with the following module types:

- **athena-boot-*** - Spring Boot microservices for different domains (core, git, kube, pipeline, tms, spec, metric)
- **athena-boot-*-feign** - Feign client modules for each microservice
- **athena-common** - Shared utilities and common code
- **athena-common-test** - Shared test utilities
- **athena-gateway** - API Gateway
- **athena-parent/athena-boot-parent** - Parent POMs with dependency management

### Data Sources

Athena collects metrics from:

- CI/CD pipelines
- Git repositories
- Kubernetes infrastructure
- Task management systems (Jira/Zephyr)
- OpenAPI (Swagger) documentation

## Build and Test Guidelines

### Building Locally

Requirements: Maven 3.8.6+, JDK-21, Docker

```shell
# Compile
./mvnw clean compile -U

# Install without tests
./mvnw install -DskipTests

# Build Docker images
./mvnw package docker:build -DskipTests
```

### Testing Strategy

The project uses a sophisticated testing approach with TestContainers:

1. **Unit Tests**: Standard JUnit tests with Spring Boot Test
2. **Integration Tests**: Use TestContainers to spin up PostgreSQL databases
3. **Performance Tests**: Locust scenarios executed after functional tests

Test execution flow:

1. TestContainer starts golden database
2. Spring Boot app starts with database
3. Execute functional tests (JUnit)
4. Restart with new golden database
5. Execute performance tests (Locust)
6. Clean up containers

### Running Tests

```shell
# Run tests
./mvnw test

# Run integration tests
./mvnw verify
```

## Coding Standards

### General Practices

- Use Lombok annotations (`@UtilityClass`, `@Data`, etc.) to reduce boilerplate
- Follow Spring Boot best practices and conventions
- Use `@RestController` for REST endpoints
- Use Spring Data JPA repositories with proper naming conventions
- Implement proper exception handling with custom exceptions in `athena-common`

### Package Structure

- `entity/` - JPA entities
- `model/` - DTOs and data models
- `mapper/` - MapStruct mappers (excluded from Sonar)
- `repository/` - Spring Data repositories
- `service/` - Business logic
- `controller/` or `rest/` - REST controllers
- `config/` - Spring configuration classes (excluded from Sonar)
- `exception/` - Custom exceptions (excluded from Sonar)

### Code Quality

- **SonarCloud** integration is enabled for code quality analysis
- **JaCoCo** is used for test coverage reporting
- Excluded from coverage: Application classes, config classes, models, entities, mappers, exceptions, test code

### Database

- Use **Flyway** for database migrations
- Default database: PostgreSQL
- Connection details configured via Spring Boot properties
- TestContainers used for integration tests

## API Design

- Use **SpringDoc OpenAPI** for API documentation
- Follow REST conventions (proper HTTP methods, status codes)
- Use Spring Data REST where appropriate for CRUD operations
- Implement validation using `@Valid` and constraint annotations

## Docker

- Docker images are built using the `docker-maven-plugin`
- Each microservice has its own Docker configuration
- Images can be built with: `./mvnw package docker:build -DskipTests`

## Dependencies

When adding or modifying dependencies:

- Update the appropriate parent POM (`athena-parent` or `athena-boot-parent`)
- Ensure version management is centralized in parent POMs
- Use Spring Boot's dependency management where possible
- Feign clients should be in separate modules (`*-feign`)

## Testing with TestContainers

- Tests use TestContainers to provide real PostgreSQL instances
- Golden database images are pulled from Docker Registry
- Database containers are automatically started and stopped during tests
- Ensure Docker is running when executing integration tests

## Module Dependencies

- Core modules should not depend on Feign client modules
- Feign client modules depend on their corresponding core modules
- Common functionality goes in `athena-common`
- Test utilities go in `athena-common-test`

## Performance Testing

- Use Locust for performance scenarios
- Performance tests run as part of integration test phase
- Located in `src/it/java` directories
- Follow the population pattern for test data generation

## Important Notes

- All Spring Boot applications should extend base configuration from `athena-boot-parent`
- Use the Maven Wrapper (`./mvnw`) for consistent builds
- Docker must be available for building images and running integration tests
- Some modules skip tests/Sonar/Docker by default (configured in parent POM)
