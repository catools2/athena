package org.catools.athena.agent.mcp;

import java.util.Map;

/**
 * One callable tool, in the shape both MCP and the Anthropic Messages API expect.
 *
 * @param name        stable tool name
 * @param description what it does and when to reach for it - this is the only thing the model
 *                    sees when deciding whether to call it, so it is documentation for a reader
 *                    who cannot ask a follow-up question
 * @param inputSchema JSON Schema for the arguments
 */
public record ToolDefinition(String name, String description, Map<String, Object> inputSchema) {}
