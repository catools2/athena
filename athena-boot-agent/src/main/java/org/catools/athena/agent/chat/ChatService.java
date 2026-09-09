package org.catools.athena.agent.chat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.agent.mcp.AthenaToolRegistry;
import org.catools.athena.agent.mcp.ToolDefinition;
import org.catools.athena.agent.mcp.ToolResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The agent loop: ask the model, run any tools it asks for, feed the results back, repeat.
 *
 * <p>Streamed over SSE because a turn that calls three tools takes long enough that a blank page
 * reads as a hang. The client is told about each tool call as it happens, which is also the only
 * honest way to show where an answer came from.
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "athena.agent.chat.enabled", havingValue = "true")
@RequiredArgsConstructor
public class ChatService {

  private static final String ANTHROPIC_URL = "https://api.anthropic.com/v1/messages";
  private static final String ANTHROPIC_VERSION = "2023-06-01";

  private final AthenaToolRegistry tools;
  private final SkillLibrary skills;
  private final ObjectMapper mapper = new ObjectMapper();
  private final ExecutorService workers = Executors.newCachedThreadPool();

  @Value("${athena.agent.chat.api-key}")
  private String apiKey;

  @Value("${athena.agent.chat.model}")
  private String model;

  @Value("${athena.agent.chat.max-tokens}")
  private int maxTokens;

  @Value("${athena.agent.chat.max-tool-iterations}")
  private int maxToolIterations;

  public record ChatRequest(String message, String skill, List<Map<String, Object>> history) {}

  public SseEmitter stream(ChatRequest request) {
    SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
    workers.submit(() -> {
      try {
        runTurn(request, emitter);
        emitter.send(SseEmitter.event().name("done").data("{}"));
        emitter.complete();
      } catch (Exception e) {
        log.warn("Chat turn failed", e);
        try {
          emitter.send(SseEmitter.event().name("error").data(
              mapper.writeValueAsString(Map.of("message", String.valueOf(e.getMessage())))));
        } catch (Exception ignored) {
          // The client is gone; nothing useful left to do.
        }
        emitter.complete();
      }
    });
    return emitter;
  }

  private void runTurn(ChatRequest request, SseEmitter emitter) throws Exception {
    List<Map<String, Object>> messages = new ArrayList<>();
    if (request.history() != null) {
      messages.addAll(request.history());
    }
    messages.add(Map.of("role", "user", "content", request.message()));

    RestClient client = RestClient.create();

    for (int iteration = 0; iteration < maxToolIterations; iteration++) {
      Map<String, Object> body = new LinkedHashMap<>();
      body.put("model", model);
      body.put("max_tokens", maxTokens);
      body.put("system", systemPrompt(request.skill()));
      body.put("messages", messages);
      body.put("tools", tools.definitions().stream().map(ChatService::toolPayload).toList());

      String response = client.post()
          .uri(ANTHROPIC_URL)
          .header("x-api-key", apiKey)
          .header("anthropic-version", ANTHROPIC_VERSION)
          .contentType(MediaType.APPLICATION_JSON)
          .body(body)
          .retrieve()
          .body(String.class);

      JsonNode parsed = mapper.readTree(response);
      List<Map<String, Object>> assistantContent = new ArrayList<>();
      List<Map<String, Object>> toolResults = new ArrayList<>();

      for (JsonNode block : parsed.path("content")) {
        String type = block.path("type").asText();
        if ("text".equals(type)) {
          String text = block.path("text").asText();
          assistantContent.add(Map.of("type", "text", "text", text));
          emitter.send(SseEmitter.event().name("text").data(
              mapper.writeValueAsString(Map.of("text", text))));
        } else if ("tool_use".equals(type)) {
          String name = block.path("name").asText();
          String id = block.path("id").asText();
          Map<String, Object> args = mapper.convertValue(block.path("input"), Map.class);

          assistantContent.add(Map.of(
              "type", "tool_use", "id", id, "name", name, "input", args == null ? Map.of() : args));
          emitter.send(SseEmitter.event().name("tool").data(
              mapper.writeValueAsString(Map.of("name", name, "input", args == null ? Map.of() : args))));

          ToolResult result = tools.call(name, args == null ? Map.of() : args);
          toolResults.add(Map.of(
              "type", "tool_result",
              "tool_use_id", id,
              "is_error", result.error(),
              "content", mapper.writeValueAsString(result.content())));
        }
      }

      messages.add(Map.of("role", "assistant", "content", assistantContent));

      if (toolResults.isEmpty()) {
        return; // The model answered without needing anything else.
      }
      messages.add(Map.of("role", "user", "content", toolResults));
    }

    // Hitting the cap is worth saying out loud rather than silently returning a partial answer.
    emitter.send(SseEmitter.event().name("text").data(mapper.writeValueAsString(Map.of(
        "text", "\n\n_Stopped after " + maxToolIterations
            + " tool rounds without reaching an answer._"))));
  }

  private String systemPrompt(String skillName) {
    StringBuilder prompt = new StringBuilder("""
        You are Athena's analyst. Athena is a QA platform that holds test cycles and executions,
        git commits, CI pipeline runs, Kubernetes pods, API specs and action timings, all in one
        database.

        Answer from the tools, never from memory. Query ids are opaque - call list_queries to find
        one rather than guessing. If a query returns no rows, say so; do not present an empty
        result as a finding.

        Numbers you report must be traceable to a tool call. When a result is a materialized view
        snapshot the tool tells you when it was last refreshed - if it is stale, say so, because
        the user cannot see that themselves.
        """);

    if (skillName != null && !skillName.isBlank()) {
      skills.find(skillName).ifPresent(skill ->
          prompt.append("\n\n# Method for this request\n\n").append(skill.content()));
    }
    return prompt.toString();
  }

  private static Map<String, Object> toolPayload(ToolDefinition tool) {
    return Map.of(
        "name", tool.name(),
        "description", tool.description(),
        "input_schema", tool.inputSchema());
  }
}
