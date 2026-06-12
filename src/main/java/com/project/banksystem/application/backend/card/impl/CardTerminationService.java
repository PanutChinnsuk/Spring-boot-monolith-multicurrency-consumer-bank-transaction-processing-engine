package com.project.banksystem.application.backend.card.impl;

import com.project.banksystem.application.backend.card.query.CardFinder;
import com.project.banksystem.application.backend.card.validator.CardStatusValidator;
import com.project.banksystem.application.backend.card.validator.CardValidator;
import com.project.banksystem.core.enums.card.CardStatus;
import com.project.banksystem.infrastructure.model.Card;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CardTerminationService {
    private final CardFinder cardFinder;
    private final CardValidator cardValidator;

    public CardTerminationService(CardFinder cardFinder,
                                  CardValidator cardValidator) {
        this.cardFinder = cardFinder;
        this.cardValidator = cardValidator;
    }


    public Card cancelCard(final UUID cardId) {
        Card card = cardFinder.findById(cardId);

        CardStatusValidator.assertTerminated(card);

        card.changeCardStatus(CardStatus.CANCELLED);

        return card;

    }
}
