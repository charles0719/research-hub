# Spring AI RAG Demo

## 1. 这个 demo 做什么

这是一个基于 `Spring Boot + Spring AI` 的面试知识库问答 demo。

核心业务是：

1. 启动时加载本地 Markdown 文档
2. 把文档放入向量存储
3. 用户提问时先做相似片段检索
4. 再把检索结果作为上下文拼进 prompt
5. 最后由大模型生成答案并返回引用来源

这个 demo 的目标不是证明“模型会聊天”，而是证明：

> 我能在 Java Spring 体系里，把一个典型 RAG 应用从文档加载、向量召回到接口返回完整串起来。

---

## 2. 为什么选这个 demo

对 Java 后端来说，这个 demo 很值钱，因为它天然能挂到很多真实业务：

- 内部知识库问答
- 智能客服 FAQ
- 面试题库助手
- 接口文档 Copilot
- 运维 SOP 问答

如果你简历里要写“LLM 应用工程落地经验”，这个 demo 是最稳的起点。

---

## 3. 核心业务逻辑

用户问：

> MySQL 幻读为什么会出现？Redis 热 Key 应该怎么处理？

系统内部流程：

1. 接收问题
2. 用 embedding 模型把问题向量化
3. 从 vector store 中召回最相近的文档片段
4. 把片段拼到上下文里
5. 要求模型“严格基于上下文回答，答不出来就说上下文不足”
6. 返回答案和来源

---

## 4. 核心技术点

- `Spring AI ChatClient`
- `EmbeddingModel`
- `SimpleVectorStore`
- 本地 Markdown 文档加载
- RAG 检索增强
- REST API 封装

如果后面你要继续升级，最自然的方向是：

- 替换成 `PGVector`
- 增加文档上传
- 做 chunking 优化
- 增加引用片段高亮
- 增加 evaluator / recall 评估

---

## 5. 项目结构

```text
spring-ai-rag-demo/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/com/raven/demo/springai/
        │   ├── SpringAiRagDemoApplication.java
        │   ├── RagController.java
        │   └── RagService.java
        └── resources/
            ├── application.yml
            └── docs/
                ├── mysql-phantom-read.md
                └── redis-hot-key.md
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
export OPENAI_EMBEDDING_MODEL=text-embedding-3-small
```

---

## 7. 启动方式

```bash
cd /Users/raven/Dev/Workspace/Personal/research-hub/projects/llm-stack-demos/spring-ai-rag-demo
mvn spring-boot:run
```

测试请求：

```bash
curl -X POST http://localhost:8080/api/rag/ask \
  -H 'Content-Type: application/json' \
  -d '{"question":"MySQL 幻读为什么会出现，InnoDB 是怎么处理的？"}'
```

---

## 8. 面试里怎么讲

可以这样说：

> 我做过一个基于 Spring AI 的 RAG demo，场景是面试知识库问答。启动时把本地 Markdown 文档加载进向量存储，用户提问后先做 embedding 检索，再把召回片段作为上下文增强给大模型生成答案，同时返回引用来源。这个 demo 重点不在聊天，而在 Java 技术栈下把知识库问答的工程主链路跑通，包括文档加载、向量检索、prompt 组装和接口封装。如果继续做成生产版，我会把简单向量存储替换成 PGVector 或 Elasticsearch，并增加 chunking、召回评估和文档上传能力。

---

## 9. 这个 demo 最适合写进简历的点

- RAG 基础链路
- Spring Boot / Spring AI 集成
- 面向企业知识库场景
- 比单纯聊天机器人更像实际系统
