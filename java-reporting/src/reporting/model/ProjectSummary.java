package reporting.model;

// Stores the project title and a short description of the study
public class ProjectSummary {

    private final String title;
    private final String description;

    public ProjectSummary(String title, String description) {
        this.title       = title;
        this.description = description;
    }

    public String getTitle()       { return title; }
    public String getDescription() { return description; }
}
