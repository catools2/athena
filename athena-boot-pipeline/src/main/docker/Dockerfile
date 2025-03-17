FROM openjdk:21-jdk-slim AS builder

ENV application="/opt/athena"
ENV project_build_finalName="${project.build.finalName}.jar"

WORKDIR $application
COPY "maven/${project_build_finalName}" ./
RUN java -Djarmode=layertools -jar "${project_build_finalName}" extract

FROM openjdk:21-jdk-slim

ENV ATHENA_DB_HOSTNAME="localhost" \
    ATHENA_DB_PORT="5432" \
    ATHENA_DB_DATABASE="athena" \
    DB_PSQL_USERNAME="postgres" \
    DB_PSQL_PASSWORD="password" \
    FLYWAY_ENABLED="true" \
    ACTIVE_PROFILE="prod" \
    FEIGN_CLIENT_CORE_URL="http://athena-core:8081" \
    application="/opt/athena"

WORKDIR $application
COPY --from=builder "${application}/dependencies/" ./
RUN true
COPY --from=builder "${application}/spring-boot-loader/" ./
RUN true
COPY --from=builder "${application}/snapshot-dependencies/" ./
RUN true
COPY --from=builder "${application}/application/" ./
RUN true

EXPOSE 8085 9090

RUN apt update && apt upgrade -qy && apt install -qy curl && apt -qy autoremove && apt -qy clean

ENTRYPOINT ["java", \
"-Dhibernate.dialect=org.hibernate.dialect.PostgreSQLDialect", \
"-Dspring.datasource.url=jdbc:postgresql://${ATHENA_DB_HOSTNAME}:${ATHENA_DB_PORT}/${ATHENA_DB_DATABASE}", \
"-Dspring.datasource.username=${DB_PSQL_USERNAME}", \
"-Dspring.datasource.password=${DB_PSQL_PASSWORD}", \
"-Dspring.profiles.active=${ACTIVE_PROFILE}", \
"-Dspring.flyway.enabled=${FLYWAY_ENABLED}", \
"-Dfeign.clients.athena.core.url=${FEIGN_CLIENT_CORE_URL}", \
"org.springframework.boot.loader.launch.JarLauncher"]