package org.catools.athena.analytics.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.analytics.registry.QueryRegistry;
import org.catools.athena.analytics.registry.RegisteredQuery;
import org.catools.athena.analytics.service.AnalyticsQueryService;
import org.catools.athena.analytics.service.QueryResult;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * The dashboard data API.
 *
 * <p>Callers name a query and supply parameters; they cannot supply SQL. That is the whole point:
 * the panel SQL was reviewed once, at generation time, and is served from the artifact rather than
 * accepted from the browser.
 */
@Slf4j
@RestController
@Tag(name = "Athena Analytics Rest API")
@RequestMapping(value = "/queries", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AnalyticsQueryController {

  private final QueryRegistry registry;
  private final AnalyticsQueryService queryService;

  public record QuerySummary(String id, String title, List<String> views, List<String> tables,
                             List<ParamSpec> params, List<String> dashboards, String origin) {}

  public record ParamSpec(String name, String kind, List<String> allowed) {}

  @GetMapping
  @Operation(summary = "List every registered analytics query and its parameters")
  public ResponseEntity<List<QuerySummary>> list() {
    return ResponseEntity.ok(registry.all().stream().map(AnalyticsQueryController::summarise).toList());
  }

  @GetMapping("/{id}")
  @Operation(summary = "Describe one registered query", responses = {
      @ApiResponse(responseCode = "200", description = "Query found"),
      @ApiResponse(responseCode = "404", description = "No such query")
  })
  public ResponseEntity<QuerySummary> get(@PathVariable String id) {
    return registry.find(id)
        .map(AnalyticsQueryController::summarise)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping(value = "/{id}/run", consumes = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Run a registered query", responses = {
      @ApiResponse(responseCode = "200", description = "Rows returned"),
      @ApiResponse(responseCode = "400", description = "Unknown query or invalid parameters")
  })
  public ResponseEntity<QueryResult> run(
      @PathVariable String id,
      @RequestBody(required = false) Map<String, Object> params) {
    return ResponseEntity.ok(queryService.run(id, params == null ? Map.of() : params));
  }

  @ExceptionHandler(AnalyticsQueryService.InvalidRequest.class)
  public ResponseEntity<Map<String, String>> onInvalidRequest(
      AnalyticsQueryService.InvalidRequest e) {
    return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
  }

  private static QuerySummary summarise(RegisteredQuery q) {
    return new QuerySummary(q.id(), q.title(), q.views(), q.tables(),
        q.params().stream()
            .map(p -> new ParamSpec(p.name(), p.kind().name().toLowerCase(), p.allowed()))
            .toList(),
        q.dashboards(),
        q.origin());
  }
}
