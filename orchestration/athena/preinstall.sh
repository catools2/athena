#!/usr/bin/env bash

cd ../../
./mvnw clean package docker:build -DskipTests

cd orchestration/athena
