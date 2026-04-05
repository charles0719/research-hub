# any-agent 技能说明

## Plugin：agent-sdk-guide

### claude-agent-sdk

这是整个 Agent SDK 主题的入口 skill。

适合场景：

- 刚开始了解官方 Claude Agent SDK
- 想知道 `query()` 怎么用
- 想理解消息模型和基本调用流程

### agent-sdk-custom-tools

这个 skill 聚焦“如何给 Agent SDK 扩展工具能力”。

适合场景：

- `createSdkMcpServer()`
- `tool()`
- MCP server 接入
- 进程内工具和外部 MCP server 的区别

### agent-sdk-sessions

这个 skill 聚焦会话持续化和恢复能力。

适合场景：

- `resume`
- `continue`
- 列出历史 session
- fork session

### agent-sdk-advanced

这个 skill 聚焦生产级控制项。

适合场景：

- 权限控制
- hooks
- 子代理
- 结构化输出
- sandbox
- 预算控制

## Plugin：session-harvest

### session-harvest

这个 skill 的重点不是 SDK 本身，而是把一次高质量对话沉淀成可复用的 skill。

它更像一个“会话总结和知识固化工具”。

## Plugin：claude-plugin-marketplace-setup

### claude-plugin-marketplace-setup

这个 skill 的重点是指导用户正确搭建 Claude Code 的 marketplace 仓库。

适合场景：

- 创建 `marketplace.json`
- 创建 `plugin.json`
- 设计 plugin 分组方式
- 排查 schema 错误
