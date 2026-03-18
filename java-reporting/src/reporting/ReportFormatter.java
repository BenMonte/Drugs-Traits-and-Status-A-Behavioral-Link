package reporting;

import reporting.model.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Formats a {@link Report} into polished plain-text CLI output.
 */
public class ReportFormatter {

    private static final int WIDTH = 70;
    private static final String HEAVY = "=".repeat(WIDTH);
    private static final String LIGHT = "-".repeat(WIDTH);
    private static final int INDENT = 4;
    private static final String PAD = " ".repeat(INDENT);

    private final Report report;

    public ReportFormatter(Report report) {
        this.report = report;
    }

    /** Print the complete report to stdout. */
    public void printReport() {
        printBanner();
        printProjectSummary();
        printResearchQuestion();
        printDatasetSummary();
        printKeyCorrelations();
        printDemographicFindings();
        printModelFindings();
        printKeyTakeaways();
        printFooter();
    }

    // ---- section printers ------------------------------------------------

    private void printBanner() {
        out("");
        out(HEAVY);
        out("|" + center("DRUGS, TRAITS & STATUS", WIDTH - 2) + "|");
        out("|" + center("Behavioral Analysis Report", WIDTH - 2) + "|");
        out(HEAVY);
    }

    private void printProjectSummary() {
        section("PROJECT SUMMARY");
        ProjectSummary ps = report.getProjectSummary();
        if (!ps.getTitle().isEmpty()) {
            out(PAD + ps.getTitle());
            out("");
        }
        wrapped(ps.getDescription(), INDENT);
    }

    private void printResearchQuestion() {
        section("RESEARCH QUESTION");
        out("");
        wrapped("\"" + report.getResearchQuestion() + "\"", INDENT);
    }

    private void printDatasetSummary() {
        section("DATASET SUMMARY");
        List<DatasetInfo> datasets = report.getDatasets();
        if (datasets.isEmpty()) { out(PAD + "(no datasets)"); return; }

        for (int i = 0; i < datasets.size(); i++) {
            DatasetInfo ds = datasets.get(i);
            out("");
            out(PAD + "[" + (i + 1) + "] " + ds.getName());
            out(PAD + "    Rows ...... " + String.format("%,d", ds.getRows()));
            out(PAD + "    Columns ... " + ds.getColumns());
            if (!ds.getDescription().isEmpty()) {
                System.out.print(PAD + "    About ..... ");
                wrapped(ds.getDescription(), INDENT + 16);
            }
        }
    }

    public void printKeyCorrelations() {
        section("KEY CORRELATIONS");
        List<Correlation> corrs = report.getKeyCorrelations();
        if (corrs.isEmpty()) { out(PAD + "(none)"); return; }

        // Find the longest variable-pair name for alignment
        int maxVar = 0;
        for (Correlation c : corrs) {
            maxVar = Math.max(maxVar, c.getVariables().length());
        }

        out("");
        String fmt = PAD + "  %-" + maxVar + "s   r = %6.2f   %s";
        for (Correlation c : corrs) {
            String note = c.getNote().isEmpty() ? "" : "(" + c.getNote() + ")";
            out(String.format(fmt, c.getVariables(), c.getCoefficient(), note));
        }
    }

    public void printDemographicFindings() {
        section("DEMOGRAPHIC FINDINGS");
        printGroupedFindings(report.getDemographicFindings());
    }

    public void printModelFindings() {
        section("MODEL FINDINGS");
        printGroupedFindings(report.getModelFindings());
    }

    public void printKeyTakeaways() {
        section("KEY TAKEAWAYS");
        List<String> items = report.getKeyTakeaways();
        if (items.isEmpty()) { out(PAD + "(none)"); return; }

        out("");
        for (int i = 0; i < items.size(); i++) {
            String bullet = String.format("%s %d) ", PAD, i + 1);
            System.out.print(bullet);
            wrapped(items.get(i), bullet.length());
        }
    }

    private void printFooter() {
        out("");
        out(HEAVY);
        out("|" + center("End of Report", WIDTH - 2) + "|");
        out(HEAVY);
        out("");
    }

    // ---- shared helpers --------------------------------------------------

    /**
     * Group findings by category and print under sub-headers.
     */
    private void printGroupedFindings(List<Finding> findings) {
        if (findings.isEmpty()) { out(PAD + "(none)"); return; }

        // Preserve insertion order of categories
        Map<String, List<String>> groups = new LinkedHashMap<>();
        for (Finding f : findings) {
            String key = f.getCategory().isEmpty() ? "General" : f.getCategory();
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(f.getDetail());
        }

        for (var entry : groups.entrySet()) {
            out("");
            out(PAD + entry.getKey() + ":");
            for (String detail : entry.getValue()) {
                System.out.print(PAD + "  > ");
                wrapped(detail, INDENT + 4);
            }
        }
    }

    private void section(String title) {
        out("");
        out(LIGHT);
        out("  " + title);
        out(LIGHT);
    }

    private static String center(String text, int width) {
        int pad = Math.max(0, (width - text.length()) / 2);
        int right = width - pad - text.length();
        return " ".repeat(pad) + text + " ".repeat(Math.max(0, right));
    }

    private static void out(String s) {
        System.out.println(s);
    }

    /**
     * Word-wrap text to fit within WIDTH. The first line prints at the
     * current cursor position; continuation lines are indented.
     */
    private void wrapped(String text, int indent) {
        if (text == null || text.isBlank()) { out(""); return; }

        String pad = " ".repeat(indent);
        int maxLine = WIDTH - indent;
        String[] words = text.split("\\s+");
        StringBuilder line = new StringBuilder();

        boolean first = true;
        for (String word : words) {
            if (line.length() > 0 && line.length() + 1 + word.length() > maxLine) {
                out((first ? "" : pad) + line);
                line.setLength(0);
                first = false;
            }
            if (line.length() > 0) line.append(" ");
            line.append(word);
        }
        if (line.length() > 0) {
            out((first ? "" : pad) + line);
        }
    }
}
