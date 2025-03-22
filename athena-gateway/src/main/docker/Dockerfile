FROM openjdk:21-jdk-slim AS builder

ENV application="/opt/athena"
ENV project_build_finalName="${project.build.finalName}.jar"

WORKDIR $application
COPY "maven/${project_build_finalName}" ./
RUN java -Djarmode=layertools -jar "${project_build_finalName}" extract

FROM openjdk:21-jdk-slim

ENV ACTIVE_PROFILE="prod" \
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

RUN apt update && apt upgrade -qy && \
    apt install -qy curl \
    && apt -qy autoremove && apt -qy clean

ENTRYPOINT ["java", \
"-Dspring.profiles.active=${ACTIVE_PROFILE}", \
"org.springframework.boot.loader.launch.JarLauncher"]