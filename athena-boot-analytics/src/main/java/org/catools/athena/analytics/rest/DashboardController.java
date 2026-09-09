package org.catools.athena.analytics.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.analytics.registry.DashboardRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

/** Dashboard layouts. The renderer reads a spec and asks {@code /queries/{id}/run} for the data. */
@RestController
@Tag(name = "Athena Analytics Rest API")
@RequestMapping(value = "/dashboards", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class DashboardController {

  private final DashboardRegistry dashboards;

  @GetMapping
  @Operation(summary = "List available dashboards")
  public ResponseEntity<List<Map<String, Object>>> list() {
    return ResponseEntity.ok(dashboards.all().stream()
        .map(d -> Map.<String, Object>of(
            "id", d.path("id").asText(),
            "title", d.path("title").asText(),
            "variant", d.path("variant").asText(),
            "panelCount", d.path("panels").size(),
            "renderable", StreamSupport.stream(d.path("panels").spliterator(), false)
                .filter(DashboardRegistry::isRenderable).count()))
        .toList());
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get one dashboard spec", responses = {
      @ApiResponse(responseCode = "200", description = "Spec found"),
      @ApiResponse(responseCode = "404", description = "No such dashboard")
  })
  public ResponseEntity<String> get(@PathVariable String id) {
    return dashboards.rawSpec(id).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
