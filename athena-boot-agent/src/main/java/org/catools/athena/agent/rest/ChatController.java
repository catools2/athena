package org.catools.athena.agent.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.agent.chat.ChatService;
import org.catools.athena.agent.chat.SkillLibrary;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

/**
 * The in-app chat surface.
 *
 * <p>Always mounted. Without a model configured it answers with a clear "not configured" message
 * rather than disappearing, which keeps the page reviewable and the contract stable for the
 * frontend. {@code /status} reports whether answers are real.
 */
@RestController
@Tag(name = "Athena Agent Chat")
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

  private final ChatService chat;
  private final SkillLibrary skills;

  @GetMapping(value = "/skills", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "List the methods the agent can be pointed at")
  public ResponseEntity<List<Map<String, String>>> listSkills() {
    return ResponseEntity.ok(skills.all().stream()
        .map(s -> Map.of("name", s.name(), "description", s.description()))
        .toList());
  }

  @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Ask a question; the answer streams back with its tool calls")
  public SseEmitter ask(@RequestBody ChatService.ChatRequest request) {
    return chat.stream(request);
  }
}
