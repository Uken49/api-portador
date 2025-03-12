package com.example.apiportador.presentation.handler.exception;

public class ClientDoesNotCorrespondToCreditAnalysisException extends RuntimeException {
    public ClientDoesNotCorrespondToCreditAnalysisException(String message) {
        super(message);
    }
}
