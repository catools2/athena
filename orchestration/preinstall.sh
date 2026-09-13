#!/usr/bin/env bash

set -Eeuo pipefail

SCRIPT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd -- "$SCRIPT_DIR/../.." && pwd)"

on_error() {
	printf 'preinstall failed at line %s: %s\n' "$1" "$2" >&2
}

trap 'on_error "$LINENO" "$BASH_COMMAND"' ERR

verify_jars() {
	local jar_file jar_file_abs jar_name module_dir module_dir_abs expected_classes
	local packaged_classes class_file class_name relative_class class_count
	local jar_index=0 failures=0 jar_failures entries duplicates validation_output
	local validation_target
	local check_dir="$(mktemp -d "${TMPDIR:-/tmp}/athena-jar-check.XXXXXX")"

	trap 'rm -rf -- "$check_dir"; trap - RETURN' RETURN

	printf 'Validating generated JARs...\n'
	while IFS= read -r -d '' jar_file; do
		jar_index=$((jar_index + 1))
		jar_failures=$failures
		jar_name="$(basename "$jar_file")"
		jar_file_abs="$(cd -- "$(dirname -- "$jar_file")" && pwd)/$jar_name"
		module_dir="${jar_file%%/target/*}"
		module_dir_abs="$(cd -- "$module_dir" && pwd)"
		expected_classes="$module_dir_abs/target/classes"

		if [[ "$jar_name" == *-tests.jar || "$jar_name" == *-tests-*.jar ]]; then
			expected_classes="$module_dir_abs/target/test-classes"
		fi

		local jar_check_dir="$check_dir/jar-$jar_index"
		mkdir -p "$jar_check_dir"

		if ! unzip -tqq "$jar_file_abs" >/dev/null 2>&1; then
			printf '  FAIL %s: ZIP container is corrupt\n' "$jar_file" >&2
			failures=$((failures + 1))
			continue
		fi
		if ! entries="$(jar tf "$jar_file_abs")"; then
			printf '  FAIL %s: cannot list JAR entries\n' "$jar_file" >&2
			failures=$((failures + 1))
			continue
		fi
		duplicates="$(printf '%s\n' "$entries" | sort | uniq -d)"
		if [[ -n "$duplicates" ]]; then
			printf '  FAIL %s: duplicate JAR entries:\n%s\n' "$jar_file" "$duplicates" >&2
			failures=$((failures + 1))
			continue
		fi
		if ! (cd -- "$jar_check_dir" && jar xf "$jar_file_abs"); then
			printf '  FAIL %s: cannot extract JAR\n' "$jar_file" >&2
			failures=$((failures + 1))
			continue
		fi

		if [[ -d "$jar_check_dir/BOOT-INF/classes" ]]; then
			packaged_classes="$jar_check_dir/BOOT-INF/classes"
		else
			packaged_classes="$jar_check_dir"
		fi

		validation_target="$jar_file_abs"
		if [[ "$packaged_classes" == "$jar_check_dir/BOOT-INF/classes" ]]; then
			validation_target="$jar_check_dir/classes-validation.jar"
			if ! jar --create --file "$validation_target" -C "$packaged_classes" .; then
				printf '  FAIL %s: cannot create flat class validation archive\n' "$jar_file" >&2
				failures=$((failures + 1))
				continue
			fi
		fi
		if ! validation_output="$(jar --validate --file "$validation_target" 2>&1)"; then
			printf '  FAIL %s: JAR validation failed\n%s\n' "$jar_file" "$validation_output" >&2
			failures=$((failures + 1))
			continue
		fi

		if [[ ! -d "$expected_classes" ]]; then
			case "$jar_name" in
				*-sources.jar|*-javadoc.jar)
					printf '  OK   %s (archive-only artifact)\n' "$jar_file"
					continue
					;;
				*)
					printf '  FAIL %s: expected class directory does not exist: %s\n' \
						"$jar_file" "$expected_classes" >&2
					failures=$((failures + 1))
					continue
					;;
			esac
		fi

		class_count=0
		while IFS= read -r class_file; do
			relative_class="${class_file#"$expected_classes"/}"
			class_count=$((class_count + 1))
			if [[ ! -f "$packaged_classes/$relative_class" ]]; then
				printf '  FAIL %s: missing packaged class %s\n' "$jar_file" "$relative_class" >&2
				failures=$((failures + 1))
			fi
		done < <(find "$expected_classes" -type f -name '*.class' -print)

		while IFS= read -r class_file; do
			if [[ "$class_file" == "$packaged_classes"/* ]]; then
				relative_class="${class_file#"$packaged_classes"/}"
			else
				relative_class="${class_file#"$jar_check_dir"/}"
			fi
			class_name="${relative_class%.class}"
			class_name="${class_name//\//.}"
			case "$class_name" in
				META-INF.*|BOOT-INF.lib.*|module-info|*\.module-info|package-info|*\.package-info)
					continue
					;;
			esac
			if ! javap -v -classpath "$packaged_classes:$jar_check_dir" "$class_name" \
					>"$jar_check_dir/javap.out" 2>&1; then
				printf '  FAIL %s: javap cannot parse %s\n%s\n' "$jar_file" "$class_name" \
					"$(cat "$jar_check_dir/javap.out")" >&2
				failures=$((failures + 1))
			  elif grep -Eq 'Unresolved compilation problem|Unresolved compilation problems|ClassFormatError' \
					"$jar_check_dir/javap.out"; then
				printf '  FAIL %s: compiler-corrupted bytecode in %s\n' "$jar_file" "$class_name" >&2
				failures=$((failures + 1))
			fi
		done < <(find "$jar_check_dir" -type f -name '*.class' -print)

		if (( failures == jar_failures )); then
			printf '  OK   %s (%s classes checked)\n' "$jar_file" "$class_count"
		else
			printf '  FAIL %s: one or more class checks failed\n' "$jar_file" >&2
		fi
	done < <(find "$REPO_ROOT" -type f -path '*/target/*.jar' -print0)

	if (( jar_index == 0 )); then
		printf 'No generated JARs found under %s\n' "$REPO_ROOT" >&2
		return 1
	fi
	if (( failures > 0 )); then
		printf '%s generated JAR validation check(s) failed\n' "$failures" >&2
		return 1
	fi
	printf 'All %s generated JAR(s) passed integrity and class checks.\n' "$jar_index"
}

cd -- "$REPO_ROOT"
./mvnw clean
./mvnw org.codehaus.mojo:versions-maven-plugin:2.15.0:set-property \
	-Dproperty=revision -DnewVersion=2-20260913.1237
./mvnw clean install -DskipTests
verify_jars
./mvnw io.fabric8:docker-maven-plugin:0.48.0:build -DskipTests
verify_jars

cd -- "$SCRIPT_DIR"

helm dependency build
helm dependency update
