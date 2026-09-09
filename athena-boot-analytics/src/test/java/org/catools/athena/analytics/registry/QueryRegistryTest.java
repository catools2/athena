package org.catools.athena.analytics.registry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The registry is generated, so these assert the generator's contract rather than hand-written
 * code: the manifest and the SQL must agree, and the one construct that reaches the SQL as text
 * must be constrained.
 */
class QueryRegistryTest {

  private static QueryRegistry registry;

  @BeforeAll
  static void load() throws IOException {
    registry = new QueryRegistry();
    registry.load();
  }

  @Test
  void load_shouldRegisterEveryGeneratedQuery() {
    assertThat(registry.size()).isGreaterThan(150);
    assertThat(registry.all()).allSatisfy(q -> {
      assertThat(q.id()).isNotBlank();
      assertThat(q.sql()).isNotBlank();
      assertThat(q.views()).as("query %s resolved no source", q.id()).isNotNull();
    });
  }

  @Test
  void everyOperatorParameterIsConstrainedToAWhitelist() {
    // An operator is substituted as text, so an unconstrained one would be an injection point.
    List<QueryParam> operators = registry.all().stream()
        .flatMap(q -> q.params().stream())
        .filter(QueryParam::isOperator)
        .toList();

    assertThat(operators).isNotEmpty();
    assertThat(operators).allSatisfy(p ->
        assertThat(p.allowed()).isNotEmpty().allSatisfy(v ->
            assertThat(v).matches("(?i)(NOT )?LIKE")));
  }

  @Test
  void noQueryCarriesAnUntranslatedGrafanaMacro() {
    assertThat(registry.all()).allSatisfy(q ->
        assertThat(q.sql())
            .as("query %s", q.id())
            .doesNotContain("$__")
            .doesNotContain("${"));
  }

  @Test
  void noQueryUsesTheJsonbQuestionMarkOperator() {
    // `?` would be consumed by the JDBC driver as a parameter marker.
    assertThat(registry.all()).allSatisfy(q ->
        assertThat(q.sql().replaceAll("\\{\\{\\w+}}", ""))
            .as("query %s", q.id())
            .doesNotContain(" ? ")
            .doesNotContain(" ?| "));
  }

  @Test
  void operatorSlotsAreNeverBound() {
    assertThat(registry.all()).allSatisfy(q -> q.params().stream()
        .filter(QueryParam::isOperator)
        .forEach(p -> assertThat(q.sql())
            .as("query %s must substitute %s, not bind it", q.id(), p.name())
            .contains("{{" + p.name() + "}}")
            .doesNotContain(":" + p.name())));
  }
}
