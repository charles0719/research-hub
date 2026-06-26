# research-hub

一个偏“研究 + 可运行 demo”的个人仓库，主要放两类内容：

- 我持续研究的项目与技术栈
- 我希望能随时跑起来、也能直接拿去讲的 demo

这个仓库不是单一业务项目，而是一个长期积累的技术练习场。内容会尽量遵循一个原则：

> 不只记录结论，还保留可运行的最小 demo，让每个主题都能被验证、演示和复用。

---

## 目前值得直接看的内容

### 1. LLM 应用 Demo

目录：

- [projects/llm-stack-demos](/Users/raven/Dev/Workspace/Personal/research-hub/projects/llm-stack-demos)

这一组 demo 是为了回答一个很现实的问题：

> 如果要证明自己“熟悉 Spring AI、LangChain4j 及 Python AI 开发生态，具备 LLM 应用工程落地经验”，到底应该做什么 demo，怎么讲，怎么展示给别人看？

这里我没有只放“聊天机器人”，而是按 3 种技术栈各做了一个更接近真实工程的 demo：

#### Spring AI

目录：

- [projects/llm-stack-demos/spring-ai-rag-demo](/Users/raven/Dev/Workspace/Personal/research-hub/projects/llm-stack-demos/spring-ai-rag-demo)

场景：

- 面试知识库问答
- 基于 Markdown 文档做 RAG 检索增强
- 通过 Spring Boot API 暴露问答接口

这个 demo 重点体现：

- Spring AI 在 Java / Spring Boot 体系内的集成方式
- Embedding、Vector Store、RAG 的基本链路
- 如何把知识库问答做成一个后端服务，而不是只停留在 prompt 级实验

#### LangChain4j

目录：

- [projects/llm-stack-demos/langchain4j-order-agent-demo](/Users/raven/Dev/Workspace/Personal/research-hub/projects/llm-stack-demos/langchain4j-order-agent-demo)

场景：

- 订单履约助手
- 通过 Tool Calling 查询订单、物流并创建工单

这个 demo 重点体现：

- LangChain4j 的 AI Service 风格开发
- Java 业务方法如何暴露给模型作为工具
- 模型如何从“直接回答”升级到“调用真实业务工具后再回答”

#### Python AI 生态

目录：

- [projects/llm-stack-demos/python-jd-resume-demo](/Users/raven/Dev/Workspace/Personal/research-hub/projects/llm-stack-demos/python-jd-resume-demo)

场景：

- JD / 简历匹配分析
- 结构化输出技能、匹配分和改写建议

这个 demo 重点体现：

- Python 生态里做 LLM 原型的速度和灵活性
- Structured Output 的实现方式
- 文本抽取和分析类应用怎么快速落地

#### 先看哪一个

如果你只想先看一个，我最推荐先看：

- [projects/llm-stack-demos/README.md](/Users/raven/Dev/Workspace/Personal/research-hub/projects/llm-stack-demos/README.md)

这份总览文档里有三套 demo 的对比、适用场景和推荐顺序。

---

### 2. any-agent 研究与 playground

目录：

- [projects/any-agent](/Users/raven/Dev/Workspace/Personal/research-hub/projects/any-agent)

这里放我对 `any-agent` 的理解笔记和可运行 playground。

当前最常用的 demo：

- [projects/any-agent/demos/claude-agent-sdk-playground](/Users/raven/Dev/Workspace/Personal/research-hub/projects/any-agent/demos/claude-agent-sdk-playground)

更适合这些场景：

- 快速验证 query / session / 自定义工具
- 理解 agent SDK 类项目的使用方式
- 做最小可运行实验

---

### 3. Java 面试 / 知识点验证 demo

目录：

- [interview/java](/Users/raven/Dev/Workspace/Personal/research-hub/interview/java)

这里放更偏底层知识点和面试验证的小 demo，例如：

- `threadlocal-demo`
- `reference-demo`
- `java-features-demo`
- `thread-print-demo`

这些内容更偏：

- 面试题验证
- 并发 / JVM / Java 基础演示
- 最小代码实验

---

## 仓库结构

```text
research-hub/
├── README.md
├── projects/
│   ├── any-agent/
│   └── llm-stack-demos/
└── interview/
    └── java/
```

---

## 为什么这样组织

我希望这个仓库同时满足 3 个目标：

1. 自己平时研究技术时，有地方沉淀可运行结果
2. 需要面试、写简历、写技术总结时，能快速找到能讲的 demo
3. 上传到 GitHub 后，别人点进来能直接理解“这里有什么、先看哪里、这些 demo 是做什么的”

所以这里的内容会尽量避免两种问题：

- 只有笔记，没有能跑的东西
- 只有代码，没有解释为什么这样做

---

## 如果你是第一次看这个仓库

推荐顺序：

1. 先看 [projects/llm-stack-demos/README.md](/Users/raven/Dev/Workspace/Personal/research-hub/projects/llm-stack-demos/README.md)
2. 再按兴趣选择：
   - 想看 Java + RAG：`spring-ai-rag-demo`
   - 想看 Java + Agent / Tool Calling：`langchain4j-order-agent-demo`
   - 想看 Python LLM 原型：`python-jd-resume-demo`
3. 如果你对 agent SDK 方向感兴趣，再看 `projects/any-agent`
4. 如果你对 Java 面试知识点 demo 感兴趣，再看 `interview/java`

---

## 当前原则

- 优先保留“能直接跑”的 demo
- 每个 demo 尽量配 README，而不是只放代码
- 能讲清楚“业务场景 + 核心逻辑 + 技术实现”，而不是只展示语法
- 面向 GitHub 展示时，尽量让陌生读者也能看懂入口
