# LLM Stack Demos

这一组 demo 用来回答一个很具体的问题：

> 如果要证明自己“熟悉 Spring AI、LangChain4j 及 Python AI 开发生态，具备 LLM 应用工程落地经验”，到底应该做什么 demo，分别怎么讲？

这里我没有做 3 个“只是能聊天”的玩具，而是给 3 种技术栈各放了一个更贴近真实业务的 demo：

1. `spring-ai-rag-demo`
2. `langchain4j-order-agent-demo`
3. `python-jd-resume-demo`

---

## 1. 三个 demo 分别解决什么问题

### A. Spring AI Demo

目录：

- `spring-ai-rag-demo`

核心场景：

- 做一个“面试知识库问答助手”
- 把 Markdown 文档加载进向量检索
- 用户提问后先检索相关片段，再基于上下文生成答案

它证明的能力：

- Spring Boot 体系内接入 LLM
- Embedding + Vector Store + RAG
- REST API 封装
- 文档分片、召回、上下文拼接

更适合什么简历：

- Java 后端
- 企业应用
- 知识库问答
- 内部 Copilot / 智能客服

### B. LangChain4j Demo

目录：

- `langchain4j-order-agent-demo`

核心场景：

- 做一个“订单履约助手”
- 用户自然语言提问
- 模型自动决定是否调用订单、物流、工单等工具

它证明的能力：

- Tool Calling
- Java Agent / AI Service 风格封装
- 业务 API 编排
- 多工具协作

更适合什么简历：

- Java 后端
- 智能运营助手
- 智能客服助手
- 订单 / 工单 / CRM / ERP 助手

### C. Python Demo

目录：

- `python-jd-resume-demo`

核心场景：

- 做一个“JD / 简历匹配分析器”
- 读取 JD 和简历文本
- 提取技能、评估匹配度、输出改写建议

它证明的能力：

- Python LLM 原型开发
- Structured Output
- Prompt Engineering
- 文本抽取和分析

更适合什么简历：

- AI 应用原型
- 智能简历分析
- 招聘匹配
- 内容抽取类业务

---

## 2. 这三个 demo 怎么比较

| 维度 | Spring AI RAG | LangChain4j Order Agent | Python JD / Resume |
|---|---|---|---|
| 技术栈贴合 Java 后端 | 很强 | 很强 | 一般 |
| 上手速度 | 中 | 中 | 最快 |
| 业务真实感 | 很强 | 很强 | 中 |
| 面试时可讲深度 | 很强 | 很强 | 中 |
| 能体现 Agent / Tool Calling | 中 | 最强 | 中 |
| 能体现 RAG | 最强 | 弱 | 弱 |
| 能体现结构化输出 | 中 | 中 | 很强 |
| 适合先做哪一个 | 最推荐 | 第二推荐 | 第三推荐 |

---

## 3. 哪一个 demo 最值得优先做

### 如果你只能做一个

我最推荐：

- `spring-ai-rag-demo`

原因：

1. 它最贴近 Java 后端岗位。
2. 它最容易和“企业落地”挂钩。
3. 它能自然带出：
   - 向量检索
   - 文档切分
   - Prompt 组装
   - 模型调用
   - 接口封装
   - 召回质量优化
4. 面试官最容易把它理解成“不是玩具，是个可以继续做成内部知识库产品的基础版”。

### 如果你想突出 Agent / 工具编排

最推荐：

- `langchain4j-order-agent-demo`

原因：

- 这类 demo 更像“让模型接入业务系统”
- 更适合讲“模型不是直接回答，而是先调工具查真实数据，再组织答案”
- 很符合现在招聘里常说的 agent / tool use / workflow

### 如果你想快速出效果

最推荐：

- `python-jd-resume-demo`

原因：

- 写起来最快
- 结果最直观
- 你现在就能拿自己的 JD 和简历去跑

---

## 4. 我建议你的 demo 讲法

如果你要把它写进简历，不要写成：

- 做了一个聊天机器人

更好的写法是：

### Spring AI RAG Demo

> 基于 Spring AI 搭建面试知识库问答 demo，完成 Markdown 文档加载、Embedding 向量化、相似片段召回、上下文增强生成和 REST API 封装，验证了 Java 技术栈下 RAG 应用的基础工程链路。

### LangChain4j Order Agent Demo

> 基于 LangChain4j 实现订单履约助手 demo，通过 tool calling 让模型按需调用订单查询、物流查询和工单创建等业务工具，验证了大模型与后端业务接口协同编排的落地方式。

### Python JD / Resume Demo

> 基于 Python + OpenAI SDK 实现 JD / 简历匹配分析 demo，完成技能抽取、差距分析、结构化结果输出和优化建议生成，验证了结构化输出和文本分析类 LLM 应用能力。

---

## 5. 目录结构

```text
llm-stack-demos/
├── README.md
├── spring-ai-rag-demo/
├── langchain4j-order-agent-demo/
└── python-jd-resume-demo/
```

---

## 6. 使用建议

推荐顺序：

1. 先看本文件，理解为什么是这 3 个 demo
2. 先跑 `python-jd-resume-demo`，最快看到结果
3. 再看 `spring-ai-rag-demo`，这是最值得放进 Java 简历的
4. 最后看 `langchain4j-order-agent-demo`，它最适合讲 agent/tool calling

如果你后面想继续扩展，我建议路线是：

1. 先把 `spring-ai-rag-demo` 做成可上传文档的版本
2. 再把 `langchain4j-order-agent-demo` 加上 memory 和工单闭环
3. 最后把 Python demo 改成批量分析多个 JD 并生成匹配报告
