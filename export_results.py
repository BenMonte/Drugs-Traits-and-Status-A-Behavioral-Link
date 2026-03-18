"""
export_results.py  —  Stage 2 of the development pipeline

Bridges the Jupyter notebook analysis (Stage 1) and the Java CLI
reporting module (Stage 3).  All values below were extracted from the
final results in FinalProject.ipynb and are encoded in a snake_case
JSON schema that the Java module's ResultsLoader expects.

Usage:
    python export_results.py                   # → java-reporting/results.json
    python export_results.py -o custom.json    # custom output path
"""

import json
import argparse

# ── Project findings ────────────────────────────────────────────────

results = {
    "project_title": "Drugs, Traits, and Status: A Behavioral Link",

    "project_description": (
        "This project explores how the frequency of common drug use "
        "(cannabis, alcohol, nicotine) correlates with personality traits "
        "and socioeconomic status. Key personality traits examined were "
        "neuroticism, extraversion, and openness to experience. Methods "
        "included data preprocessing, exploratory data analysis, and "
        "correlation and regression techniques applied to two datasets."
    ),

    "research_question": (
        "How does the frequency of use of common drugs (cannabis, alcohol, "
        "nicotine) correlate with personality (neuroticism, extraversion, "
        "openness) and socioeconomic status (class status)?"
    ),

    "datasets": [
        {
            "name": "Youth Smoking and Drug Dataset",
            "rows": 10000,
            "columns": 8,
            "description": (
                "Covers ages 10-80 with smoking prevalence, drug "
                "experimentation scores, socioeconomic status, and "
                "mental health ratings."
            ),
        },
        {
            "name": "Drug Consumption Dataset (UCI)",
            "rows": 1884,
            "columns": 32,
            "description": (
                "Personality traits (Big Five), demographics, and usage "
                "frequency for cannabis, alcohol, nicotine and other "
                "substances."
            ),
        },
    ],

    "key_correlations": [
        {"variables": "Cannabis & Nicotine",         "coefficient": 0.52,  "note": "strong positive"},
        {"variables": "Cannabis & Openness",         "coefficient": 0.41,  "note": "moderate positive"},
        {"variables": "Neuroticism & Extraversion",  "coefficient": -0.43, "note": "strong negative"},
        {"variables": "Extraversion & Openness",     "coefficient": 0.25,  "note": "weak positive"},
        {"variables": "Alcohol & Personality Traits", "coefficient": 0.05,  "note": "negligible"},
    ],

    "demographic_findings": [
        {"category": "Age",       "detail": "Cannabis use peaks in mid-twenties and tapers after age 65."},
        {"category": "Age",       "detail": "Alcohol use is relatively stable across age groups."},
        {"category": "Education", "detail": "Alcohol use increases with higher education levels."},
        {"category": "Education", "detail": "Cannabis and nicotine use decrease as education level increases."},
        {"category": "SES",       "detail": "No significant socioeconomic skew found in drug experimentation groups."},
        {"category": "SES",       "detail": "Smoking prevalence is roughly equal (~27%) across all SES levels."},
    ],

    "model_findings": [
        {"category": "OLS Regression", "detail": "Age explains near-zero variance in drug experimentation (R-sq ~ 0, p = 0.32)."},
        {"category": "OLS Regression", "detail": "Socioeconomic status is a poor predictor of smoking prevalence (R-sq ~ 0, p = 0.48)."},
        {"category": "OLS Regression", "detail": "Small but significant link between drug experimentation and mental health resilience (R-sq = 0.001, p < 0.01)."},
        {"category": "Correlation",    "detail": "Openness exerts the strongest positive association with cannabis and alcohol use."},
        {"category": "Correlation",    "detail": "Neuroticism aligns most closely with nicotine consumption."},
        {"category": "General",        "detail": "Trait-driven associations persist across age groups."},
    ],

    "key_takeaways": [
        "Substance use is better explained by personality traits than by demographic factors like age or SES.",
        "Cannabis users tend to score higher on openness to experience.",
        "Nicotine users tend to score higher on neuroticism.",
        "Socioeconomic status alone does not meaningfully predict drug use patterns.",
        "The relationship between drug use and personality is trait-driven, not age-determined.",
    ],
}

# ── Export ──────────────────────────────────────────────────────────

def export(output_path: str) -> None:
    with open(output_path, "w") as f:
        json.dump(results, f, indent=2)
    print(f"Exported results to {output_path}")


if __name__ == "__main__":
    parser = argparse.ArgumentParser(
        description="Export project findings to results.json for the Java reporting module."
    )
    parser.add_argument(
        "--output", "-o",
        default="java-reporting/results.json",
        help="Output file path (default: java-reporting/results.json)",
    )
    args = parser.parse_args()
    export(args.output)
