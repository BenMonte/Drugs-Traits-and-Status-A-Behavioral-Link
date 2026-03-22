# Java CLI Reporting Module

This module reads the JSON results file exported from the Python analysis
and prints the findings in a simple interactive terminal view. See the root
[README](../README.md) for the overall project context.

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

## Classes

| Class | Role |
|-------|------|
| `model.Report` | Holds the full report data |
| `model.ProjectSummary` | Stores the project title and description |
| `model.DatasetInfo` | Stores dataset metadata |
| `model.Correlation` | Stores one correlation result |
| `model.Finding` | Stores one demographic or model finding |
| `ResultsLoader` | Loads JSON into a `Report` |
| `JsonParser` | Parses the JSON text |
| `ReportFormatter` | Prints the report sections |
| `Main` | Runs the CLI menu |

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

To regenerate the JSON, run the export script from the project root:

```bash
python3 ../export_results.py          # writes results.json into this directory
python3 ../export_results.py -o out.json   # custom path
```
