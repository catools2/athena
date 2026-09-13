package org.catools.athena.analytics.rest.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.analytics.common.service.AdHocQueryService;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.model.analytics.AdHocQueryRequestDto;
import org.catools.athena.model.analytics.QueryResultDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The read-only SQL execution API used by Atlas Console and the agent. */
@Slf4j
@RestController
@Tag(name = "Athena Analytics Rest API")
@RequestMapping(value = AnalyticsQueryController.QUERIES, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AnalyticsQueryController {

  public static final String QUERIES = "/queries";

  private final AdHocQueryService adHocQueryService;

  @PostMapping(value = "/execute", consumes = APPLICATION_JSON_VALUE)
  @Operation(
      summary = "Run a caller-built, read-only SQL statement",
      description =
          "Run one SELECT/WITH statement in a PostgreSQL read-only transaction. The service "
              + "rejects writes, stacked statements, and data-modifying CTEs.",
      responses = {
        @ApiResponse(responseCode = "200", description = "Rows returned"),
        @ApiResponse(responseCode = "400", description = "Invalid or non-read-only SQL")
      })
  public ResponseEntity<QueryResultDto> execute(@RequestBody final AdHocQueryRequestDto request) {
    log.info("execute(params={})", request.params() == null ? Map.of() : request.params().keySet());
    return ResponseEntityUtils.ok(adHocQueryService.execute(request.sql(), request.params()));
  }
}
