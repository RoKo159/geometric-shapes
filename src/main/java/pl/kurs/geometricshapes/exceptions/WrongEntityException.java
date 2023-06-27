package pl.kurs.geometricshapes.exceptions;

import javax.persistence.PersistenceException;

public class WrongEntityException extends PersistenceException {

    public WrongEntityException(String message) {
        super(message);
    }

    public WrongEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
