package reporting.model;

/**
 * Metadata for a single dataset used in the analysis.
 */
public class DatasetInfo {

    private final String name;
    private final int rows;
    private final int columns;
    private final String description;

    public DatasetInfo(String name, int rows, int columns, String description) {
        this.name        = name;
        this.rows        = rows;
        this.columns     = columns;
        this.description = description;
    }

    public String getName()        { return name; }
    public int    getRows()        { return rows; }
    public int    getColumns()     { return columns; }
    public String getDescription() { return description; }
}
