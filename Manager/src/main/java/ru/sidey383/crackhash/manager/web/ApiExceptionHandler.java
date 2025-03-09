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
import ru.sidey383.crackhash.manager.dto.ErrorAnswer;
import ru.sidey383.crackhash.manager.exception.ServiceException;

import java.util.UUID;

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
    public ResponseEntity<ErrorAnswer> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        UUID uuid = UUID.randomUUID();
        log.info("Fail with error {}", uuid, ex);
        return new ResponseEntity<>(
                new ErrorAnswer(
                        "ERROR",
                        "Wrong arguments",
                        "field %s %s".formatted(
                                ex.getBindingResult().getFieldErrors().getFirst().getField(),
                                ex.getBindingResult().getFieldErrors().getFirst().getDefaultMessage()
                        ),
                        uuid
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorAnswer> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        UUID uuid = UUID.randomUUID();
        log.info("Fail with error {}", uuid, ex);
        return new ResponseEntity<>(
                new ErrorAnswer(
                        "ERROR",
                        "Wrong request",
                        ex.getMessage(),
                        uuid
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ErrorAnswer> handleConstraintViolationException(ConstraintViolationException ex) {
        UUID uuid = UUID.randomUUID();
        log.info("Fail with error {}", uuid, ex);
        return new ResponseEntity<>(
                new ErrorAnswer(
                        "ERROR",
                        "Wrong arguments",
                        "field %s %s".formatted(
                                ex.getConstraintViolations().stream().toList().getFirst().getPropertyPath(),
                                ex.getConstraintViolations().stream().toList().getFirst().getMessage()
                        ),
                        uuid
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorAnswer> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex
    ) {
        UUID uuid = UUID.randomUUID();
        log.info("Fail with error {}", uuid, ex);
        return new ResponseEntity<>(
                new ErrorAnswer(
                        "ERROR",
                        "Wrong argument type",
                        ex.getMessage(),
                        uuid
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorAnswer> handleRuntimeException(RuntimeException ex) {
        UUID uuid = UUID.randomUUID();
        log.info("Fail with error {}", uuid, ex);
        return new ResponseEntity<>(
                new ErrorAnswer(
                        "ERROR",
                        "Internal error",
                        ex.getClass().toString(),
                        uuid
                ),
                HttpStatus.BAD_REQUEST
        );
    }

}
