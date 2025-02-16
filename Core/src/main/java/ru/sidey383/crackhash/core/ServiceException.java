package ru.sidey383.crackhash.core;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    private final ErrorStatus errorStatus;

    public ServiceException(String message) {
        super(message);
        this.errorStatus = ErrorStatus.INTERNAL_ERROR;
    }

    public ServiceException(String message, Throwable reason) {
        super(message, reason);
        this.errorStatus = ErrorStatus.INTERNAL_ERROR;
    }

    public ServiceException(ErrorStatus status, String message) {
        super(message);
        this.errorStatus = status;
    }

    public ServiceException(ErrorStatus status, String message, Throwable reason) {
        super(message, reason);
        this.errorStatus = status;
    }

}

