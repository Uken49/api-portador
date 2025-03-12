package com.example.apiportador.presentation.handler.exception;

public class CardHolderDoesNotCorrespondToCardException extends RuntimeException {
    public CardHolderDoesNotCorrespondToCardException(String message) {
        super(message);
    }
}
