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
    ATHENA_DB_USERNAME="postgres" \
    ATHENA_DB_PASSWORD="password" \
    FLYWAY_ENABLED="true" \
    ACTIVE_PROFILE="prod" \
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

EXPOSE 8081 9090

RUN apt update && apt upgrade -qy && \
    apt install -qy curl \
    && apt -qy autoremove && apt -qy clean

ENTRYPOINT ["java", \
"-Dhibernate.dialect=org.hibernate.dialect.PostgreSQLDialect", \
"-Dspring.datasource.username=${ATHENA_DB_USERNAME}", \
"-Dspring.datasource.password=${ATHENA_DB_PASSWORD}", \
"-Dspring.profiles.active=${ACTIVE_PROFILE}", \
"-Dspring.flyway.enabled=${FLYWAY_ENABLED}", \
"org.springframework.boot.loader.launch.JarLauncher"]
