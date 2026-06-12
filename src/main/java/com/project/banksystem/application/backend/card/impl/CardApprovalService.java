package com.project.banksystem.application.backend.card.impl;

import com.project.banksystem.application.backend.card.CardNumberEncryption;
import com.project.banksystem.application.backend.card.body.CardRequiredMigrationContext;
import com.project.banksystem.application.backend.card.query.CardFinder;
import com.project.banksystem.application.backend.card.validator.CardStatusValidator;
import com.project.banksystem.application.backend.card.validator.CardValidator;
import com.project.banksystem.core.enums.card.CardStatus;
import com.project.banksystem.infrastructure.model.Card;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CardApprovalService {
    private final CardValidator cardValidator;
    private final CardFinder cardFinder;
    private final CardNumberEncryption cardNumberEncryption;
    private final CardMigrationService cardMigrationService;

    public CardApprovalService(CardValidator cardValidator,
                               CardFinder cardFinder,
                               CardNumberEncryption cardNumberEncryption,
                               CardMigrationService cardMigrationService) {
        this.cardValidator = cardValidator;
        this.cardFinder = cardFinder;
        this.cardNumberEncryption = cardNumberEncryption;
        this.cardMigrationService = cardMigrationService;
    }



    public Card approveCard(final UUID cardId) {
        Card card = cardFinder.findByIdWithActiveAccountAndCustomer(cardId);

        cardValidator.validateCardHaveLastestEncryptionKey(card);
        CardStatusValidator.assertApprove(card);

        card.changeCardStatus(CardStatus.APPROVED);

        return card;
    }

    public void approvePendingCard(final UUID cardId) {
        Card card = cardFinder.findByIdWithActiveAccountAndCustomer(cardId);

        CardStatusValidator.assertApprovePending(card);

        CardRequiredMigrationContext context = cardMigrationService.isCardRequiredMigration(card);
        if (context.isRequired()) {
            cardMigrationService.migrateCardEncryptionKey(null, context);
        }

        card.changeCardStatus(CardStatus.ACTIVE);

    }
}
