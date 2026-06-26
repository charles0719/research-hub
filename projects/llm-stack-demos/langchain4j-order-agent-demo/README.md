# LangChain4j Order Agent Demo

## 1. 这个 demo 做什么

这是一个基于 `LangChain4j` 的订单履约助手 demo。

用户可以直接问：

- 订单 `1003` 为什么没有发货？
- 订单 `2001` 现在在哪？
- 如果是库存不足，帮我建一个工单。

模型不会只靠猜，而是会：

1. 识别问题意图
2. 选择合适的工具
3. 调用订单、物流、工单方法
4. 基于工具结果组织自然语言答案

这个 demo 的核心价值在于：

> 它证明模型已经不只是“聊天”，而是能通过 tool calling 接入真实业务接口。

---

## 2. 为什么选这个 demo

这类 demo 很适合写“LLM 应用工程落地经验”，因为它很接近真实企业应用：

- 智能客服助手
- 订单查询助手
- 运营助手
- 工单辅助处理
- 内部流程问答

---

## 3. 核心业务逻辑

用户输入：

> 帮我看下订单 1003 为什么没发货，如果是库存不足就给运营建单。

内部链路：

1. 模型解析出这是“订单履约查询”问题
2. 先调用 `queryOrder("1003")`
3. 如果状态需要补充，再调用 `queryLogistics("1003")`
4. 如果订单因为库存不足阻塞，再调用 `createTicket(...)`
5. 最终给用户返回一个自然语言解释

---

## 4. 核心技术点

- `AiServices`
- `@Tool`
- `OpenAiChatModel`
- Java 业务方法直接暴露为工具
- 工具结果回填给模型继续推理

如果后面要扩展，最自然的方向是：

- 加 memory
- 加更多业务工具
- 加工单状态回查
- 加权限控制
- 加多轮会话

---

## 5. 项目结构

```text
langchain4j-order-agent-demo/
├── pom.xml
├── README.md
└── src/main/java/com/raven/demo/langchain4j/
    ├── OrderAgentDemoApplication.java
    ├── OrderAssistant.java
    ├── OrderTools.java
    └── FakeOrderDataStore.java
```

---

## 6. 运行前准备

你需要准备：

- Java 21
- Maven 3.9+
- 一个可用的 OpenAI 兼容模型 API Key

环境变量：

```bash
export OPENAI_API_KEY=your_api_key
export OPENAI_CHAT_MODEL=gpt-4o-mini
```

---

## 7. 启动方式

```bash
cd /Users/raven/Dev/Workspace/Personal/research-hub/projects/llm-stack-demos/langchain4j-order-agent-demo
mvn exec:java -Dexec.mainClass=com.raven.demo.langchain4j.OrderAgentDemoApplication
```

---

## 8. 面试里怎么讲

可以这样说：

> 我做过一个基于 LangChain4j 的订单履约助手 demo，重点是 tool calling，而不是普通聊天。模型接到用户问题后不会直接猜答案，而是先按需调用订单查询、物流查询和工单创建等工具，再基于工具返回的真实数据组织答案。这个 demo 让我验证了 Java 技术栈下 agent/tool use 的基础工程链路，包括工具暴露、模型编排和结果回填。如果做成生产版，我会增加鉴权、会话记忆、审计日志和失败重试。

---

## 9. 这个 demo 最适合写进简历的点

- Tool Calling
- Agent 编排
- 大模型与业务系统接口打通
- 比“纯问答”更贴近真实企业系统
