package com.project.banksystem.application.backend.card.impl;

import com.project.banksystem.application.backend.card.query.CardFinder;
import com.project.banksystem.application.backend.card.validator.CardStatusValidator;
import com.project.banksystem.application.backend.card.validator.CardValidator;
import com.project.banksystem.application.config.properties.CardConfig;
import com.project.banksystem.core.enums.card.CardStatus;
import com.project.banksystem.infrastructure.model.Card;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CardStatusService {
    private final CardValidator cardValidator;
    private final CardFinder cardFinder;
    private final CardConfig cardConfig;


    public CardStatusService(CardValidator cardValidator,
                             CardFinder cardFinder,
                             CardConfig cardConfig) {
        this.cardValidator = cardValidator;
        this.cardFinder = cardFinder;
        this.cardConfig = cardConfig;
    }


    public Card updateCardStatus(final UUID cardId,
                                 final CardStatus cardStatus) {
        Card card = cardFinder.findByIdWithActiveAccountAndCustomer(cardId);

        CardStatusValidator.assertTransitionalStatus(card, cardStatus);


        card.changeCardStatus(cardStatus);
        if (cardStatus == CardStatus.DELIVERED) {
            card.setDeliveredAt(LocalDateTime.now(), cardConfig.getDeliveredStatusExpiresDay());
        }

        return card;
    }
}
