package pl.kurs.geometricshapes.exceptionhandling;

import java.time.LocalDateTime;
import java.util.List;

public class ExceptionResponseDto {

    private List<String> errorMessages;
    private String errorCode;
    private LocalDateTime timestamp;

    public ExceptionResponseDto(List<String> errorMessages, String errorCode, LocalDateTime timestamp) {
        this.errorMessages = errorMessages;
        this.errorCode = errorCode;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ExceptionResponseDto{" +
                "errorMessages='" + errorMessages + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
