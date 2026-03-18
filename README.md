# Drugs, Traits, and Status: A Behavioral Link

This project investigates how the frequency of common drug use (cannabis, alcohol, nicotine) correlates with personality traits (neuroticism, extraversion, openness) and socioeconomic status.

## Development Progression

The repository evolved through four stages. Each stage builds on the output
of the previous one.

| Stage | Focus | Key Files |
|-------|-------|-----------|
| **1. Data Analysis** | Load, clean, and explore two datasets; run regressions and build visualizations | `FinalProject.ipynb` |
| **2. Findings Export** | Extract notebook results into a structured JSON schema | `export_results.py` → `results.json` |
| **3. Java CLI** | Build an interactive terminal report reader (zero dependencies) | `java-reporting/` |
| **4. Documentation** | Tie all layers together with READMEs and phase markers | `README.md` |

## Project Structure

```
├── FinalProject.ipynb        ← Stage 1: Jupyter notebook (data cleaning, EDA, statistical analysis)
├── export_results.py         ← Stage 2: Python script (exports findings to JSON)
├── java-reporting/           ← Stage 3: Java CLI app (interactive terminal report)
│   ├── src/reporting/        ←   Source code (model/, Main, JsonParser, etc.)
│   ├── results.json          ←   Exported findings consumed by the CLI
│   ├── compile.sh            ←   One-command build script
│   └── README.md             ←   Module-specific documentation
└── README.md                 ← This file — project overview
```

## Quick Start

### 1. Analysis (Python / Jupyter Notebook)

`FinalProject.ipynb` performs the full behavioral data analysis — loading two datasets, cleaning and standardizing the data, running OLS regressions, and producing correlation matrices and visualizations. All statistical findings originate here.

### 2. Export (Python)

`export_results.py` packages the key findings (project summary, correlations, demographic patterns, model results, and takeaways) into a structured `results.json` file that follows a defined schema the Java module can read.

```bash
python3 export_results.py                   # writes java-reporting/results.json
python3 export_results.py -o custom.json    # custom output path
```

### 3. Reporting (Java CLI)

`java-reporting/` is a lightweight command-line tool that reads `results.json` and presents a clean, section-by-section terminal report with an interactive menu. No web, database, or GUI dependencies — just `javac` and `java`.

```bash
cd java-reporting
./compile.sh
java -cp out reporting.Main results.json
```

## Key Findings

- Substance use is better explained by **personality traits** than by age or SES.
- Cannabis users score higher on **openness to experience** (r = 0.41).
- Nicotine users score higher on **neuroticism**.
- Socioeconomic status alone does **not** meaningfully predict drug use patterns.
- Cannabis & nicotine use are **strongly correlated** (r = 0.52).

## Team

- Ben Montesinos
- Nathan Tosoc
- Ryan Luo
- Faiz Lodhi
- Adrian Billawala
