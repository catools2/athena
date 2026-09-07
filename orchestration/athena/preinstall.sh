#!/usr/bin/env bash

cd ../../
./mvnw org.codehaus.mojo:versions-maven-plugin:2.15.0:set-property -Dproperty=revision -DnewVersion=2.0.0 -q
./mvnw clean package -DskipTests -T 2C
./mvnw -pl athena-boot-core,athena-boot-git,athena-boot-kube,athena-boot-metric,athena-boot-pipeline,athena-boot-spec,athena-boot-tms,athena-gateway docker:build -DskipTests -T 2C

cd orchestration/athena

helm dependency update