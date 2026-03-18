package reporting.model;

/**
 * Top-level container for the project summary text.
 */
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
