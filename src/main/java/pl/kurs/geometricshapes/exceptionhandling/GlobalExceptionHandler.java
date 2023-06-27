package pl.kurs.geometricshapes.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.kurs.geometricshapes.exceptions.WrongEntityException;


import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({WrongEntityException.class})
    public ResponseEntity<ExceptionResponseDto> handleWrongEntityException(WrongEntityException e) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(List.of(e.getMessage()), "BAD_REQUEST", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDto);
    }

     @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ExceptionResponseDto> handleException(EntityNotFoundException e) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(List.of(e.getMessage()), "NOT_FOUND", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDto);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionResponseDto> handleValidationException(MethodArgumentNotValidException e) {
        List<String> errorsMessages = e.getFieldErrors().stream()
                .map(fe -> "Field: " + fe.getField() + " / rejected value: '" + fe.getRejectedValue() + "' / message: " + fe.getDefaultMessage())
                .collect(Collectors.toList());
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(errorsMessages, "BAD_REQUEST", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDto);
    }

}
