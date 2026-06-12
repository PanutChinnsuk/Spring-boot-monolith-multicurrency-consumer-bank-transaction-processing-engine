package com.project.banksystem.application.backend.card.impl;

import com.project.banksystem.application.backend.card.CardNumberEncryption;
import com.project.banksystem.application.backend.card.body.CardMigrationInfo;
import com.project.banksystem.application.backend.card.body.CardRequiredMigrationContext;
import com.project.banksystem.application.backend.card.query.CardFinder;
import com.project.banksystem.application.backend.card.validator.CardValidator;
import com.project.banksystem.application.config.properties.CardEncryptionConfig;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Card;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CardMigrationService {
    private final CardEncryptionConfig cardEncryptionConfig;
    private final CardNumberEncryption cardNumberEncryption;
    private final CardFinder cardFinder;
    private final CardValidator cardValidator;

    public CardMigrationService(CardEncryptionConfig cardEncryptionConfig,
                                CardNumberEncryption cardNumberEncryption,
                                CardFinder cardFinder,
                                CardValidator cardValidator) {
        this.cardEncryptionConfig = cardEncryptionConfig;
        this.cardNumberEncryption = cardNumberEncryption;
        this.cardFinder = cardFinder;
        this.cardValidator = cardValidator;
    }

    public CardRequiredMigrationContext isCardRequiredMigration(final Card card) {
        if (card.getKeyVersion() != cardEncryptionConfig.getKeyVersion()) {
            return new CardRequiredMigrationContext(card);
        }

        return new CardRequiredMigrationContext(null);
    }

    public void forceCheckCardMigration(final Card card) {
        if (card.getKeyVersion() != cardEncryptionConfig.getKeyVersion()) {
            migrateCardEncryptionKey(null, new CardRequiredMigrationContext(card));
        }
    }

    public void migrateCardEncryptionKey(final UUID cardId,
                                         final CardRequiredMigrationContext optionalContext) {
        Card fromContext = optionalContext.getCard();

        if (cardId == null && fromContext == null) {
            throw new BusinessException("Invalid data for card migration.");
        }

        Card card = fromContext != null
                ? fromContext
                : cardFinder.findOperationalById(cardId);

        cardValidator.validateCardDoesNotHaveLastestKey(card);

        String oldPan = cardNumberEncryption.decrypt(card.getPanEncrypt(), card.getKeyVersion());
        CardMigrationInfo cardMigrationInfo = cardNumberEncryption.migrateEncrypt(oldPan);
        card.migratePan(cardMigrationInfo.newEncrypted());
        card.changeKeyVersion(cardMigrationInfo.usedVersion());

    }
}
