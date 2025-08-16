#!/usr/bin/env bash

# Build and Install
helm dependency build

helm upgrade --install athena-common .