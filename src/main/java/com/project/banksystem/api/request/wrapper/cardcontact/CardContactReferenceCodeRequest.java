package com.project.banksystem.api.request.wrapper.cardcontact;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CardContactReferenceCodeRequest {
    @NotNull
    private UUID cardId;


    public CardContactReferenceCodeRequest() {

    }

    public void setCardId(UUID cardId) {
        this.cardId = cardId;
    }

    public UUID getCardId() {
        return cardId;
    }
}
