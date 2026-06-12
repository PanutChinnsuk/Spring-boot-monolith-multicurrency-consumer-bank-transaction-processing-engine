package com.project.banksystem.application.backend.card.body;

import com.project.banksystem.infrastructure.model.Card;

public class CardRequiredMigrationContext {
    private Card card;

    public CardRequiredMigrationContext(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public boolean isRequired() {
        return card != null;
    }
}
