package com.example.apiportador.presentation.controller;

import com.example.apiportador.applicationservice.cardservice.CreateCard;
import com.example.apiportador.presentation.request.CardRequest;
import com.example.apiportador.presentation.response.CardResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0/card-holders/{cardHolderId}/cards")
@RequiredArgsConstructor
@Validated
public class CardController {

    private final CreateCard createCard;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CardResponse createCard(@PathVariable UUID cardHolderId, @RequestBody @Valid CardRequest cardRequest) {
        return createCard.createCard(cardHolderId, cardRequest);
    }

}
