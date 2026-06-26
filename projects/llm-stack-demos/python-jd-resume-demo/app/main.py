from pathlib import Path

from resume_analyzer import ResumeAnalyzer, load_text


def main() -> None:
    base_dir = Path(__file__).resolve().parent.parent
    jd_text = load_text(base_dir / "data" / "sample_jd.md")
    resume_text = load_text(base_dir / "data" / "sample_resume.md")

    analyzer = ResumeAnalyzer.from_env()
    report = analyzer.analyze(jd_text, resume_text)

    print("===== JD 核心技能 =====")
    for item in report.jd_core_skills:
        print("-", item)

    print("\n===== 简历已有技能 =====")
    for item in report.resume_skills:
        print("-", item)

    print("\n===== 缺口技能 =====")
    for item in report.missing_skills:
        print("-", item)

    print("\n===== 匹配亮点 =====")
    for item in report.strengths:
        print("-", item)

    print(f"\n===== 匹配评分 =====\n{report.score}")

    print("\n===== 简历改写建议 =====")
    for item in report.rewrite_suggestions:
        print("-", item)


if __name__ == "__main__":
    main()
