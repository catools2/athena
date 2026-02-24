#!/usr/bin/env bash

# Build and Install
helm dependency build

helm upgrade --install athena . --timeout 10m