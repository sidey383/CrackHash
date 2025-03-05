package ru.sidey383.crackhash.manager.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    WRONG_ARGS(HttpStatus.BAD_REQUEST);

    private final HttpStatusCode statusCode;
}
