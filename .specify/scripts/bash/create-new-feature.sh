#!/usr/bin/env bash

set -euo pipefail

if [[ $# -ne 1 ]]; then
  echo "Usage: $0 <feature-name>" >&2
  exit 1
fi

feature_name="$1"
feature_slug="$(printf '%s' "$feature_name" | tr '[:upper:]' '[:lower:]' | sed -E 's/[^a-z0-9]+/-/g; s/^-+|-+$//g')"

if [[ -z "$feature_slug" ]]; then
  echo "Feature name must contain at least one letter or number" >&2
  exit 1
fi

script_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
repo_root="$(cd "$script_dir/../../.." && pwd)"
feature_dir="$repo_root/.specify/specs/$feature_slug"
templates_dir="$repo_root/.specify/templates"

if [[ -e "$feature_dir" ]]; then
  echo "Feature already exists: .specify/specs/$feature_slug" >&2
  exit 1
fi

mkdir -p "$feature_dir"

for template in spec.md plan.md research.md data-model.md; do
  source_file="$templates_dir/$template"
  target_file="$feature_dir/$template"

  if [[ ! -f "$source_file" ]]; then
    echo "Missing template: $source_file" >&2
    exit 1
  fi

  sed "s/{{FEATURE_NAME}}/$feature_name/g; s/{{FEATURE_SLUG}}/$feature_slug/g" "$source_file" > "$target_file"
done

echo "Created feature scaffold at .specify/specs/$feature_slug"
