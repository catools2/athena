package org.catools.athena.core.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.controlleradvice.ControllerErrorHandler;
import org.catools.athena.core.common.service.QueryService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Ad-hoc read-only SQL, for reporting tools that need a shape the typed endpoints do not expose.
 *
 * <p><strong>Disabled by default, and off is the right setting for most deployments.</strong> It
 * executes whatever SQL the caller sends; there is no allowlist and the statement cannot be
 * parameterised. Enabling it (see {@code athena.query.enabled}) is a decision to trust every caller
 * that can reach this service with read access to every table {@code athena_ro} can see, so put it
 * behind authentication and network policy before turning it on. Writes are refused by the database
 * itself - see {@link org.catools.athena.core.common.config.QueryDataSourceConfig}.
 */
@Slf4j
@RestController
@Tag(name = "Athena Query Rest API")
@RequestMapping(value = QueryController.QUERY, produces = MediaType.APPLICATION_JSON_VALUE)
@Import(ControllerErrorHandler.class)
@RequiredArgsConstructor
@ConditionalOnProperty(name = "athena.query.enabled", havingValue = "true")
public class QueryController {

  public static final String QUERY = "/query";
  private final QueryService queryService;

  @GetMapping("/record")
  @Operation(summary = "Execute a read-only query that returns a single result", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully executed query and retrieved single result"),
      @ApiResponse(responseCode = "204", description = "Query executed but no result found"),
      @ApiResponse(responseCode = "400", description = "Failed to execute query")
  })
  public ResponseEntity<Object> querySingleResult(
      @Parameter(name = "query", description = "The read-only SQL query to execute", required = true)
      @RequestParam final String query
  ) {
    log.info("querySingleResult(query={})", query);
    return queryService.querySingleResult(query)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.noContent().build());
  }

  @GetMapping("/records")
  @Operation(summary = "Execute a read-only query that returns multiple results", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully executed query and retrieved results"),
      @ApiResponse(responseCode = "204", description = "Query executed but no results found"),
      @ApiResponse(responseCode = "400", description = "Failed to execute query")
  })
  public ResponseEntity<Set<Object>> queryCollection(
      @Parameter(name = "query", description = "The read-only SQL query to execute", required = true)
      @RequestParam final String query
  ) {
    log.info("queryCollection(query={})", query);
    return queryService.queryCollection(query)
        .filter(set -> !set.isEmpty())
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.noContent().build());
  }
}
