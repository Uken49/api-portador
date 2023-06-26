package com.example.apiportador.applicationservice.cardservice;

import com.example.apiportador.infrastructure.repository.CardRepository;
import com.example.apiportador.presentation.request.CardRequest;
import com.example.apiportador.presentation.response.CardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCard {

    private final CardRepository cardRepository;

    public CardResponse createCard(CardRequest cardRequest) {

        return null;
    }
}
