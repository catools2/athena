#!/usr/bin/env bash

cd ../../
./mvnw org.codehaus.mojo:versions-maven-plugin:2.15.0:set-property -Dproperty=revision -DnewVersion=2.0.0 -q
./mvnw clean package docker:build -DskipTests -T 2C

cd orchestration/athena

helm dependency update