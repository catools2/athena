package org.catools.athena.analytics.common.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.Map;
import org.catools.athena.AthenaSpringBootIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The read-only guarantee, against a real PostgreSQL rather than a mock.
 *
 * <p>Every rejection here has to actually reach the assertion it claims: a data-modifying CTE
 * that {@code AdHocQueryService} somehow let through would otherwise fail silently by returning
 * whatever the last {@code SELECT} in the CTE produced, which looks exactly like success.
 */
class AdHocQueryServiceIT extends AthenaSpringBootIT {

  @Autowired private AdHocQueryService service;

  @Test
  void shallRunASimpleSelect() {
    var result = service.execute("SELECT 1 AS one", Map.of());

    assertThat(result.columns(), not(empty()));
    assertThat(result.rows().get(0).get(0), equalTo(1));
  }

  @Test
  void shallBindNamedParameters() {
    var result = service.execute("SELECT :value AS echoed", Map.of("value", "hello"));

    assertThat(result.rows().get(0).get(0), equalTo("hello"));
  }

  @Test
  void shallBindAnOmittedOptionalParameterWithAStableType() {
    var result = service.execute(
      "SELECT :value IS NULL AS omitted", Collections.singletonMap("value", null));

    assertThat(result.rows().get(0).get(0), equalTo(true));
  }

  @Test
  void shallRejectAWriteStatement() {
    var e = assertThrows(
        AdHocQueryService.InvalidRequest.class,
        () -> service.execute("DELETE FROM athena.view_refresh_log", Map.of()));

    assertThat(e.getMessage(), containsString("SELECT/WITH"));
  }

  @Test
  void shallRejectAWriteHiddenInsideAWithClause() {
    var e = assertThrows(
        AdHocQueryService.InvalidRequest.class,
        () -> service.execute(
            "WITH deleted AS (DELETE FROM athena.view_refresh_log RETURNING *) "
                + "SELECT * FROM deleted",
            Map.of()));

    assertThat(e.getMessage(), containsString("disallowed keyword"));
  }

  @Test
  void shallRejectMultipleStatements() {
    var e = assertThrows(
        AdHocQueryService.InvalidRequest.class,
        () -> service.execute("SELECT 1; SELECT 2", Map.of()));

    assertThat(e.getMessage(), containsString("single statement"));
  }
}
