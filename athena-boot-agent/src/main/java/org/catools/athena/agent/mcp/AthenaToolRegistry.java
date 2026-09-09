package org.catools.athena.agent.mcp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The tools an agent can call against Athena.
 *
 * <p>Every one of them is a thin wrapper over the analytics REST API. That is the design: the
 * agent cannot reach data the UI cannot, cannot run SQL the registry has not vetted, and cannot
 * produce a number the dashboards would disagree with.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AthenaToolRegistry {

  private static final TypeReference<List<Map<String, Object>>> LIST_OF_MAPS = new TypeReference<>() {};

  private final RestClient analytics;
  private final ObjectMapper mapper = new ObjectMapper();

  public List<ToolDefinition> definitions() {
    return List.of(
        new ToolDefinition(
            "list_queries",
            "List every analytics query Athena can run, with the parameters each one takes. "
                + "Call this first when you do not already know which query answers the question - "
                + "query ids are opaque, so guessing one will not work.",
            schema(Map.of(
                "search", prop("string",
                    "Optional case-insensitive filter on the query title or the views it reads.")))),
        new ToolDefinition(
            "run_query",
            "Run one analytics query by id and return its rows. Parameters must match what "
                + "list_queries reports: 'instant' takes an ISO-8601 timestamp, 'list' takes an "
                + "array of strings (an empty array means no filter), and 'operator' only accepts "
                + "one of its enumerated values.",
            schema(
                Map.of(
                    "queryId", prop("string", "The query id from list_queries."),
                    "params", Map.of("type", "object",
                        "description", "Parameter values, keyed by parameter name.",
                        "additionalProperties", true)),
                List.of("queryId"))),
        new ToolDefinition(
            "list_dashboards",
            "List Athena's dashboards and how many of their panels are backed by a query. "
                + "Useful for answering 'what reporting already exists for X'.",
            schema(Map.of())),
        new ToolDefinition(
            "describe_dashboard",
            "Return one dashboard's full spec: its panels, the query behind each, and its filters. "
                + "Use it to find the query that produces a chart the user is asking about.",
            schema(Map.of("dashboardId", prop("string", "The dashboard id from list_dashboards.")),
                List.of("dashboardId"))),
        new ToolDefinition(
            "correlate_change",
            "Given a time window, return what changed and what happened: commits, pipeline runs, "
                + "test executions, pods and timing percentiles, all bound to the same interval. "
                + "This is the tool for 'what broke after that release' questions.",
            schema(
                Map.of(
                    "timeFrom", prop("string", "ISO-8601 start of the window."),
                    "timeTo", prop("string", "ISO-8601 end of the window.")),
                List.of("timeFrom", "timeTo"))));
  }

  public ToolResult call(String name, Map<String, Object> args) {
    try {
      return switch (name) {
        case "list_queries" -> listQueries(str(args.get("search")));
        case "run_query" -> runQuery(str(args.get("queryId")), asMap(args.get("params")));
        case "list_dashboards" -> ToolResult.ok(get("/dashboards", LIST_OF_MAPS));
        case "describe_dashboard" -> ToolResult.ok(
            get("/dashboards/" + str(args.get("dashboardId")), new TypeReference<Map<String, Object>>() {}));
        case "correlate_change" -> correlate(str(args.get("timeFrom")), str(args.get("timeTo")));
        default -> ToolResult.failed("Unknown tool '" + name + "'");
      };
    } catch (RestClientResponseException e) {
      // Hand the model the service's own message; it is usually precise enough to correct from.
      return ToolResult.failed("Athena rejected the call: " + e.getResponseBodyAsString());
    } catch (RuntimeException e) {
      log.warn("Tool {} failed", name, e);
      return ToolResult.failed("Tool '" + name + "' failed: " + e.getMessage());
    }
  }

  private ToolResult listQueries(String search) {
    List<Map<String, Object>> all = get("/queries", LIST_OF_MAPS);
    if (search == null || search.isBlank()) {
      return ToolResult.ok(summarise(all));
    }
    String needle = search.toLowerCase();
    List<Map<String, Object>> matched = all.stream()
        .filter(q -> String.valueOf(q.get("title")).toLowerCase().contains(needle)
            || String.valueOf(q.get("views")).toLowerCase().contains(needle))
        .toList();
    return ToolResult.ok(summarise(matched));
  }

  /** Trim to what the model needs to choose and call a query; the full manifest is mostly noise. */
  private List<Map<String, Object>> summarise(List<Map<String, Object>> queries) {
    List<Map<String, Object>> out = new ArrayList<>();
    for (Map<String, Object> q : queries) {
      out.add(Map.of(
          "id", q.get("id"),
          "title", q.get("title"),
          "params", q.get("params"),
          "views", q.get("views")));
    }
    return out;
  }

  private ToolResult runQuery(String queryId, Map<String, Object> params) {
    if (queryId == null || queryId.isBlank()) {
      return ToolResult.failed("queryId is required; call list_queries to find one.");
    }
    Map<String, Object> result = analytics.post()
        .uri("/queries/{id}/run", queryId)
        .header("Content-Type", "application/json")
        .body(params == null ? Map.of() : params)
        .retrieve()
        .body(new org.springframework.core.ParameterizedTypeReference<>() {});
    return ToolResult.ok(result);
  }

  /**
   * The five correlation queries, run against one window and returned together. Doing this
   * server-side keeps the model from having to make five calls and re-derive the same window
   * each time - and from getting the windows subtly different between them.
   */
  private ToolResult correlate(String timeFrom, String timeTo) {
    if (timeFrom == null || timeTo == null) {
      return ToolResult.failed("Both timeFrom and timeTo are required.");
    }
    Map<String, Object> window = Map.of("timeFrom", timeFrom, "timeTo", timeTo);
    Map<String, Object> out = new java.util.LinkedHashMap<>();
    out.put("window", window);
    out.put("commits", rows("correlate_commits",
        Map.of("timeFrom", timeFrom, "timeTo", timeTo, "repository", List.of())));
    out.put("pipelineRuns", rows("correlate_pipeline_runs", window));
    out.put("testExecutions", rows("correlate_test_executions", window));
    out.put("pods", rows("correlate_pods",
        new java.util.HashMap<>(Map.of("timeFrom", timeFrom, "timeTo", timeTo))));
    out.put("timing", rows("correlate_metrics", window));
    return ToolResult.ok(out);
  }

  private Object rows(String queryId, Map<String, Object> params) {
    try {
      ToolResult result = runQuery(queryId, params);
      return result.error() ? Map.of("error", result.content()) : result.content();
    } catch (RuntimeException e) {
      // One empty section is better than losing the other four.
      return Map.of("error", String.valueOf(e.getMessage()));
    }
  }

  private <T> T get(String path, TypeReference<T> type) {
    String body = analytics.get().uri(path).retrieve().body(String.class);
    try {
      return mapper.readValue(body, type);
    } catch (Exception e) {
      throw new IllegalStateException("Could not read " + path, e);
    }
  }

  private static Map<String, Object> schema(Map<String, Object> properties) {
    return schema(properties, List.of());
  }

  private static Map<String, Object> schema(Map<String, Object> properties, List<String> required) {
    return Map.of("type", "object", "properties", properties, "required", required);
  }

  private static Map<String, Object> prop(String type, String description) {
    return Map.of("type", type, "description", description);
  }

  private static String str(Object value) {
    return value == null ? null : String.valueOf(value);
  }

  @SuppressWarnings("unchecked")
  private static Map<String, Object> asMap(Object value) {
    return value instanceof Map ? (Map<String, Object>) value : Map.of();
  }
}
