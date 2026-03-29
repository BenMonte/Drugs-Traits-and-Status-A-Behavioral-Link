#!/usr/bin/env bash
set -e

SRC_DIR="src"
OUT_DIR="out"

# Clean previous build output, then compile all Java sources
rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

echo "Compiling..."
javac -d "$OUT_DIR" \
  "$SRC_DIR"/reporting/model/*.java \
  "$SRC_DIR"/reporting/*.java

echo "Done. Run with:"
echo "  java -cp out reporting.Main <results.json>"
