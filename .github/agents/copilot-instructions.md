# Athena Development Guidelines (Specify Integration)

Auto-generated from feature plans. Last updated: December 22, 2024

## Active Technologies

- **Language**: Java 21
- **Build Tool**: Maven 3.8.6+ (Maven Wrapper)
- **Framework**: Spring Boot 3.4.1
- **Database**: PostgreSQL with Flyway 10.21.0
- **Testing**: JUnit 5, TestContainers, Locust
- **API**: SpringDoc OpenAPI 2.7.0
- **Code Quality**: JaCoCo 0.8.12, SonarCloud

## Project Structure

```text
athena/
├── athena-parent/              # Parent POM with dependency management
├── athena-boot-parent/         # Spring Boot parent configuration
├── athena-common/              # Shared utilities and common code
├── athena-common-test/         # Shared test utilities
├── athena-boot-core/           # Core domain microservice
├── athena-boot-core-feign/     # Core Feign clients
├── athena-boot-git/            # Git metrics microservice
├── athena-boot-git-feign/      # Git Feign clients
├── athena-boot-kube/           # Kubernetes metrics microservice
├── athena-boot-kube-feign/     # Kube Feign clients
├── athena-boot-pipeline/       # Pipeline metrics microservice
├── athena-boot-pipeline-feign/ # Pipeline Feign clients
├── athena-boot-tms/            # TMS metrics microservice
├── athena-boot-tms-feign/      # TMS Feign clients
├── athena-boot-spec/           # Spec metrics microservice
├── athena-boot-spec-feign/     # Spec Feign clients
├── athena-boot-metric/         # Metric aggregation microservice
├── athena-boot-metric-feign/   # Metric Feign clients
└── athena-gateway/             # API Gateway
```

## Commands

### Build Commands

```bash
# Compile all modules
./mvnw clean compile -U

# Install without tests
./mvnw install -DskipTests

# Full build with tests
./mvnw clean verify

# Build Docker images
./mvnw package docker:build -DskipTests
```

### Test Commands

```bash
# Run unit tests only
./mvnw test

# Run integration tests
./mvnw verify

# Run tests for specific module
./mvnw test -pl athena-boot-core
```

### Run Commands

```bash
# Run specific microservice (example: core)
cd athena-boot-core
../mvnw spring-boot:run

# Run with specific profile
../mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## Code Style

### Java Conventions

- Use **Lombok** annotations (`@Data`, `@Builder`, `@Slf4j`) to reduce boilerplate
- Follow **Spring Boot** best practices and conventions
- Use **MapStruct** for entity-to-DTO mappings
- Implement proper exception handling in `athena-common`

### Package Structure (per module)

```text
src/main/java/org/catools/athena/{module}/
├── entity/        # JPA entities
├── model/         # DTOs and data models
├── mapper/        # MapStruct mappers
├── repository/    # Spring Data repositories
├── service/       # Business logic
├── controller/    # REST controllers
├── config/        # Spring configuration
└── exception/     # Custom exceptions
```

### Testing Standards

- Unit tests use **JUnit 5** with Spring Boot Test
- Integration tests use **TestContainers** for PostgreSQL
- Performance tests use **Locust** scenarios
- Test coverage reported via **JaCoCo** to **SonarCloud**

### Database Migrations

- Use **Flyway** for all schema changes
- Migration files: `src/main/resources/db/migration/V{version}__{description}.sql`
- Follow PostgreSQL syntax and best practices

## Recent Changes

[This section will be updated by Specify as new features are added]

<!-- MANUAL ADDITIONS START -->
<!-- Add your custom instructions here - they will be preserved during updates -->

<!-- MANUAL ADDITIONS END -->

