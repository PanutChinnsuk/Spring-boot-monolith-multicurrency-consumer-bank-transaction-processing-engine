package com.project.banksystem.application.backend.card.body;

import com.project.banksystem.infrastructure.model.Card;

public class CardReplacementContext {
    private Card optionalCard;

    public CardReplacementContext(Card optionalCard) {
        this.optionalCard = optionalCard;
    }

    public Card getOptionalCard() {
        return optionalCard;
    }
}
