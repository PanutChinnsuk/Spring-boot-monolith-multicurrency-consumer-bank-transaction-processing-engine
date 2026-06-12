package com.project.banksystem.application.backend.card;

import com.project.banksystem.infrastructure.model.Card;
import com.project.banksystem.infrastructure.repository.CardRepository;
import org.springframework.stereotype.Component;

@Component
public class CardManager {
    private final CardRepository cardRepository;

    public CardManager(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card save(final Card card) {
        return cardRepository.save(card);
    }
}
