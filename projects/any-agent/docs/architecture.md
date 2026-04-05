# any-agent 结构分析

## 顶层结构

```text
any-agent/
├── .claude-plugin/marketplace.json
├── plugins/
│   ├── agent-sdk-guide/
│   ├── session-harvest/
│   └── claude-plugin-marketplace-setup/
├── README.md
└── README_CN.md
```

## 三层结构

### 1. Marketplace 层

由 `.claude-plugin/marketplace.json` 定义。

这个文件负责列出整个仓库里有哪些插件，是 Claude Code 加载这个市场时的入口。

### 2. Plugin 层

每个 plugin 通常包含：

- `.claude-plugin/plugin.json`
- 一个或多个 skill

当前项目中的 plugin 有：

- `agent-sdk-guide`
- `session-harvest`
- `claude-plugin-marketplace-setup`

### 3. Skill 层

每个 skill 都放在：

```text
plugins/<plugin-name>/skills/<skill-name>/SKILL.md
```

其中最关键的部分是 frontmatter 里的 `description` 字段，因为它直接影响自动触发时的匹配效果。

## 结构观察

这套结构很干净，也很适合作为样板：

- marketplace 负责分发
- plugin 负责分组
- skill 负责行为说明

所以这个项目很适合拿来作为以后设计类似仓库时的参考基线。
