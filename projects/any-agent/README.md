# any-agent

这里放的是我围绕 `any-agent` 做的最小实践目录，不是为了复述上游 README，而是为了回答几个更实际的问题：

- 装完 skill 之后，Claude Code 到底能多做什么
- Claude Agent SDK 的最小调用怎么跑
- custom tools、session、agent 这些能力怎么落地

## 上游信息

- 仓库地址：https://github.com/yuebanhome/any-agent
- 本地参考目录：`/Users/raven/Dev/Workspace/Inbox/any-agent-main`

## 目录

- [docs/](docs/overview.md)：整理后的理解笔记
- [demos/](demos/README.md)：可以直接运行的示例

## 从哪里开始

如果你现在只想先跑起来，直接进这个目录：

```bash
cd /Users/raven/Dev/Workspace/Personal/research-hub/projects/any-agent/demos/claude-agent-sdk-playground
```

然后先执行：

```bash
npm run basic
```

如果你更关心 `skill` 和 `custom tools` 的关系，再去看：

- [docs/skills.md](docs/skills.md)
- [docs/usage.md](docs/usage.md)
- [docs/custom-tools-validation.md](docs/custom-tools-validation.md)
