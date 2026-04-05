# research-hub

这个仓库是我的个人练习场，用来放两类东西：

- 我想研究的 GitHub 项目
- 我想随时拿来跑的面试题 / 小 demo

所以顶层结构不围着某一个项目展开，而是围着“项目研究”和“练习验证”这两件事展开。

## 目录

```text
research-hub/
├── README.md
├── projects/
│   └── any-agent/
│       ├── README.md
│       ├── docs/
│       └── demos/
└── interview/
    └── java/
        ├── reference-demo/
        └── threadlocal-demo/
```

## 为什么这样分

### `projects/`

这里放我持续研究的上游项目。

比如现在的：

- `projects/any-agent`

以后如果还研究别的 GitHub 项目，就继续往这里加，例如：

- `projects/langgraph`
- `projects/spring-ai`
- `projects/some-open-source-tool`

### `interview/`

这里放知识点验证、语言练习、小型可运行 demo。

现在先保留 Java 面试题，所以是：

- `interview/java/threadlocal-demo`
- `interview/java/reference-demo`

后面如果有别的方向，也可以继续加：

- `interview/mysql`
- `interview/redis`
- `interview/jvm`

## 我怎么用这个仓库

### 1. 研究某个 GitHub 项目

例如：

```bash
cd /Users/raven/Dev/Workspace/Personal/research-hub/projects/any-agent
```

在一个项目目录里，通常会有：

- `docs/`：我整理后的理解笔记
- `demos/`：能反复运行的稳定示例

any-agent 最常用的 playground：

```bash
cd /Users/raven/Dev/Workspace/Personal/research-hub/projects/any-agent/demos/claude-agent-sdk-playground
npm run basic
```

### 2. 跑面试题 / 小 demo

例如：

```bash
cd /Users/raven/Dev/Workspace/Personal/research-hub/interview/java/threadlocal-demo
javac BasicUsageDemo.java && java BasicUsageDemo
```

## 当前原则

- 顶层只放长期会用到的分类
- `projects/` 面向“研究上游项目”
- `interview/` 面向“知识点验证和小 demo”
- 具体示例优先能直接跑，不做过多空目录设计
