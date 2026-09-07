#!/usr/bin/env bash

set -euo pipefail

if [[ $# -ne 1 ]]; then
  echo "Usage: $0 <agent-name>" >&2
  exit 1
fi

agent_name="$1"
script_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
repo_root="$(cd "$script_dir/../../.." && pwd)"
target_file="$repo_root/.github/agents/${agent_name}-instructions.md"

if [[ ! -f "$target_file" ]]; then
  echo "Target agent instructions not found: $target_file" >&2
  exit 1
fi

start_marker="<!-- MANUAL ADDITIONS START -->"
end_marker="<!-- MANUAL ADDITIONS END -->"

if ! grep -q "$start_marker" "$target_file" || ! grep -q "$end_marker" "$target_file"; then
  echo "Target file is missing manual addition markers" >&2
  exit 1
fi

feature_summary_file="$(mktemp)"
trap 'rm -f "$feature_summary_file"' EXIT

{
  echo "## Active Specify Features"
  echo
  if find "$repo_root/.specify/specs" -mindepth 1 -maxdepth 1 -type d | sort | grep -q .; then
    find "$repo_root/.specify/specs" -mindepth 1 -maxdepth 1 -type d | sort | while read -r feature_dir; do
      feature_slug="$(basename "$feature_dir")"
      echo "- $feature_slug"
    done
  else
    echo "- No feature packages have been created yet"
  fi
} > "$feature_summary_file"

python3 - <<'PY' "$target_file" "$feature_summary_file" "$start_marker" "$end_marker"
from pathlib import Path
import sys

target = Path(sys.argv[1])
summary = Path(sys.argv[2]).read_text()
start = sys.argv[3]
end = sys.argv[4]
content = target.read_text()
prefix, remainder = content.split(start, 1)
_, suffix = remainder.split(end, 1)
new_content = prefix + start + "\n" + summary.rstrip() + "\n\n" + end + suffix
target.write_text(new_content)
PY

echo "Updated agent context in .github/agents/${agent_name}-instructions.md"
