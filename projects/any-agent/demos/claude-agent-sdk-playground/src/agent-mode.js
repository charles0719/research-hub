import { query } from "@anthropic-ai/claude-agent-sdk";
import { buildDemoOptions, readPrompt, streamConversation } from "./common.js";

const prompt = readPrompt(
  "请从研究者视角介绍 any-agent 值得重点研究的 3 个方向，每个方向 2 句话。"
);

const conversation = query({
  prompt,
  options: buildDemoOptions({
    cwd: process.cwd(),
    permissionMode: "plan",
    maxTurns: 1,
    tools: [],
    agent: "researcher",
    agents: {
      researcher: {
        description: "Explains Claude Agent SDK topics in concise Chinese.",
        prompt:
          "You are a research-oriented assistant. Explain concepts clearly, stay concrete, and keep the answer short.",
        tools: []
      }
    }
  })
});

streamConversation(conversation).catch((error) => {
  console.error("agent-mode demo failed");
  console.error(error);
  process.exit(1);
});
