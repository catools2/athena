package org.catools.athena.analytics.registry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * Dashboard layouts generated from the Grafana JSON by {@code tools/build_dashboards.py}.
 *
 * <p>Specs are served as-is; the renderer interprets them. What this class adds is the check that
 * every panel's {@code queryId} actually exists in the {@link QueryRegistry} - a spec pointing at a
 * query that was renamed or dropped would otherwise render as a panel that is permanently, silently
 * empty.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DashboardRegistry {

  private static final String SPEC_DIR = "classpath:analytics/dashboards/*.json";

  private final QueryRegistry queries;
  private final ObjectMapper mapper = new ObjectMapper();
  private final Map<String, JsonNode> dashboards = new LinkedHashMap<>();
  /**
   * The spec exactly as generated. Served verbatim rather than re-serialized from the tree:
   * a JsonNode returned from a controller is serialized as a bean, not as its content.
   */
  private final Map<String, String> rawSpecs = new LinkedHashMap<>();

  @PostConstruct
  void load() throws IOException {
    Resource[] specs = new PathMatchingResourcePatternResolver().getResources(SPEC_DIR);
    List<String> problems = new ArrayList<>();

    for (Resource spec : specs) {
      String raw;
      try (InputStream in = spec.getInputStream()) {
        raw = new String(in.readAllBytes(), StandardCharsets.UTF_8);
      }
      JsonNode node = mapper.readTree(raw);
      String id = node.path("id").asText();
      if (id.isBlank()) {
        problems.add(spec.getFilename() + ": spec has no id");
        continue;
      }
      for (JsonNode panel : arrayOf(node, "panels")) {
        for (JsonNode q : arrayOf(panel, "queries")) {
          String queryId = q.path("queryId").asText();
          if (queries.find(queryId).isEmpty()) {
            problems.add(id + ": panel '" + panel.path("title").asText()
                + "' references unknown query " + queryId);
          }
        }
      }
      dashboards.put(id, node);
      rawSpecs.put(id, raw);
    }

    if (!problems.isEmpty()) {
      throw new IllegalStateException(
          "Dashboard specs are inconsistent with the query registry:\n  "
              + String.join("\n  ", problems));
    }

    long panels = dashboards.values().stream().mapToLong(d -> arrayOf(d, "panels").size()).sum();
    long renderable = dashboards.values().stream()
        .flatMap(d -> StreamSupport.stream(arrayOf(d, "panels").spliterator(), false))
        .filter(DashboardRegistry::isRenderable)
        .count();
    log.info("Loaded {} dashboards, {} panels ({} backed by a registered query).",
        dashboards.size(), panels, renderable);
  }

  /**
   * A panel the renderer can draw: it is not marked unsupported and it names at least one
   * registered query. Rows and text panels are layout, not data, so they are excluded.
   */
  public static boolean isRenderable(JsonNode panel) {
    return !panel.has("unsupported") && arrayOf(panel, "queries").size() > 0;
  }

  /**
   * Explicit array lookup. {@code withArray(String)} has changed meaning across Jackson versions
   * (property name vs JSON Pointer) and creates the node when absent, which made a missing
   * "queries" field read as present.
   */
  private static JsonNode arrayOf(JsonNode node, String field) {
    JsonNode value = node.path(field);
    return value.isArray() ? value : MissingNode.getInstance();
  }

  public Collection<JsonNode> all() {
    return dashboards.values();
  }

  public Optional<JsonNode> find(String id) {
    return Optional.ofNullable(dashboards.get(id));
  }

  /** The generated spec text, for serving unchanged. */
  public Optional<String> rawSpec(String id) {
    return Optional.ofNullable(rawSpecs.get(id));
  }
}
