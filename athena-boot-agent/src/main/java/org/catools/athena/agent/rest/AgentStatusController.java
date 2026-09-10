package org.catools.athena.agent.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.agent.chat.ChatService;
import org.catools.athena.agent.mcp.AthenaToolRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/** What this agent can actually do right now, so the UI does not offer a chat that is switched off. */
@RestController
@Tag(name = "Athena Agent Tools")
@RequestMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AgentStatusController {

  private final AthenaToolRegistry tools;
  private final ChatService chat;

  @Value("${athena.agent.chat.model:}")
  private String model;

  /**
   * Two separate facts, kept separate on purpose. {@code chatAvailable} says the surface exists
   * and will answer; {@code modelConfigured} says whether those answers come from a model. A UI
   * that conflates them either hides a working page or promises answers it cannot give.
   */
  @GetMapping
  @Operation(summary = "Report which agent capabilities are available")
  public ResponseEntity<Map<String, Object>> status() {
    boolean configured = chat.isConfigured();
    return ResponseEntity.ok(Map.of(
        "toolCount", tools.definitions().size(),
        "chatAvailable", true,
        "modelConfigured", configured,
        "model", configured ? model : ""));
  }
}
