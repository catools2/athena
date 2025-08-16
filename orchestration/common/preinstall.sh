#!/usr/bin/env bash

# Add Helm Repository
helm repo add kedacore https://kedacore.github.io/charts
helm repo add runix https://helm.runix.net

# Add local-path-provisioner
kubectl apply -f https://raw.githubusercontent.com/rancher/local-path-provisioner/master/deploy/local-path-storage.yaml

# Install CRDs
helm upgrade --install athena-common kedacore/keda
