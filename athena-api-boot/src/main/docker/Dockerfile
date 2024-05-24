FROM openjdk:17-jdk-slim as builder

ENV application /opt/athena

WORKDIR ${application}
COPY "maven/${project.build.finalName}.jar" ./
RUN java -Djarmode=layertools -jar "${project.build.finalName}.jar" extract

FROM openjdk:17-jdk-slim

ENV DB_PSQL_URL="jdbc:postgresql://localhost:5432/postgres?TC_INITSCRIPT=psql/create-schema.sql" \
    DB_PSQL_USERNAME="postgres" \
    DB_PSQL_PASSWORD="password"

WORKDIR ${application}
COPY DOCKER_PACKAGING_DIR "${application}"
COPY --from=builder "${DOCKER_PACKAGING_DIR}/dependencies/" ./
RUN true
COPY --from=builder "${DOCKER_PACKAGING_DIR}/spring-boot-loader/" ./
RUN true
COPY --from=builder "${DOCKER_PACKAGING_DIR}/snapshot-dependencies/" ./
RUN true
COPY --from=builder "${DOCKER_PACKAGING_DIR}/application/" ./
RUN true

EXPOSE 8080 9090

ENTRYPOINT ["java", \
"-Dspring.datasource.url=${DB_PSQL_URL}", \
"-Dspring.datasource.username=${DB_PSQL_USERNAME}", \
"-Dspring.datasource.password=${DB_PSQL_PASSWORD}", \
"org.springframework.boot.loader.launch.JarLauncher"]