import { query } from "@anthropic-ai/claude-agent-sdk";
import { buildDemoOptions, readPrompt, streamConversation } from "./common.js";

const prompt = readPrompt(
  "请直接用中文回答：Claude Agent SDK 的 query() 是做什么的？限制在 3 句话内，不要先说你要搜索或研究。"
);

const conversation = query({
  prompt,
  options: buildDemoOptions({
    cwd: process.cwd(),
    permissionMode: "plan",
    maxTurns: 3,
    systemPrompt: "Answer directly in Chinese. Do not say you will search, research, or look things up first.",
    promptSuggestions: true
  })
});

streamConversation(conversation).catch((error) => {
  console.error("basic-query demo failed");
  console.error(error);
  process.exit(1);
});
