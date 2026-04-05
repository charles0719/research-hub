import {
  getSessionInfo,
  getSessionMessages,
  listSessions,
  query
} from "@anthropic-ai/claude-agent-sdk";
import { buildDemoOptions, readPrompt, streamConversation } from "./common.js";

const action = process.argv[2];

async function startSession() {
  const prompt = readPrompt(
    "请记住：我的研究主题是 any-agent，我重点关注 marketplace、skill 触发和 Agent SDK 的关系。然后用中文确认你记住了什么。",
    3
  );

  const conversation = query({
    prompt,
    options: buildDemoOptions({
      cwd: process.cwd(),
      permissionMode: "plan",
      maxTurns: 2,
      persistSession: true
    })
  });

  await streamConversation(conversation);
}

async function continueSession() {
  const prompt = readPrompt("我们刚才重点关注的 3 个主题分别是什么？", 3);

  const conversation = query({
    prompt,
    options: buildDemoOptions({
      cwd: process.cwd(),
      continue: true,
      permissionMode: "plan",
      maxTurns: 2
    })
  });

  await streamConversation(conversation);
}

async function listProjectSessions() {
  const sessions = await listSessions({
    dir: process.cwd(),
    limit: 10
  });

  if (sessions.length === 0) {
    console.log("No sessions found for this demo directory.");
    return;
  }

  console.log("Recent sessions:");
  for (const session of sessions) {
    console.log(
      [
        session.sessionId,
        session.lastModified,
        session.summary ?? "(no summary)",
        session.customTitle ?? ""
      ].join(" | ")
    );
  }
}

async function inspectSession() {
  const explicitSessionId = process.argv[3];
  let sessionId = explicitSessionId;

  if (!sessionId) {
    const sessions = await listSessions({
      dir: process.cwd(),
      limit: 1
    });
    sessionId = sessions[0]?.sessionId;
  }

  if (!sessionId) {
    console.log("No session available to inspect.");
    return;
  }

  const info = await getSessionInfo(sessionId, { dir: process.cwd() });
  const messages = await getSessionMessages(sessionId, { limit: 8 });

  console.log("Session info:");
  console.log(JSON.stringify(info, null, 2));
  console.log("\nRecent messages:");
  console.log(
    JSON.stringify(
      messages.map((message) => ({
        type: message.type,
        uuid: message.uuid,
        session_id: message.session_id,
        content: message.message?.content
      })),
      null,
      2
    )
  );
}

async function main() {
  if (action === "start") {
    await startSession();
    return;
  }

  if (action === "continue") {
    await continueSession();
    return;
  }

  if (action === "list") {
    await listProjectSessions();
    return;
  }

  if (action === "inspect") {
    await inspectSession();
    return;
  }

  console.log("Usage:");
  console.log("  node src/session-demo.js start [prompt]");
  console.log("  node src/session-demo.js continue [prompt]");
  console.log("  node src/session-demo.js list");
  console.log("  node src/session-demo.js inspect [sessionId]");
}

main().catch((error) => {
  console.error("session demo failed");
  console.error(error);
  process.exit(1);
});
