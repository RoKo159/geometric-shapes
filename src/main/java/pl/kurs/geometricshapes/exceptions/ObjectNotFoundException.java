package pl.kurs.geometricshapes.exceptions;

public class ObjectNotFoundException extends RuntimeException {
    private long id;
    private String name;

    public ObjectNotFoundException(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getMessage() {
        return name + " with id: " + id + " not found";
    }
}
