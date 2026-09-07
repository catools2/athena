#!/usr/bin/env bash
# Apply the athena analytics views in dependency order.
#
#   ./apply_views.sh                       # all 41 objects, in order
#   ./apply_views.sh 27                    # only 27__mvw_items.sql
#   ./apply_views.sh 27 41                 # range 27..41 inclusive
#
# Connection uses standard libpq variables. Existing PG* values take
# precedence, followed by ATHENA_DB_* values and the project defaults.
#
# Each file DROPs its object with CASCADE before recreating it, so applying a
# low-numbered file on its own will drop everything downstream of it. When in
# doubt, apply from that file to the end.
set -euo pipefail

cd "$(dirname "$0")"
export PGHOST="${PGHOST:-${ATHENA_DB_HOSTNAME:-localhost}}"
export PGPORT="${PGPORT:-${ATHENA_DB_PORT:-5432}}"
export PGDATABASE="${PGDATABASE:-${ATHENA_DB_DATABASE:-athena}}"
export PGUSER="${PGUSER:-${ATHENA_DB_USERNAME:-postgres}}"
export PGPASSWORD="${PGPASSWORD:-${ATHENA_DB_PASSWORD:-password}}"

from=${1:-0}
to=${2:-99}

shopt -s nullglob
for f in views/*.sql; do
  n=$(basename "$f" | cut -d_ -f1)
  n=$((10#$n))
  (( n >= from && n <= to )) || continue
  echo ">> $f"
  psql --set ON_ERROR_STOP=1 --quiet --file "$f"
done
echo "done"
