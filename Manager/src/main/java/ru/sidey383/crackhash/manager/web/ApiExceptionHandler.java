package ru.sidey383.crackhash.manager.web;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.sidey383.crackhash.manager.exception.ServiceException;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<String> handleServiceException(ServiceException ex) {
        return new ResponseEntity<>(
                ex.getMessage(),
                ex.getErrorStatus().getStatusCode()
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(
                "field %s %s".formatted(
                        ex.getBindingResult().getFieldErrors().getFirst().getField(),
                        ex.getBindingResult().getFieldErrors().getFirst().getDefaultMessage()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(
                "Can't read request body",
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntity<>(
                "field %s %s".formatted(
                        ex.getConstraintViolations().stream().toList().getFirst().getPropertyPath(),
                        ex.getConstraintViolations().stream().toList().getFirst().getMessage()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex
    ) {
        return new ResponseEntity<>(
                "Wrong request format",
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        log.error("Runtime exception", ex);
        return new ResponseEntity<>(
                "Internal error",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
