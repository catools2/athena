package org.catools.athena.agent.mcp;

/**
 * The outcome of a tool call.
 *
 * <p>{@code error} is a normal outcome, not an exception: a model that asked for a query that
 * does not exist should be told so and given the chance to correct itself, which is far more
 * useful than the turn failing.
 */
public record ToolResult(Object content, boolean error) {

  public static ToolResult ok(Object content) {
    return new ToolResult(content, false);
  }

  public static ToolResult failed(String message) {
    return new ToolResult(message, true);
  }
}
