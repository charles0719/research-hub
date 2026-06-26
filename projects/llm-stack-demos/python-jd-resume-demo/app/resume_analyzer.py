from __future__ import annotations

import json
import os
from dataclasses import dataclass
from pathlib import Path
from typing import List

from openai import OpenAI
from pydantic import BaseModel, Field


class ResumeMatchReport(BaseModel):
    jd_core_skills: List[str] = Field(default_factory=list)
    resume_skills: List[str] = Field(default_factory=list)
    missing_skills: List[str] = Field(default_factory=list)
    strengths: List[str] = Field(default_factory=list)
    score: int = Field(ge=0, le=100)
    rewrite_suggestions: List[str] = Field(default_factory=list)


@dataclass
class ResumeAnalyzer:
    model: str
    client: OpenAI

    @classmethod
    def from_env(cls) -> "ResumeAnalyzer":
        api_key = os.getenv("OPENAI_API_KEY")
        if not api_key:
            raise RuntimeError("Please set OPENAI_API_KEY before running this demo.")
        model = os.getenv("OPENAI_CHAT_MODEL", "gpt-4o-mini")
        return cls(model=model, client=OpenAI(api_key=api_key))

    def analyze(self, jd_text: str, resume_text: str) -> ResumeMatchReport:
        prompt = f"""
你是一个招聘匹配分析助手。
请根据下面的 JD 和简历，输出一个 JSON 对象。

JSON 字段要求：
- jd_core_skills: JD 里的核心技能列表
- resume_skills: 简历里已经体现的技能列表
- missing_skills: 简历里缺少但 JD 强调的技能
- strengths: 候选人的匹配亮点
- score: 0 到 100 的匹配评分
- rewrite_suggestions: 3 条简历改写建议

JD:
{jd_text}

简历:
{resume_text}
"""

        response = self.client.chat.completions.create(
            model=self.model,
            temperature=0.1,
            response_format={"type": "json_object"},
            messages=[
                {
                    "role": "system",
                    "content": "你是一个严格输出 JSON 的招聘分析助手。不要输出 JSON 之外的内容。",
                },
                {"role": "user", "content": prompt},
            ],
        )

        content = response.choices[0].message.content or "{}"
        data = json.loads(content)
        return ResumeMatchReport.model_validate(data)


def load_text(path: str | Path) -> str:
    return Path(path).read_text(encoding="utf-8")
