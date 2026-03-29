package reporting.model;

// Holds a single correlation entry, variables paired with a coefficient and note
public class Correlation {

    private final String variables;
    private final double coefficient;
    private final String note;

    public Correlation(String variables, double coefficient, String note) {
        this.variables   = variables;
        this.coefficient = coefficient;
        this.note        = note;
    }

    public String getVariables()   { return variables; }
    public double getCoefficient() { return coefficient; }
    public String getNote()        { return note; }
}
