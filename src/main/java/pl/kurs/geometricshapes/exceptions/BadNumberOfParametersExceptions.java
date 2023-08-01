package pl.kurs.geometricshapes.exceptions;

public class BadNumberOfParametersExceptions extends RuntimeException {
    private String type;
    private int numberOfParameters;

    public BadNumberOfParametersExceptions(String type, int numberOfParameters) {
        this.type = type;
        this.numberOfParameters = numberOfParameters;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumberOfParameters() {
        return numberOfParameters;
    }

    public void setNumberOfParameters(int numberOfParameters) {
        this.numberOfParameters = numberOfParameters;
    }

    public String getMessage() {
        return "ShapeType: " + type + " should have " + numberOfParameters + " parameter(s)";
    }
}
