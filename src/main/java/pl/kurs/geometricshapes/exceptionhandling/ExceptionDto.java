package pl.kurs.geometricshapes.exceptionhandling;

public class ExceptionDto {
    private String message;

    public ExceptionDto() {
    }

    public ExceptionDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
