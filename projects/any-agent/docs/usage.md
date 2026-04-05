# any-agent 使用方式

## 安装

在 Claude Code 中添加 marketplace：

```bash
/plugin marketplace add https://github.com/yuebanhome/any-agent.git
```

## Skill 如何使用

主要有两种方式。

### 1. 自动触发

Claude 会根据每个 skill 中 `description` 的描述，判断你的问题是否命中对应场景。

例如：

- “怎么使用 Claude Agent SDK？”
- “如何给 Agent SDK 加 custom tools？”
- “怎么创建 Claude Code 插件市场？”

### 2. 手动调用

一般形式是：

```bash
/plugin-name:skill-name
```

例如：

```bash
/agent-sdk-guide:claude-agent-sdk
/agent-sdk-guide:agent-sdk-custom-tools
/agent-sdk-guide:agent-sdk-sessions
/agent-sdk-guide:agent-sdk-advanced
/session-harvest:session-harvest
/claude-plugin-marketplace-setup:claude-plugin-marketplace-setup
```

## 实际使用建议

推荐使用顺序：

1. 先从 `claude-agent-sdk` 开始建立整体认知
2. 研究工具扩展时，再进入 `agent-sdk-custom-tools`
3. 研究会话恢复时，使用 `agent-sdk-sessions`
4. 研究权限、hooks、沙盒、结构化输出时，使用 `agent-sdk-advanced`
5. 当一次对话值得沉淀时，使用 `session-harvest`
6. 当准备自己搭建 marketplace 时，使用 `claude-plugin-marketplace-setup`
