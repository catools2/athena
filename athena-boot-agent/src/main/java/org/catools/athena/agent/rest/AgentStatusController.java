package org.catools.athena.agent.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

  @Value("${athena.agent.chat.enabled:false}")
  private boolean chatEnabled;

  @Value("${athena.agent.chat.model:}")
  private String model;

  @GetMapping
  @Operation(summary = "Report which agent capabilities are enabled")
  public ResponseEntity<Map<String, Object>> status() {
    return ResponseEntity.ok(Map.of(
        "toolCount", tools.definitions().size(),
        "chatEnabled", chatEnabled,
        "model", chatEnabled ? model : ""));
  }
}
