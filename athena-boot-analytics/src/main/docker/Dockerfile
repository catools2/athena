ARG HARBOR_REGISTRY=harbor-ny2n.saas-n.com
ARG TAG=2025-11-27

FROM ${HARBOR_REGISTRY}/pbs-pmx/base-images/spring-boot-jdk21:${TAG} AS builder

ENV application="/opt/athena"
ENV project_build_finalName="${project.build.finalName}.jar"

WORKDIR $application
COPY "maven/${project_build_finalName}" ./
RUN java -Djarmode=layertools -jar "${project_build_finalName}" extract

FROM ${HARBOR_REGISTRY}/pbs-pmx/base-images/spring-boot-jdk21:${TAG}

ENV ATHENA_DB_HOSTNAME="localhost" \
    DB_POOL_SIZE="30" \
    ATHENA_DB_PORT="5432" \
    ATHENA_DB_DATABASE="athena" \
    ATHENA_DB_USERNAME="postgres" \
    ATHENA_DB_PASSWORD="password" \
    FLYWAY_ENABLED="true" \
    ACTIVE_PROFILE="prod" \
    CORE_CLIENT_HOST="athena-core" \
    CORE_CLIENT_PORT="8081" \
    JVM_ARGS="-Xmx2g -Xms512m"\
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

EXPOSE 8088 9090

ENTRYPOINT ["/bin/sh", "-c", "exec java $JVM_ARGS -Dspring.profiles.active=$ACTIVE_PROFILE org.springframework.boot.loader.launch.JarLauncher"]
