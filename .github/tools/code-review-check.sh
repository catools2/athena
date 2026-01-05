#!/bin/bash
# Athena Code Review Helper Script
# This script helps automate common code review checks

set -e

# Colors for output
RED='\033[0;31m'
YELLOW='\033[1;33m'
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print section headers
print_header() {
    echo -e "\n${BLUE}========================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}========================================${NC}\n"
}

# Function to print success
print_success() {
    echo -e "${GREEN}✅ $1${NC}"
}

# Function to print warning
print_warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

# Function to print error
print_error() {
    echo -e "${RED}❌ $1${NC}"
}

# Change to project root
cd "$(dirname "$0")/.."

print_header "Athena Code Review Automated Checks"

# Check 1: Compile Check
print_header "1. Compilation Check"
if ./mvnw clean compile -DskipTests -q; then
    print_success "Code compiles successfully"
else
    print_error "Compilation failed - review compile errors"
    exit 1
fi

# Check 2: Unit Tests
print_header "2. Unit Tests"
if ./mvnw test -q; then
    print_success "All unit tests passed"
else
    print_warning "Some unit tests failed - review test results"
fi

# Check 3: Integration Tests
print_header "3. Integration Tests (Sample)"
echo "Running integration tests for modified modules..."
# Add specific modules as needed
if ./mvnw verify -pl athena-boot-core -DskipTests=false -q; then
    print_success "Integration tests passed"
else
    print_warning "Some integration tests failed"
fi

# Check 4: Code Style
print_header "4. Code Style Check"
if ./mvnw spotless:check -q 2>/dev/null; then
    print_success "Code style is consistent"
else
    print_warning "Code style issues found - run './mvnw spotless:apply' to fix"
fi

# Check 5: Common Pattern Violations
print_header "5. Pattern Violations Check"

echo "Checking for common anti-patterns..."

# Check for @Autowired field injection in mappers
MAPPER_AUTOWIRED=$(find . -name "*Mapper.java" -type f -exec grep -l "@Autowired" {} \; 2>/dev/null | wc -l)
if [ "$MAPPER_AUTOWIRED" -gt 0 ]; then
    print_error "Found $MAPPER_AUTOWIRED mapper(s) with @Autowired field injection"
    find . -name "*Mapper.java" -type f -exec grep -l "@Autowired" {} \; 2>/dev/null | head -5
else
    print_success "No @Autowired field injection in mappers"
fi

# Check for abstract mapper classes
ABSTRACT_MAPPERS=$(find . -name "*Mapper.java" -type f -exec grep -l "abstract class.*Mapper" {} \; 2>/dev/null | wc -l)
if [ "$ABSTRACT_MAPPERS" -gt 0 ]; then
    print_warning "Found $ABSTRACT_MAPPERS abstract mapper class(es) - consider using interfaces"
    find . -name "*Mapper.java" -type f -exec grep -l "abstract class.*Mapper" {} \; 2>/dev/null | head -5
else
    print_success "All mappers are interfaces"
fi

# Check for entities without @Table annotation
ENTITIES_NO_TABLE=$(find . -name "*.java" -path "*/entity/*" -type f -exec grep -l "@Entity" {} \; | while read file; do
    if ! grep -q "@Table" "$file"; then
        echo "$file"
    fi
done | wc -l)
if [ "$ENTITIES_NO_TABLE" -gt 0 ]; then
    print_warning "Found $ENTITIES_NO_TABLE entity/entities without @Table annotation"
else
    print_success "All entities have @Table annotation"
fi

# Check for System.out.println (should use logger)
SYSTEM_OUT=$(find . -name "*.java" -path "*/src/main/*" -type f -exec grep -l "System.out.println" {} \; 2>/dev/null | wc -l)
if [ "$SYSTEM_OUT" -gt 0 ]; then
    print_warning "Found $SYSTEM_OUT file(s) using System.out.println - use logger instead"
else
    print_success "No System.out.println found in main code"
fi

# Check 6: Test Coverage Patterns
print_header "6. Test Coverage Check"

# Check for test classes without @TestInstance
TEST_NO_LIFECYCLE=$(find . -name "*IT.java" -path "*/src/it/*" -type f | while read file; do
    if grep -q "@SpringBootTest" "$file" && ! grep -q "@TestInstance" "$file"; then
        echo "$file"
    fi
done | wc -l)
if [ "$TEST_NO_LIFECYCLE" -gt 0 ]; then
    print_warning "Found $TEST_NO_LIFECYCLE integration test(s) without @TestInstance annotation"
else
    print_success "All integration tests have @TestInstance annotation"
fi

# Check 7: Database Migration Check
print_header "7. Database Migration Consistency"

# Check if new entities have corresponding migrations
echo "Checking for Flyway migrations..."
for module in athena-boot-core athena-boot-git athena-boot-kube athena-boot-metric athena-boot-spec athena-boot-pipeline athena-boot-tms; do
    if [ -d "$module/src/main/resources/db/migration" ]; then
        MIGRATION_COUNT=$(ls -1 "$module/src/main/resources/db/migration"/*.sql 2>/dev/null | wc -l)
        echo "  $module: $MIGRATION_COUNT migration(s)"
    fi
done

# Check 8: Dependency Check
print_header "8. Dependency Analysis"
echo "Checking for dependency conflicts..."
./mvnw dependency:analyze -q 2>&1 | grep -E "WARNING|Used undeclared|Unused declared" | head -10 || print_success "No major dependency issues"

# Summary
print_header "Review Summary"
echo -e "✅ Automated checks completed"
echo -e "\n📋 Manual review checklist:"
echo -e "  [ ] Review entity annotations and indexes"
echo -e "  [ ] Check mapper implementations"
echo -e "  [ ] Verify test coverage adequacy"
echo -e "  [ ] Review API design and DTOs"
echo -e "  [ ] Check error handling"
echo -e "  [ ] Verify documentation updates"
echo -e "\n📖 See .github/code-review-agent.md for detailed guidelines"
echo -e "📝 Use .github/code-review-quickstart.md for review templates\n"

print_success "Automated review checks complete!"

