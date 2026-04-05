import { createSdkMcpServer, query, tool } from "@anthropic-ai/claude-agent-sdk";
import { z } from "zod/v4";
import { buildDemoOptions, readPrompt, streamConversation } from "./common.js";

const demoUsers = [
  { id: 1, name: "Zhang San", role: "Backend Engineer", city: "Shanghai" },
  { id: 2, name: "Zhang Xiaoyu", role: "Product Manager", city: "Hangzhou" },
  { id: 3, name: "Bob Chen", role: "DevOps Engineer", city: "Shenzhen" },
  { id: 4, name: "Alice Wang", role: "Designer", city: "Beijing" }
];

const searchUsers = tool(
  "search-users",
  "Search demo users by keyword and optionally filter by role.",
  {
    keyword: z.string().describe("Keyword to match against the user name"),
    role: z.string().optional().describe("Optional role filter")
  },
  async ({ keyword, role }) => {
    const normalizedKeyword = keyword.trim().toLowerCase();
    const normalizedRole = role?.trim().toLowerCase();

    const matches = demoUsers.filter((user) => {
      const nameMatches = user.name.toLowerCase().includes(normalizedKeyword);
      const roleMatches = normalizedRole
        ? user.role.toLowerCase().includes(normalizedRole)
        : true;

      return nameMatches && roleMatches;
    });

    console.log(`[tool] search-users called with keyword="${keyword}" role="${role ?? ""}"`);

    return {
      content: [
        {
          type: "text",
          text: JSON.stringify(
            {
              keyword,
              role: role ?? null,
              count: matches.length,
              users: matches
            },
            null,
            2
          )
        }
      ]
    };
  }
);

const prompt = readPrompt(
  "请调用 search-users 工具，查找名字里包含 Zhang 的用户，并用中文总结结果。"
);

const conversation = query({
  prompt,
  options: buildDemoOptions({
    cwd: process.cwd(),
    maxTurns: 4,
    tools: [],
    systemPrompt: "When the user asks about demo users, call the MCP tool before answering.",
    mcpServers: {
      "demo-tools": createSdkMcpServer({
        name: "demo-tools",
        tools: [searchUsers]
      })
    }
  })
});

streamConversation(conversation).catch((error) => {
  console.error("custom-tool demo failed");
  console.error(error);
  process.exit(1);
});
