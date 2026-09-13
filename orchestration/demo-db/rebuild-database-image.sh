#!/usr/bin/env bash
# rebuild-database-image.sh
#
# Builds and optionally pushes the athena-pg-demo seeded database image.
# This image is used by integration tests (docker/core-compose.yml).
#
# Usage:
#   ./rebuild-database-image.sh            # build only (tag: 0.3)
#   ./rebuild-database-image.sh --push     # build and push to Docker Hub
#   ./rebuild-database-image.sh --tag 0.4  # use a custom tag
#
# Prerequisites: Docker must be running.

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
IMAGE_NAME="akeshmiri/athena-pg-demo"
TAG="0.3"
PUSH=false

while [[ $# -gt 0 ]]; do
  case "$1" in
    --push) PUSH=true; shift ;;
    --tag)  TAG="$2"; shift 2 ;;
    *) echo "Unknown option: $1"; exit 1 ;;
  esac
done

FULL_IMAGE="${IMAGE_NAME}:${TAG}"

echo "==> Building seeded database image: ${FULL_IMAGE}"
docker build -t "${FULL_IMAGE}" "${SCRIPT_DIR}"

if [[ "${PUSH}" == "true" ]]; then
  echo "==> Pushing ${FULL_IMAGE} to Docker Hub..."
  docker push "${FULL_IMAGE}"
  echo "==> Done. Remember to update docker/core-compose.yml to use tag ${TAG}."
else
  echo "==> Build complete. Run with --push to publish."
  echo "    Update docker/core-compose.yml: image: ${FULL_IMAGE}"
fi
