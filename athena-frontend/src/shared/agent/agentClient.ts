export interface AgentStatus {
  toolCount: number;
  chatEnabled: boolean;
  model: string;
}

export interface AgentSkill {
  name: string;
  description: string;
}

export interface ToolCall {
  name: string;
  input: Record<string, unknown>;
}

const AGENT_ROOT = "/agent";

export async function getStatus(): Promise<AgentStatus> {
  const response = await fetch(`${AGENT_ROOT}/status`, { headers: { Accept: "application/json" } });
  if (!response.ok) throw new Error(`Agent unavailable (${response.status})`);
  return response.json();
}

export async function listSkills(): Promise<AgentSkill[]> {
  const response = await fetch(`${AGENT_ROOT}/chat/skills`, { headers: { Accept: "application/json" } });
  if (!response.ok) return [];
  return response.json();
}

export interface StreamHandlers {
  onText: (text: string) => void;
  onTool: (call: ToolCall) => void;
  onError: (message: string) => void;
  onDone: () => void;
}

/**
 * Read the SSE turn.
 *
 * fetch + ReadableStream rather than EventSource, because the turn is a POST carrying the
 * message and history, and EventSource can only issue a GET.
 */
export async function askAgent(
  body: { message: string; skill?: string | null; history?: unknown[] },
  handlers: StreamHandlers,
  signal?: AbortSignal,
): Promise<void> {
  const response = await fetch(`${AGENT_ROOT}/chat`, {
    method: "POST",
    headers: { "Content-Type": "application/json", Accept: "text/event-stream" },
    body: JSON.stringify(body),
    signal,
  });

  if (!response.ok || !response.body) {
    handlers.onError(`Agent request failed (${response.status})`);
    handlers.onDone();
    return;
  }

  const reader = response.body.getReader();
  const decoder = new TextDecoder();
  let buffer = "";

  while (true) {
    const { done, value } = await reader.read();
    if (done) break;
    buffer += decoder.decode(value, { stream: true });

    // SSE frames are separated by a blank line; anything after the last one is a partial frame.
    const frames = buffer.split("\n\n");
    buffer = frames.pop() ?? "";

    for (const frame of frames) {
      let event = "message";
      const dataLines: string[] = [];
      for (const line of frame.split("\n")) {
        if (line.startsWith("event:")) event = line.slice(6).trim();
        else if (line.startsWith("data:")) dataLines.push(line.slice(5).trim());
      }
      if (dataLines.length === 0) continue;
      let payload: Record<string, unknown> = {};
      try {
        payload = JSON.parse(dataLines.join("\n"));
      } catch {
        continue;
      }
      if (event === "text") handlers.onText(String(payload.text ?? ""));
      else if (event === "tool") handlers.onTool(payload as unknown as ToolCall);
      else if (event === "error") handlers.onError(String(payload.message ?? "Unknown error"));
      else if (event === "done") handlers.onDone();
    }
  }
  handlers.onDone();
}
