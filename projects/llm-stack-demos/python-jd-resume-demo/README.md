# Python JD Resume Demo

## 1. 这个 demo 做什么

这是一个基于 `Python + OpenAI SDK + Pydantic` 的 JD / 简历匹配分析 demo。

核心业务是：

1. 读取 JD 文本
2. 读取简历文本
3. 让模型提取技能要求和候选人已有技能
4. 输出匹配分、差距项、补强建议和简历改写建议

这个 demo 的核心价值是：

> 用最短路径证明你能做结构化输出、文本分析和 LLM 驱动的业务报告生成。

---

## 2. 为什么选这个 demo

这个 demo 有几个优点：

- 上手快
- 结果直观
- 你自己马上能用
- 很容易扩成批量分析工具

它很适合做：

- 招聘匹配
- 简历优化
- JD 提取
- 技能标签抽取

---

## 3. 核心业务逻辑

输入：

- 一个 JD
- 一份简历

输出：

- 岗位核心技能
- 候选人已有技能
- 缺口技能
- 匹配分
- 三条针对性改写建议

---

## 4. 核心技术点

- Python OpenAI SDK
- Pydantic 结构化结果
- JSON 输出约束
- Prompt Engineering
- 文本抽取与分析

如果后面继续做，可以扩展成：

- 批量分析多个 JD
- 生成 Excel / Markdown 报告
- 做简历 bullet 优化
- 做岗位画像聚类

---

## 5. 项目结构

```text
python-jd-resume-demo/
├── README.md
├── requirements.txt
├── app/
│   ├── main.py
│   └── resume_analyzer.py
└── data/
    ├── sample_jd.md
    └── sample_resume.md
```

---

## 6. 运行前准备

```bash
export OPENAI_API_KEY=your_api_key
export OPENAI_CHAT_MODEL=gpt-4o-mini
```

安装依赖：

```bash
cd /Users/raven/Dev/Workspace/Personal/research-hub/projects/llm-stack-demos/python-jd-resume-demo
python3 -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
```

---

## 7. 运行方式

```bash
python app/main.py
```

---

## 8. 面试里怎么讲

可以这样说：

> 我做过一个 Python 的 JD / 简历匹配分析 demo，重点不是普通文本生成，而是让模型输出结构化结果。输入是岗位 JD 和简历文本，输出是技能标签、匹配分、差距项和改写建议。我用 Pydantic 约束结果结构，再把模型返回的 JSON 解析成业务对象。这个 demo 验证了我在 Python 生态里做结构化输出和文本分析类 LLM 应用的能力。如果继续做成产品，我会加批量任务、报告导出和评估指标。

---

## 9. 这个 demo 最适合写进简历的点

- Structured Output
- Python AI 原型开发
- 文本抽取
- 分析报告生成
