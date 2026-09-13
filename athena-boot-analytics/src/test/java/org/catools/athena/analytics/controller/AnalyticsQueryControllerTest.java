package org.catools.athena.analytics.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import org.catools.athena.analytics.common.service.AdHocQueryService;
import org.catools.athena.analytics.rest.controller.AnalyticsQueryController;
import org.catools.athena.model.analytics.AdHocQueryRequestDto;
import org.catools.athena.model.analytics.QueryResultDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

/** Unit contract for the one analytics execution path. */
class AnalyticsQueryControllerTest {

  private AdHocQueryService service;
  private AnalyticsQueryController controller;

  @BeforeEach
  void setUp() {
    service = mock(AdHocQueryService.class);
    controller = new AnalyticsQueryController(service);
  }

  @Test
  void executeEndpointReturnsRows() {
    QueryResultDto result = new QueryResultDto(
        "adhoc",
        List.of(new QueryResultDto.Column("one", "int4")),
        List.<List<Object>>of(List.of(1)),
        false,
        List.of(),
        List.of());
    when(service.execute("SELECT 1 AS one", Map.of())).thenReturn(result);

    ResponseEntity<QueryResultDto> response = controller.execute(
        new AdHocQueryRequestDto("SELECT 1 AS one", Map.of()));

    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody().rows().getFirst().getFirst(), equalTo(1));
  }

  @Test
  void executeEndpointPropagatesReadOnlyValidationErrors() {
    when(service.execute("DELETE FROM athena.view_refresh_log", Map.of()))
        .thenThrow(new AdHocQueryService.InvalidRequest("Only SELECT/WITH statements are allowed"));

    AdHocQueryService.InvalidRequest error = assertThrows(
        AdHocQueryService.InvalidRequest.class,
        () -> controller.execute(
            new AdHocQueryRequestDto("DELETE FROM athena.view_refresh_log", Map.of())));

    assertThat(error.getMessage(), equalTo("Only SELECT/WITH statements are allowed"));
  }
}
