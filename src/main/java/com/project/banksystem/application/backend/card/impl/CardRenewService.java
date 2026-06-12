package com.project.banksystem.application.backend.card.impl;

import com.project.banksystem.api.request.dto.card.CardCreateDTO;
import com.project.banksystem.application.backend.card.body.CardReplacementContext;
import com.project.banksystem.application.backend.card.query.CardFinder;
import com.project.banksystem.application.backend.card.validator.CardStatusValidator;
import com.project.banksystem.application.backend.card.validator.CardValidator;
import com.project.banksystem.core.enums.card.CardStatus;
import com.project.banksystem.infrastructure.model.Card;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CardRenewService {
    private final CardFinder cardFinder;
    private final CardValidator cardValidator;
    private final CardCreateService cardCreateService;

    public CardRenewService(CardFinder cardFinder,
                            CardValidator cardValidator,
                            CardCreateService cardCreateService) {
        this.cardFinder = cardFinder;
        this.cardValidator = cardValidator;
        this.cardCreateService = cardCreateService;
    }



    public Card renewCard(final UUID cardId) {
        Card card = cardFinder.findByIdWithActiveAccountAndCustomer(cardId);

        CardStatusValidator.assertReplace(card);

        CardCreateDTO cardCreateDTO = new CardCreateDTO();
        cardCreateDTO.setAccountId(card.getAccount().getAccountId());

        card.changeCardStatus(CardStatus.REPLACED);

        return cardCreateService.createCard(cardCreateDTO, new CardReplacementContext(card));
    }
}
