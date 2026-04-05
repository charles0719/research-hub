import { existsSync, readFileSync } from "node:fs";
import { homedir } from "node:os";
import { join } from "node:path";

export function readPrompt(defaultPrompt, startIndex = 2) {
  const prompt = process.argv.slice(startIndex).join(" ").trim();
  return prompt || defaultPrompt;
}

function loadClaudeSettingsEnv() {
  const settingsPath =
    process.env.CLAUDE_SETTINGS_PATH || join(homedir(), ".claude", "settings.json");

  if (!existsSync(settingsPath)) {
    return {};
  }

  try {
    const raw = readFileSync(settingsPath, "utf8");
    const parsed = JSON.parse(raw);
    const env = parsed?.env;

    if (!env || typeof env !== "object" || Array.isArray(env)) {
      return {};
    }

    return Object.fromEntries(
      Object.entries(env).filter(([, value]) => typeof value === "string" && value.length > 0)
    );
  } catch (error) {
    console.warn(`[demo] Failed to load Claude settings env: ${error.message}`);
    return {};
  }
}

function warnIfSettingsLookInvalid(settingsEnv) {
  const baseUrl = settingsEnv.ANTHROPIC_BASE_URL;
  const authToken = settingsEnv.ANTHROPIC_AUTH_TOKEN;

  if (typeof authToken !== "string" || authToken.length === 0) {
    return;
  }

  const tokenLooksLikeUrl = /^https?:\/\//i.test(authToken);
  const tokenMatchesBaseUrl = typeof baseUrl === "string" && authToken === baseUrl;

  if (tokenLooksLikeUrl || tokenMatchesBaseUrl) {
    console.warn(
      "[demo] ~/.claude/settings.json looks misconfigured: ANTHROPIC_AUTH_TOKEN currently looks like a URL. " +
        "ANTHROPIC_BASE_URL should be the gateway URL, while ANTHROPIC_AUTH_TOKEN should be the actual token."
    );
  }
}

export function buildDemoOptions(overrides = {}) {
  const settingsEnv = loadClaudeSettingsEnv();
  warnIfSettingsLookInvalid(settingsEnv);
  const env = {
    ...settingsEnv,
    ...process.env,
    CLAUDE_AGENT_SDK_CLIENT_APP:
      process.env.CLAUDE_AGENT_SDK_CLIENT_APP || "research-hub-any-agent-demo",
    ...overrides.env
  };

  const options = {
    ...overrides,
    env
  };

  const preferredExecutable =
    process.env.CLAUDE_CODE_EXECUTABLE ||
    ["/opt/homebrew/bin/claude", "/usr/local/bin/claude"].find((candidate) =>
      existsSync(candidate)
    );

  if (preferredExecutable) {
    options.pathToClaudeCodeExecutable = preferredExecutable;
  }

  if (!options.model && typeof settingsEnv.ANTHROPIC_MODEL === "string") {
    options.model = settingsEnv.ANTHROPIC_MODEL;
  }

  if (
    !options.fallbackModel &&
    typeof settingsEnv.ANTHROPIC_DEFAULT_SONNET_MODEL === "string" &&
    settingsEnv.ANTHROPIC_DEFAULT_SONNET_MODEL !== options.model
  ) {
    options.fallbackModel = settingsEnv.ANTHROPIC_DEFAULT_SONNET_MODEL;
  }

  return options;
}

export function printDivider(label) {
  console.log(`\n=== ${label} ===`);
}

export function printAssistantText(message) {
  if (message.type !== "assistant") {
    return;
  }

  for (const block of message.message.content) {
    if (block.type === "text") {
      console.log(block.text);
    }
  }
}

export function printSdkMessage(message) {
  if (message.type === "system" && message.subtype === "init") {
    printDivider("Session Init");
    console.log(`session_id: ${message.session_id}`);
    console.log(`cwd: ${message.cwd}`);
    console.log(`model: ${message.model}`);
    console.log(`permissionMode: ${message.permissionMode}`);
    const mcpServers = Array.isArray(message.mcp_servers) ? message.mcp_servers : [];
    console.log(`mcp servers: ${mcpServers.join(", ") || "(none)"}`);
    return;
  }

  if (message.type === "assistant") {
    printDivider("Assistant");
    printAssistantText(message);
    return;
  }

  if (message.type === "result") {
    printDivider("Result");

    if (message.subtype === "success") {
      console.log(`turns: ${message.num_turns}`);
      console.log(`cost usd: ${message.total_cost_usd}`);
      console.log(message.result);
    } else {
      console.log(`subtype: ${message.subtype}`);
      console.log(`errors: ${message.errors.join(" | ")}`);
    }
  }
}

export async function streamConversation(conversation) {
  for await (const message of conversation) {
    printSdkMessage(message);
  }
}
