package org.catools.athena.agent.mcp;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The tool descriptions are the entire interface a model has to Athena, so they are worth
 * asserting on: a tool with a vague description or a schema that does not match its arguments
 * fails silently, as a model that never calls it or always calls it wrongly.
 */
class AthenaToolRegistryTest {

  private final AthenaToolRegistry registry =
      new AthenaToolRegistry(RestClient.builder().baseUrl("http://localhost:0").build());

  @Test
  void everyToolIsDescribedWellEnoughToChoose() {
    List<ToolDefinition> tools = registry.definitions();
    assertThat(tools).isNotEmpty();
    assertThat(tools).allSatisfy(tool -> {
      assertThat(tool.name()).matches("[a-z_]+");
      assertThat(tool.description()).hasSizeGreaterThan(40);
      assertThat(tool.inputSchema()).containsKey("type").containsKey("properties");
    });
  }

  @Test
  void requiredArgumentsAreDeclaredInProperties() {
    for (ToolDefinition tool : registry.definitions()) {
      @SuppressWarnings("unchecked")
      Map<String, Object> properties = (Map<String, Object>) tool.inputSchema().get("properties");
      @SuppressWarnings("unchecked")
      List<String> required = (List<String>) tool.inputSchema().get("required");
      assertThat(properties.keySet())
          .as("tool %s declares required args it does not define", tool.name())
          .containsAll(required);
    }
  }

  @Test
  void toolNamesAreUnique() {
    List<String> names = registry.definitions().stream().map(ToolDefinition::name).toList();
    assertThat(names).doesNotHaveDuplicates();
  }

  @Test
  void anUnknownToolIsAnErrorResultRatherThanAnException() {
    // The caller is a model that should be able to read the message and correct itself.
    ToolResult result = registry.call("no_such_tool", Map.of());
    assertThat(result.error()).isTrue();
    assertThat(String.valueOf(result.content())).contains("no_such_tool");
  }

  @Test
  void runQueryWithoutAnIdExplainsHowToFindOne() {
    ToolResult result = registry.call("run_query", Map.of());
    assertThat(result.error()).isTrue();
    assertThat(String.valueOf(result.content())).contains("list_queries");
  }
}
