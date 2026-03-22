package reporting.model;

public class Finding {

    private final String category;
    private final String detail;

    public Finding(String category, String detail) {
        this.category = category;
        this.detail   = detail;
    }

    public String getCategory() { return category; }
    public String getDetail()   { return detail; }
}
