package org.catools.athena.analytics.service;

import org.catools.athena.analytics.registry.ParamKind;
import org.catools.athena.analytics.registry.QueryParam;
import org.catools.athena.analytics.registry.QueryRegistry;
import org.catools.athena.analytics.registry.RegisteredQuery;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

/**
 * Executes every curated query against a real PostgreSQL, because nothing cheaper is sufficient.
 *
 * <p>Parsing the SQL proves it is well-formed and {@code PREPARE} proves the planner accepts it,
 * and both have already let real defects through here: {@code round(double precision, integer)
 * does not exist}, {@code could not determine data type of parameter}, and {@code operator is not
 * unique: unknown - unknown} all pass a syntax check and all fail on execution. The parameter
 * binding is also under test - the {@code Types.VARCHAR} on nulls and the {@code text[]} array
 * value are exactly the parts a PREPARE never exercises.
 *
 * <p>Skips itself when no database is reachable, so a build without one stays green. Point it
 * somewhere with {@code -Dathena.analytics.test.url=jdbc:postgresql://localhost:65432/athena}.
 */
class CuratedQueryLiveTest {

  private static final String URL = System.getProperty("athena.analytics.test.url",
      System.getenv().getOrDefault("ATHENA_ANALYTICS_TEST_URL",
          "jdbc:postgresql://localhost:5432/athena"));
  private static final String USER = System.getProperty("athena.analytics.test.username",
      System.getenv().getOrDefault("ATHENA_ANALYTICS_TEST_USERNAME", "athena_ro"));
  private static final String PASSWORD = System.getProperty("athena.analytics.test.password",
      System.getenv().getOrDefault("ATHENA_ANALYTICS_TEST_PASSWORD", "athena_ro"));

  private static QueryRegistry registry;
  private static AnalyticsQueryService service;
  private static boolean reachable;

  @BeforeAll
  static void connect() throws Exception {
    registry = new QueryRegistry();
    registry.load();

    DriverManager.setLoginTimeout(3);
    try (Connection probe = DriverManager.getConnection(URL, USER, PASSWORD)) {
      reachable = probe.isValid(3);
    } catch (Exception e) {
      reachable = false;
      return;
    }

    SimpleDriverDataSource dataSource = new SimpleDriverDataSource(
        new org.postgresql.Driver(), URL, USER, PASSWORD);
    NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
    service = new AnalyticsQueryService(registry, jdbc, new ViewFreshnessService(jdbc));
    // Field-injected in production; without this the cap is 0 and every result reads as truncated.
    ReflectionTestUtils.setField(service, "maxRows", 50_000);
  }

  @TestFactory
  Stream<DynamicTest> everyCuratedQueryExecutes() {
    assumeThat(reachable)
        .as("no PostgreSQL at %s - set athena.analytics.test.url to run this", URL)
        .isTrue();

    return registry.all().stream()
        .filter(q -> "curated".equals(q.origin()))
        .map(query -> DynamicTest.dynamicTest(query.id(), () -> {
          QueryResult result = service.run(query.id(), defaults(query));
          // A query that returns no columns did not really run; an empty row list is legitimate
          // on a sparse fixture and is not what this asserts.
          assertThat(result.columns())
              .as("%s returned no column metadata", query.id())
              .isNotEmpty();
        }));
  }

  /**
   * The parameter set a page sends with no filters applied: a real window, every optional filter
   * absent. That is the shape almost every request has, so it is the one worth guarding - and a
   * null scalar is precisely what breaks an optional filter that forgot its cast.
   */
  private static Map<String, Object> defaults(RegisteredQuery query) {
    Instant now = Instant.now();
    Map<String, Object> params = new HashMap<>();
    for (QueryParam param : query.params()) {
      if (param.kind() == ParamKind.INSTANT) {
        params.put(param.name(), param.name().toLowerCase().contains("from")
            ? now.minus(Duration.ofDays(30)).toString()
            : now.toString());
      } else if (param.kind() == ParamKind.LIST) {
        params.put(param.name(), List.of());
      } else {
        params.put(param.name(), requiredScalar(query, param));
      }
    }
    return params;
  }

  /**
   * Three queries take an identifier rather than a filter - {@code cycle}, {@code item},
   * {@code action} - and passing null would exercise a code path the UI never takes. A value that
   * matches nothing still executes the statement, which is what is being checked.
   */
  private static Object requiredScalar(RegisteredQuery query, QueryParam param) {
    boolean identifier = switch (param.name()) {
      case "cycle", "item" -> true;
      case "action" -> query.id().startsWith("perf_action_") || "perf_samples".equals(query.id());
      default -> false;
    };
    return identifier ? "__no_such_value__" : null;
  }
}
