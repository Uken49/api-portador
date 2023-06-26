package com.example.apiportador.presentation.handler.exception;

public class ClientWithIDAlreadyExistsException extends RuntimeException {
    public ClientWithIDAlreadyExistsException(String message) {
        super(message);
    }
}
