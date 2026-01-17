FROM eclipse-temurin:21-jdk-alpine AS builder

ENV application="/opt/athena"
ENV project_build_finalName="${project.build.finalName}.jar"

WORKDIR $application
COPY "maven/${project_build_finalName}" ./
RUN java -Djarmode=layertools -jar "${project_build_finalName}" extract

FROM eclipse-temurin:21-jre-alpine

ENV ACTIVE_PROFILE="prod" \
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

EXPOSE 8080 9090

ENTRYPOINT ["/bin/sh", "-c", "java \
 -Dspring.profiles.active=$ACTIVE_PROFILE \
 org.springframework.boot.loader.launch.JarLauncher"]
