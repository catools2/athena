import { useEffect, useRef, useState } from "react";
import { Bot, Loader2, Send, Wrench } from "lucide-react";
import {
  askAgent, getStatus, listSkills,
  type AgentSkill, type AgentStatus, type ToolCall,
} from "../../../shared/agent/agentClient";

interface Turn {
  role: "user" | "assistant";
  text: string;
  tools: ToolCall[];
}

/**
 * Chat over Athena's own data.
 *
 * Tool calls are shown as they happen rather than hidden. An answer about a release is only
 * trustworthy if you can see which query produced it, and that provenance is the difference
 * between this and a chatbot guessing.
 */
export function AgentPage() {
  const [status, setStatus] = useState<AgentStatus | null>(null);
  const [statusError, setStatusError] = useState<string | null>(null);
  const [skills, setSkills] = useState<AgentSkill[]>([]);
  const [skill, setSkill] = useState("");
  const [input, setInput] = useState("");
  const [turns, setTurns] = useState<Turn[]>([]);
  const [busy, setBusy] = useState(false);
  const endRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    getStatus().then(setStatus).catch((e: Error) => setStatusError(e.message));
    listSkills().then(setSkills).catch(() => setSkills([]));
  }, []);

  useEffect(() => {
    // Only once there is a conversation. Running this on mount scrolls the whole page down
    // to an element that is already in view, which reads as the app loading half-scrolled.
    if (turns.length === 0) return;
    endRef.current?.scrollIntoView({ behavior: "smooth", block: "nearest" });
  }, [turns, busy]);

  async function send() {
    const message = input.trim();
    if (!message || busy) return;
    setInput("");
    setBusy(true);
    setTurns((t) => [...t, { role: "user", text: message, tools: [] },
                            { role: "assistant", text: "", tools: [] }]);

    await askAgent(
      { message, skill: skill || null },
      {
        onText: (text) => setTurns((t) => patchLast(t, (last) => ({ ...last, text: last.text + text }))),
        onTool: (call) => setTurns((t) => patchLast(t, (last) => ({ ...last, tools: [...last.tools, call] }))),
        onError: (msg) => setTurns((t) => patchLast(t, (last) => ({
          ...last, text: `${last.text}\n\n**${msg}**`,
        }))),
        onDone: () => setBusy(false),
      },
    ).catch((e: Error) => {
      setTurns((t) => patchLast(t, (last) => ({ ...last, text: `${last.text}\n\n**${e.message}**` })));
      setBusy(false);
    });
  }

  if (statusError) {
    return (
      <div className="animate-fade-in p-6">
        <h1 className="font-display text-2xl font-semibold text-ink">Agent</h1>
        <p className="card mt-3 p-4 text-sm text-ink-muted">
          The agent service is not reachable. {statusError}
        </p>
      </div>
    );
  }

  if (status && !status.chatEnabled) {
    return (
      <div className="animate-fade-in p-6">
        <h1 className="font-display text-2xl font-semibold text-ink">Agent</h1>
        <div className="card mt-3 max-w-[70ch] p-4 text-sm text-ink-muted">
          <p className="mb-2">
            Chat is switched off, but the {status.toolCount} Athena tools are live and callable at{" "}
            <code className="text-ink">/agent/tools</code> — an MCP client or Claude Code can drive
            Athena without any model credentials here.
          </p>
          <p>
            To enable chat, set <code className="text-ink">ATHENA_AGENT_CHAT_ENABLED=true</code> and{" "}
            <code className="text-ink">ANTHROPIC_API_KEY</code> on the agent service.
          </p>
        </div>
      </div>
    );
  }

  return (
    <div className="animate-fade-in flex min-h-[75vh] flex-col p-6">
      <header className="mb-4">
        <p className="eyebrow-label">Agent</p>
        <h1 className="font-display text-2xl font-semibold text-ink">Ask Athena</h1>
        <p className="mt-1 max-w-[70ch] text-sm text-ink-muted">
          Answers come from the same queries the dashboards run. Tool calls are shown so you can
          check where a number came from.
        </p>
      </header>

      <div className="mb-3 flex flex-wrap items-end gap-3">
        <label className="flex flex-col gap-1">
          <span className="eyebrow-label">Method</span>
          <select
            value={skill}
            onChange={(e) => setSkill(e.target.value)}
            className="min-w-[14rem] rounded-md border border-line bg-surface-muted/80 px-2 py-1 text-[11px] text-ink"
          >
            <option value="">No specific method</option>
            {skills.map((s) => (
              <option key={s.name} value={s.name}>{s.name}</option>
            ))}
          </select>
        </label>
        {status ? (
          <span className="text-[10px] text-ink-muted">
            {status.toolCount} tools · {status.model}
          </span>
        ) : null}
      </div>

      <div className="card mb-3 min-h-[18rem] flex-1 overflow-auto p-4">
        {turns.length === 0 ? (
          <p className="text-xs text-ink-muted">
            Try: “which cycles failed in the last release?” or “what changed before the timing
            regression yesterday?”
          </p>
        ) : (
          <ul className="space-y-4">
            {turns.map((turn, i) => (
              <li key={i}>
                <p className="eyebrow-label mb-1 flex items-center gap-1.5">
                  {turn.role === "assistant" ? <Bot className="h-3 w-3" aria-hidden="true" /> : null}
                  {turn.role === "user" ? "You" : "Athena"}
                </p>
                {turn.tools.map((tool, j) => (
                  <p key={j} className="mb-1 flex items-center gap-1.5 text-[11px] text-accent">
                    <Wrench className="h-3 w-3" aria-hidden="true" />
                    <span className="font-medium">{tool.name}</span>
                    <span className="truncate text-ink-muted">{JSON.stringify(tool.input)}</span>
                  </p>
                ))}
                <p className="whitespace-pre-wrap text-sm leading-relaxed text-ink">{turn.text}</p>
              </li>
            ))}
          </ul>
        )}
        <div ref={endRef} />
      </div>

      <div className="flex gap-2">
        <input
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={(e) => { if (e.key === "Enter" && !e.shiftKey) { e.preventDefault(); void send(); } }}
          placeholder="Ask about cycles, commits, pipelines, pods or timings…"
          className="flex-1 rounded-md border border-line bg-surface-muted/80 px-3 py-2 text-sm text-ink placeholder:text-ink-muted/60"
        />
        <button
          type="button"
          onClick={() => void send()}
          disabled={busy || !input.trim()}
          className="flex items-center gap-1.5 rounded-md border border-line bg-accent/20 px-3 py-2 text-sm text-ink disabled:opacity-40"
        >
          {busy ? <Loader2 className="h-4 w-4 animate-spin" aria-hidden="true" /> : <Send className="h-4 w-4" aria-hidden="true" />}
          Ask
        </button>
      </div>
    </div>
  );
}

function patchLast(turns: Turn[], patch: (last: Turn) => Turn): Turn[] {
  if (turns.length === 0) return turns;
  const copy = [...turns];
  copy[copy.length - 1] = patch(copy[copy.length - 1]);
  return copy;
}
