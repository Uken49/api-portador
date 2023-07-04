package com.example.apiportador.presentation.controller;

import com.example.apiportador.applicationservice.cardservice.CreateCard;
import com.example.apiportador.applicationservice.cardservice.SearchCard;
import com.example.apiportador.applicationservice.cardservice.UpdateCard;
import com.example.apiportador.presentation.request.CardRequest;
import com.example.apiportador.presentation.response.CardResponse;
import com.example.apiportador.presentation.response.CardUpdatedResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    private final SearchCard searchCard;
    private final UpdateCard updateCard;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CardResponse createCard(@PathVariable UUID cardHolderId, @RequestBody @Valid CardRequest cardRequest) {
        return createCard.createCard(cardHolderId, cardRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CardResponse> getAllCard(@PathVariable UUID cardHolderId) {
        return searchCard.getAllCard(cardHolderId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{cardId}")
    public CardResponse getAllCard(@PathVariable UUID cardHolderId, @PathVariable UUID cardId) {
        return searchCard.getCard(cardId, cardHolderId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{cardId}")
    public CardUpdatedResponse updateCardLimit(
            @PathVariable UUID cardHolderId,
            @PathVariable UUID cardId,
            @Valid @RequestBody CardRequest cardRequest
    ) {
        return updateCard.updateCardLimit(cardId, cardHolderId, cardRequest);
    }
}
