package com.project.banksystem.application.backend.card.impl;

import com.project.banksystem.application.backend.card.query.CardFinder;
import com.project.banksystem.application.backend.card.validator.CardStatusValidator;
import com.project.banksystem.application.backend.card.validator.CardValidator;
import com.project.banksystem.core.enums.card.CardStatus;
import com.project.banksystem.infrastructure.model.Card;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CardSuspensionService {
    private final CardFinder cardFinder;
    private final CardValidator cardValidator;

    public CardSuspensionService(CardFinder cardFinder,
                                 CardValidator cardValidator) {
        this.cardFinder = cardFinder;
        this.cardValidator = cardValidator;
    }

    @Transactional
    public Card suspendCard(final UUID cardId, final CardStatus restrictStatus) {
        Card card = cardFinder.findById(cardId);

        CardStatusValidator.assertUpdate(card, restrictStatus);
        CardStatusValidator.assertRestrict(card, restrictStatus);

        card.changeCardStatus(restrictStatus);

        return card;

    }
}
