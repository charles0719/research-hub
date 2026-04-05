import { execFile } from "node:child_process";
import { promisify } from "node:util";
import { createSdkMcpServer, query, tool } from "@anthropic-ai/claude-agent-sdk";
import { z } from "zod/v4";
import { buildDemoOptions, readPrompt, streamConversation } from "./common.js";

const execFileAsync = promisify(execFile);
const REDIS_PREFIX = "agent-demo:task";

async function redisCli(args) {
  const { stdout } = await execFileAsync("redis-cli", args, {
    env: process.env
  });

  return stdout.trim();
}

async function seedDemoDataIfNeeded() {
  const existing = await redisCli(["EXISTS", `${REDIS_PREFIX}:1001`]);
  if (existing === "1") {
    return;
  }

  const demoTasks = [
    {
      id: "1001",
      title: "应收款重算任务",
      status: "pending",
      owner: "财务机器人",
      scene: "收费中心"
    },
    {
      id: "1002",
      title: "停车月卡同步",
      status: "running",
      owner: "停车服务",
      scene: "车场业务"
    },
    {
      id: "1003",
      title: "短信复核发送",
      status: "failed",
      owner: "消息中心",
      scene: "短信任务"
    }
  ];

  for (const task of demoTasks) {
    await redisCli([
      "HSET",
      `${REDIS_PREFIX}:${task.id}`,
      "id",
      task.id,
      "title",
      task.title,
      "status",
      task.status,
      "owner",
      task.owner,
      "scene",
      task.scene
    ]);
    await redisCli(["SADD", `${REDIS_PREFIX}:ids`, task.id]);
  }
}

async function readTask(taskId) {
  const raw = await redisCli(["HGETALL", `${REDIS_PREFIX}:${taskId}`]);
  if (!raw) {
    return null;
  }

  const fields = raw.split("\n");
  const task = {};
  for (let index = 0; index < fields.length; index += 2) {
    task[fields[index]] = fields[index + 1];
  }

  return task;
}

async function listTasks() {
  const idsRaw = await redisCli(["SMEMBERS", `${REDIS_PREFIX}:ids`]);
  const ids = idsRaw ? idsRaw.split("\n").filter(Boolean).sort() : [];
  const tasks = [];

  for (const id of ids) {
    const task = await readTask(id);
    if (task) {
      tasks.push(task);
    }
  }

  return tasks;
}

const manageTaskCache = tool(
  "manage-task-cache",
  "Read or update task state in the local Redis cache used by the demo business workflow.",
  {
    action: z
      .enum(["get_task", "list_tasks", "update_status"])
      .describe("The cache action to perform"),
    taskId: z.string().optional().describe("Task ID, required for get_task and update_status"),
    status: z
      .enum(["pending", "running", "done", "failed"])
      .optional()
      .describe("New status for update_status")
  },
  async ({ action, taskId, status }) => {
    await seedDemoDataIfNeeded();

    if (action === "get_task") {
      if (!taskId) {
        return {
          isError: true,
          content: [{ type: "text", text: "taskId is required for get_task" }]
        };
      }

      const task = await readTask(taskId);
      return {
        content: [{ type: "text", text: JSON.stringify({ task }, null, 2) }]
      };
    }

    if (action === "list_tasks") {
      const tasks = await listTasks();
      return {
        content: [{ type: "text", text: JSON.stringify({ count: tasks.length, tasks }, null, 2) }]
      };
    }

    if (!taskId || !status) {
      return {
        isError: true,
        content: [{ type: "text", text: "taskId and status are required for update_status" }]
      };
    }

    const task = await readTask(taskId);
    if (!task) {
      return {
        isError: true,
        content: [{ type: "text", text: `task ${taskId} does not exist` }]
      };
    }

    await redisCli(["HSET", `${REDIS_PREFIX}:${taskId}`, "status", status]);
    const updated = await readTask(taskId);
    console.log(`[tool] manage-task-cache update_status taskId=${taskId} status=${status}`);

    return {
      content: [{ type: "text", text: JSON.stringify({ updated }, null, 2) }]
    };
  }
);

const prompt = readPrompt(
  "请先调用 manage-task-cache 列出所有任务，然后告诉我哪些任务还没完成；如果有 failed 状态的任务，也请点出来。"
);

const conversation = query({
  prompt,
  options: buildDemoOptions({
    cwd: process.cwd(),
    maxTurns: 5,
    tools: [],
    allowedTools: ["mcp__redis-business-tools__manage-task-cache"],
    systemPrompt:
      "You are analyzing task state for a business system. Always call the manage-task-cache tool before answering questions about tasks.",
    mcpServers: {
      "redis-business-tools": createSdkMcpServer({
        name: "redis-business-tools",
        tools: [manageTaskCache]
      })
    }
  })
});

streamConversation(conversation).catch((error) => {
  console.error("redis-tool demo failed");
  console.error(error);
  process.exit(1);
});
