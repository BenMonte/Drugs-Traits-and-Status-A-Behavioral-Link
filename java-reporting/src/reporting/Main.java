package reporting;

import reporting.model.Report;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * CLI entry point for the reporting tool.
 *
 * Usage:  java reporting.Main &lt;results.json&gt;
 *
 * Loads a JSON results file, then presents an interactive menu
 * to view the full report or individual sections.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java reporting.Main <results.json>");
            System.err.println();
            System.err.println("  Reads a JSON results file and lets you");
            System.err.println("  view the full report or individual sections.");
            System.err.println();
            System.err.println("  See results.json for the expected format.");
            System.exit(1);
        }

        Path resultsPath = Path.of(args[0]);
        Report report;

        try {
            report = ResultsLoader.load(resultsPath);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
            return;
        }

        ReportFormatter formatter = new ReportFormatter(report);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("  Enter choice: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> formatter.printReport();
                case "2" -> formatter.printKeyCorrelations();
                case "3" -> formatter.printDemographicFindings();
                case "4" -> formatter.printModelFindings();
                case "5" -> formatter.printKeyTakeaways();
                case "0" -> {
                    System.out.println("\n  Goodbye!\n");
                    scanner.close();
                    return;
                }
                default -> System.out.println("\n  Invalid choice. Try 0-5.");
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("======================================================================");
        System.out.println("  REPORT MENU");
        System.out.println("======================================================================");
        System.out.println("  1)  Full Report");
        System.out.println("  2)  Key Correlations");
        System.out.println("  3)  Demographic Findings");
        System.out.println("  4)  Model Findings");
        System.out.println("  5)  Key Takeaways");
        System.out.println("  0)  Exit");
        System.out.println("----------------------------------------------------------------------");
    }
}
