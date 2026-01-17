FROM eclipse-temurin:21-jdk-alpine AS builder

ENV application="/opt/athena"
ENV project_build_finalName="${project.build.finalName}.jar"

WORKDIR $application
COPY "maven/${project_build_finalName}" ./
RUN java -Djarmode=layertools -jar "${project_build_finalName}" extract

FROM eclipse-temurin:21-jre-alpine

ENV ATHENA_DB_HOSTNAME="localhost" \
    DB_POOL_SIZE="10" \
    ATHENA_DB_PORT="5432" \
    ATHENA_DB_DATABASE="athena" \
    ATHENA_DB_USERNAME="postgres" \
    ATHENA_DB_PASSWORD="password" \
    FLYWAY_ENABLED="true" \
    ACTIVE_PROFILE="prod" \
    CORE_CLIENT_HOST="athena-core" \
    CORE_CLIENT_PORT="8081" \
    JVM_ARGS="-Xmx1g -Xms512m"\
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

EXPOSE 8083 9090

ENTRYPOINT ["/bin/sh", "-c", "exec java $JVM_ARGS -Dspring.profiles.active=$ACTIVE_PROFILE org.springframework.boot.loader.launch.JarLauncher"]
