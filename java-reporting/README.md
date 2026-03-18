# Java CLI Reporting Module — Stage 3

> **Where this fits:** The Jupyter notebook (Stage 1) produces the analysis →
> `export_results.py` (Stage 2) extracts findings into `results.json` →
> **this module** (Stage 3) presents them as a polished interactive terminal
> report.  See the root [README](../README.md) and [DEVLOG](../DEVLOG.md) for
> the full project timeline.

A lightweight, zero-dependency command-line tool that reads the JSON results
file exported from the Python analysis notebook and prints a clean,
section-by-section terminal report with an interactive menu.

## Project Structure

```
java-reporting/
├── src/
│   └── reporting/
│       ├── Main.java              # CLI entry point
│       ├── ResultsLoader.java     # Parses JSON into model objects
│       ├── ReportFormatter.java   # Formats & prints the terminal report
│       ├── JsonParser.java        # Minimal JSON parser (no dependencies)
│       └── model/
│           ├── Report.java        # Top-level report container
│           ├── ProjectSummary.java
│           ├── DatasetInfo.java
│           ├── Correlation.java
│           └── Finding.java
├── results.json                   # Sample results (pre-filled with notebook findings)
├── compile.sh                     # Build helper
└── README.md
```

## Class Responsibilities

| Class                      | Role                                             |
|----------------------------|--------------------------------------------------|
| `model.Report`             | Aggregates all seven report sections              |
| `model.ProjectSummary`     | Title + description for the project overview      |
| `model.DatasetInfo`        | Name, dimensions, description for one dataset     |
| `model.Correlation`        | Variable pair, r-value, and interpretive note     |
| `model.Finding`            | Category tag + detail string (demographics/model) |
| `ResultsLoader`            | Reads JSON file → builds a `Report`               |
| `JsonParser`               | Zero-dependency recursive-descent JSON parser     |
| `ReportFormatter`          | Walks a `Report` and prints formatted output      |
| `Main`                     | CLI glue (arg parsing, wiring loader → formatter) |

## Prerequisites

- Java 11 or later (`java --version` / `javac --version`)

## Quick Start

```bash
# 1. Compile
chmod +x compile.sh
./compile.sh

# 2. Run with the sample results
java -cp out reporting.Main results.json
```

## JSON Schema

See `results.json` for the full example. Top-level keys:

```json
{
  "project_title":        "string",
  "project_description":  "string",
  "research_question":    "string",
  "datasets":             [ { "name", "rows", "columns", "description" } ],
  "key_correlations":     [ { "variables", "coefficient", "note" } ],
  "demographic_findings": [ { "category", "detail" } ],
  "model_findings":       [ { "category", "detail" } ],
  "key_takeaways":        [ "string" ]
}
```

### Exporting from the Notebook

The recommended way to regenerate the JSON is to run the export script
from the project root:

```bash
python3 ../export_results.py          # writes results.json into this directory
python3 ../export_results.py -o out.json   # custom path
```
