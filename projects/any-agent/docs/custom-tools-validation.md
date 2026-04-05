# custom-tools 验证方案

## 这次要验证什么

这次验证的目标不是证明 `any-agent` 自己提供了 custom tools 运行时。

真正要验证的是：

1. `any-agent` 里的 `agent-sdk-custom-tools` skill，讲解的用法是否能落到一个最小可运行 demo 上
2. Claude Agent SDK 是否能通过 `createSdkMcpServer()` + `tool()` 正常注册进程内工具
3. agent 是否真的会在对话过程中调用这个工具，而不是只做文本回答

## 为什么现在收进 `demos/`

一开始这个想法更像验证性实验，但现在仓库结构已经收得更轻，只保留真正会反复跑的内容。

所以这部分能力直接并入现有 playground，更方便以后持续使用：

```text
projects/any-agent/demos/claude-agent-sdk-playground/
```

也就是说，现在不再单独保留 `experiments/` 目录，而是把真正有价值的验证点沉淀到可直接运行的 demo 里。

## 当前实验目录

```text
projects/any-agent/demos/claude-agent-sdk-playground/
├── package.json
└── src/
    ├── basic-query.js
    ├── custom-tool.js
    ├── redis-tool.js
    ├── session-demo.js
    └── agent-mode.js
```

## demo 的设计思路

为了避免把实验复杂化，这里不接数据库、不接外部 HTTP，也不做真实业务系统集成。

如果只看 custom tools，最小验证主要落在这两个文件：

- `src/custom-tool.js`：内存用户查询，验证 `tool()` + `createSdkMcpServer()`
- `src/redis-tool.js`：接本机 Redis，验证更贴近业务的工具调用

这样做的好处是：

- 验证目标单一
- 没有额外基础设施依赖
- 很容易看清楚“工具有没有被调用”

## 你应该重点观察什么

运行这个 demo 时，重点观察下面几件事：

1. agent 是否能识别到 `search-users` 这个工具
2. agent 是否真的调用了工具
3. 工具返回的数据是否被 agent 正常吸收并组织成自然语言回答
4. 如果 prompt 改写，工具调用行为是否稳定

## 推荐的实验步骤

### 第一步：跑默认 prompt

先直接运行 demo，看默认问题下能不能触发工具调用。

### 第二步：改 prompt 再跑两次

建议至少试下面两种：

- “请使用 search-users 工具查找名字里包含 张 的用户”
- “帮我看看有没有叫 Bob 的用户，请先调用工具再回答”

如果写得更含糊，比如：

- “系统里有没有相关用户？”

就可以观察 agent 对工具选择是否还稳定。

### 第三步：记录实验结果

实验完成后，把结果补到本文件末尾，建议按下面结构记录：

```md
## 实验结果

### 环境
- Node.js 版本：
- SDK 版本：

### 结果
- 是否成功注册工具：
- 是否成功调用工具：
- 是否得到符合预期的回答：

### 观察
- 观察点 1
- 观察点 2

### 结论
- 结论 1
- 结论 2
```

## 这份文档现在怎么对应代码

你现在可以直接对应这几个入口：

1. `npm run tool` 对应 `src/custom-tool.js`
2. `npm run redis` 对应 `src/redis-tool.js`
3. 这份文档负责解释“为什么这样设计”，playground 负责真正可运行
