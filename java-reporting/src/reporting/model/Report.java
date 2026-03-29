package reporting.model;

import java.util.List;

// Top level model that aggregates all sections of the report
public class Report {

    private final ProjectSummary projectSummary;
    private final String researchQuestion;
    private final List<DatasetInfo> datasets;
    private final List<Correlation> keyCorrelations;
    private final List<Finding> demographicFindings;
    private final List<Finding> modelFindings;
    private final List<String> keyTakeaways;

    public Report(ProjectSummary projectSummary,
                  String researchQuestion,
                  List<DatasetInfo> datasets,
                  List<Correlation> keyCorrelations,
                  List<Finding> demographicFindings,
                  List<Finding> modelFindings,
                  List<String> keyTakeaways) {
        this.projectSummary      = projectSummary;
        this.researchQuestion    = researchQuestion;
        this.datasets            = datasets;
        this.keyCorrelations     = keyCorrelations;
        this.demographicFindings = demographicFindings;
        this.modelFindings       = modelFindings;
        this.keyTakeaways        = keyTakeaways;
    }

    public ProjectSummary   getProjectSummary()      { return projectSummary; }
    public String           getResearchQuestion()    { return researchQuestion; }
    public List<DatasetInfo> getDatasets()            { return datasets; }
    public List<Correlation> getKeyCorrelations()     { return keyCorrelations; }
    public List<Finding>    getDemographicFindings()  { return demographicFindings; }
    public List<Finding>    getModelFindings()        { return modelFindings; }
    public List<String>     getKeyTakeaways()         { return keyTakeaways; }
}
