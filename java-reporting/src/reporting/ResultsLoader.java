package reporting;

import reporting.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static reporting.JsonParser.*;

// Reads a JSON file and converts it into a Report with all sub models
public class ResultsLoader {

    // Loads the file, parses JSON, and assembles the Report object
    public static Report load(Path path) throws IOException {
        if (!Files.exists(path)) {
            throw new IOException("File not found: " + path);
        }

        String content = Files.readString(path);
        Map<String, Object> root = asObject(JsonParser.parse(content));

        ProjectSummary summary     = new ProjectSummary(
            asString(root.get("project_title")),
            asString(root.get("project_description"))
        );
        String question            = asString(root.get("research_question"));
        List<DatasetInfo> datasets = parseDatasets(root.get("datasets"));
        List<Correlation> corrs    = parseCorrelations(root.get("key_correlations"));
        List<Finding> demoFindings = parseFindings(root.get("demographic_findings"));
        List<Finding> modelFinds   = parseFindings(root.get("model_findings"));
        List<String> takeaways     = parseStringList(root.get("key_takeaways"));

        return new Report(summary, question, datasets, corrs,
                          demoFindings, modelFinds, takeaways);
    }

    private static List<DatasetInfo> parseDatasets(Object obj) {
        List<DatasetInfo> list = new ArrayList<>();
        if (obj == null) return list;
        for (Object item : asArray(obj)) {
            Map<String, Object> m = asObject(item);
            list.add(new DatasetInfo(
                asString(m.get("name")),
                asInt(m.get("rows")),
                asInt(m.get("columns")),
                asString(m.get("description"))
            ));
        }
        return list;
    }

    private static List<Correlation> parseCorrelations(Object obj) {
        List<Correlation> list = new ArrayList<>();
        if (obj == null) return list;
        for (Object item : asArray(obj)) {
            Map<String, Object> m = asObject(item);
            list.add(new Correlation(
                asString(m.get("variables")),
                asDouble(m.get("coefficient")),
                asString(m.get("note"))
            ));
        }
        return list;
    }

    private static List<Finding> parseFindings(Object obj) {
        List<Finding> list = new ArrayList<>();
        if (obj == null) return list;
        for (Object item : asArray(obj)) {
            Map<String, Object> m = asObject(item);
            list.add(new Finding(
                asString(m.get("category")),
                asString(m.get("detail"))
            ));
        }
        return list;
    }

    private static List<String> parseStringList(Object obj) {
        List<String> list = new ArrayList<>();
        if (obj == null) return list;
        for (Object item : asArray(obj)) {
            list.add(asString(item));
        }
        return list;
    }
}
