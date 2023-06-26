package com.example.apiportador.presentation.controller;

import com.example.apiportador.applicationservice.cardholderservice.CreateCardHolders;
import com.example.apiportador.applicationservice.cardholderservice.SearchCardHolders;
import com.example.apiportador.presentation.request.CardHolderRequest;
import com.example.apiportador.presentation.response.CardHolderResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0/card-holders")
@RequiredArgsConstructor
@Validated
public class CardHolderController {

    private final CreateCardHolders createCardHolders;
    private final SearchCardHolders searchCardHolders;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CardHolderResponse createCardHolder(@RequestBody @Valid CardHolderRequest cardHolderRequest) {
        return createCardHolders.createCardHolder(cardHolderRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CardHolderResponse> searchCardHolder(@RequestParam(required = false) String status) {
        return searchCardHolders.getAllCardHolders(status);
    }

}
