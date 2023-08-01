package pl.kurs.geometricshapes.exceptions;

public class NoShapeTypeException extends RuntimeException{

    private String type;

    public NoShapeTypeException(String type) {
        this.type = type;
    }

    public NoShapeTypeException(String message, String type) {
        super(message);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return "ShapeType: " + type + " is not supported";
    }
}
