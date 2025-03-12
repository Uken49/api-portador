package com.example.apiportador.presentation.handler.exception;

public class InvalidStatusValueException extends RuntimeException {
    public InvalidStatusValueException(String message) {
        super(message);
    }
}
