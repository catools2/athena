package org.catools.athena.agent.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.agent.mcp.AthenaToolRegistry;
import org.catools.athena.agent.mcp.ToolDefinition;
import org.catools.athena.agent.mcp.ToolResult;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Tool access for agents.
 *
 * <p>Deliberately usable without any model configured: an external agent (Claude Code, an MCP
 * client, a CI script) can drive Athena through these endpoints on its own. The in-app chat in
 * {@link ChatController} is one consumer of the same registry, not a prerequisite for it.
 */
@Slf4j
@RestController
@Tag(name = "Athena Agent Tools")
@RequestMapping(value = "/tools", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class McpController {

  private final AthenaToolRegistry tools;

  @GetMapping
  @Operation(summary = "List the tools an agent can call against Athena")
  public ResponseEntity<List<ToolDefinition>> list() {
    return ResponseEntity.ok(tools.definitions());
  }

  @PostMapping(value = "/{name}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Call one tool")
  public ResponseEntity<Map<String, Object>> call(
      @PathVariable String name,
      @RequestBody(required = false) Map<String, Object> args) {
    ToolResult result = tools.call(name, args == null ? Map.of() : args);
    // A tool error is a 200 with error:true, not an HTTP failure - the caller is an agent that
    // should read the message and try again, not a client that should treat this as an outage.
    return ResponseEntity.ok(Map.of(
        "tool", name,
        "error", result.error(),
        "content", result.content()));
  }
}
