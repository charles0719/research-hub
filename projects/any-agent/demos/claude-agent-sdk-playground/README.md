# Claude Agent SDK Playground

这个 demo 放在 `research-hub/projects/any-agent/demos` 下，目的是把 `any-agent-main` 里关于 Claude Agent SDK 的几条核心说明，落成一个能直接运行和观察的 playground。

它不去“运行 any-agent 仓库本身”，因为 `any-agent-main` 本质上是一个 marketplace / plugin / skill 仓库，不是传统应用。这个 demo 验证的是：

1. `query()` 怎么跑起来
2. `createSdkMcpServer()` + `tool()` 怎么接 custom tool
3. session 怎么开始、继续、查看
4. 自定义 `agent` 定义最小能怎么写

## 文件结构

```text
claude-agent-sdk-playground/
├── README.md
├── package.json
└── src/
    ├── agent-mode.js
    ├── basic-query.js
    ├── common.js
    ├── custom-tool.js
    └── session-demo.js
```

## 运行前准备

### 1. Node 版本

要求 Node.js 18+。

### 2. 安装依赖

```bash
cd /Users/raven/Dev/Workspace/Personal/research-hub/projects/any-agent/demos/claude-agent-sdk-playground
npm install
```

### 3. 配置认证

如果你走 API key，最常见的是：

```bash
export ANTHROPIC_API_KEY=your_key_here
```

如果你本机已经完成 Claude Code/Agent SDK 相关登录，也可以直接复用本地认证状态。

如果你走的是 cloud 兼容网关，最容易填错的是这两个值：

- `ANTHROPIC_BASE_URL`：你的网关 URL
- `ANTHROPIC_AUTH_TOKEN`：真正的访问 token，不是 URL

如果把 URL 同时填到了这两个字段，demo 通常能初始化 session，但实际回答会异常或卡住。

### 4. Claude Code 可执行文件

这个 demo 会优先尝试使用本机已有的 Claude Code 可执行文件。

如果你本机不是 Homebrew 默认路径，或者仍然报找不到可执行文件，可以手动指定：

```bash
export CLAUDE_CODE_EXECUTABLE=/absolute/path/to/claude
```

### 5. 自动读取本机 Claude 配置

这个 demo 默认会读取：

```bash
~/.claude/settings.json
```

并把其中的 `env` 配置注入给 Claude Agent SDK 子进程，所以如果你平时 `claude` CLI 就是靠这里面的 `ANTHROPIC_BASE_URL`、`ANTHROPIC_AUTH_TOKEN`、`ANTHROPIC_MODEL` 跑起来的，demo 也会尽量沿用同一套配置。

如果你想临时改成另一份配置文件，可以这样：

```bash
export CLAUDE_SETTINGS_PATH=/absolute/path/to/settings.json
```

## Demo 列表

| 命令 | 作用 |
|---|---|
| `npm run basic` | 最小 `query()` 调用，先感受 SDK 消息流 |
| `npm run tool` | 让 agent 调用进程内 MCP tool |
| `npm run redis` | 让 agent 调用本机 Redis 上的业务状态 tool |
| `npm run agent` | 演示自定义 `agent` 定义 |
| `npm run session:start` | 创建一个可持久化的 session |
| `npm run session:continue` | 在当前 demo 目录继续最近一次 session |
| `npm run session:list` | 列出当前目录下最近的 session |
| `npm run session:inspect` | 查看最近一个 session 的元信息和消息 |

## 推荐执行顺序

### 1. 先跑基础 query

```bash
npm run basic
```

你应该重点看：

- 有没有输出 `session_id`
- assistant 内容是不是通过消息流打印出来
- 最终 `result` 是否正常收尾

### 2. 再跑 custom tool

```bash
npm run tool
```

你应该重点看：

- 终端里会不会出现 `[tool] search-users called ...`
- assistant 最终回答有没有用到工具返回的数据

也可以传自定义 prompt：

```bash
node src/custom-tool.js "请调用 search-users 工具，查找名字里包含 Alice 的用户，并用中文说明。"
```

### 3. 再看 agent 定义

```bash
npm run agent
```

这个例子不是为了做复杂子代理编排，而是为了验证：

- `agents` 选项怎么写
- `agent` 选项怎么指定主线程 agent
- 不依赖文件系统工具时，最小 agent 配置是什么样

### 4. 看一个更贴近业务的 Redis tool

```bash
npm run redis
```

这个 demo 会在本机 Redis 中使用独立命名空间：

```text
agent-demo:task:*
```

它会自动准备几条 demo 任务数据，然后让 agent 通过 custom tool 去：

- 列出任务
- 读取单个任务
- 更新任务状态

这类能力更接近真实业务里的“查缓存状态、看任务进度、给运营/客服/财务总结结果”。

你也可以自己试：

```bash
node src/redis-tool.js "请调用 manage-task-cache，把 1003 的状态改成 done，然后告诉我现在还有哪些任务未完成。"
```

### 5. 最后跑 session

先创建：

```bash
npm run session:start
```

再继续：

```bash
npm run session:continue
```

查看当前目录的 session：

```bash
npm run session:list
```

查看最近一次 session 详情：

```bash
npm run session:inspect
```

## 这个 demo 和 any-agent-main 的对应关系

这个 playground 主要对应上游这些内容：

- `plugins/agent-sdk-guide/skills/claude-agent-sdk/SKILL.md`
- `plugins/agent-sdk-guide/skills/agent-sdk-custom-tools/SKILL.md`
- `plugins/agent-sdk-guide/skills/agent-sdk-sessions/SKILL.md`
- `plugins/agent-sdk-guide/skills/agent-sdk-advanced/SKILL.md`

可以把它理解成：

- `any-agent-main` 负责教你“应该怎么理解和组织知识”
- 这个 demo 负责验证“这些说明到底能不能跑起来”

## 适合继续扩展的方向

如果这个 playground 跑顺了，下一步最值得扩的通常是：

1. 把内存用户查询改成真实数据库或 HTTP API
2. 把进程内 tool 改成独立 MCP server
3. 增加 structured output 和权限控制示例
4. 增加读取 `any-agent-main` 本地仓库的研究型 prompt
