# any-agent 概览

## 总结

`any-agent` 是一个面向 Claude Code 的 marketplace 风格仓库，用插件和 skill 的方式来组织知识。

它的主要作用不是替代官方 SDK，而是在 Claude Code 的使用层之上，再加一层可复用的说明和指导。

## 核心思路

这个项目通过不同的 `SKILL.md`，把一些主题化知识包装成可触发的技能，让 Claude 在用户提到这些问题时，能更稳定地走向正确的回答路径。

目前覆盖的重点主题包括：

- Claude Agent SDK
- custom tools
- sessions
- 高级配置
- 会话知识沉淀
- marketplace 搭建

## 关键结论

这个项目和官方 SDK 的关系可以概括成：

- 官方 SDK 提供可编程运行能力
- `any-agent` 提供可复用的说明内容和组织方式

## 初步判断

这个项目的价值主要体现在：

- 学习辅助
- 知识路由
- skill 组织范例
- marketplace 仓库样板

它的重点不是新增底层运行能力，而是把已有能力包装得更好理解、更容易复用。
