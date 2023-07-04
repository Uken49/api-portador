package com.example.apiportador.presentation.handler.exception;

public class RequestedLimitGreaterThanAvailableException extends RuntimeException {
    public RequestedLimitGreaterThanAvailableException(String message) {
        super(message);
    }
}
