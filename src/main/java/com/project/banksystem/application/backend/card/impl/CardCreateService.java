package com.project.banksystem.application.backend.card.impl;

import com.project.banksystem.api.request.dto.card.CardCreateDTO;
import com.project.banksystem.application.backend.account.query.AccountFinder;
import com.project.banksystem.application.backend.card.CardManager;
import com.project.banksystem.application.backend.card.CardNumberGenerator;
import com.project.banksystem.application.backend.card.body.CardReplacementContext;
import com.project.banksystem.application.backend.card.body.GeneratedCardNumber;
import com.project.banksystem.application.backend.card.validator.CardValidator;
import com.project.banksystem.application.config.properties.CardConfig;
import com.project.banksystem.application.config.properties.CardEncryptionConfig;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Card;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CardCreateService {
    private final CardManager cardManager;
    private final CardValidator cardValidator;
    private final CardNumberGenerator cardNumberGenerator;
    private final AccountFinder accountFinder;
    private final CardEncryptionConfig cardEncryptionConfig;
    private final CardConfig cardConfig;

    public CardCreateService(CardManager cardManager,
                             CardValidator cardValidator,
                             CardNumberGenerator cardNumberGenerator,
                             AccountFinder accountFinder,
                             CardEncryptionConfig cardEncryptionConfig,
                             CardConfig cardConfig) {
        this.cardManager = cardManager;
        this.cardValidator = cardValidator;
        this.cardNumberGenerator = cardNumberGenerator;
        this.accountFinder = accountFinder;
        this.cardEncryptionConfig = cardEncryptionConfig;
        this.cardConfig = cardConfig;
    }




    public Card createCard(final CardCreateDTO cardCreateDTO,
                        final CardReplacementContext cardReplacementContext) {
        Account account = accountFinder.findOperationalById(cardCreateDTO.getAccountId());

        GeneratedCardNumber generatedCardNumber = cardNumberGenerator.generateCardNumber();
        int encryptKeyVersion = cardEncryptionConfig.getKeyVersion();

        String plain = generatedCardNumber.plain();
        if (plain.length() < 16) {
            throw new IllegalStateException("Card number length must be 16.");
        }

        String maskedPan = "*".repeat(plain.length() - 4)
                + plain.substring(plain.length() - 4);

        Card replaced = cardReplacementContext.getOptionalCard();

        return cardManager.save(Card.builder()
                .account(account)
                .keyVersion(encryptKeyVersion)
                .expiry(cardConfig.getCardExpiry())
                .panEncrypt(generatedCardNumber.encrypted())
                .maskedPan(maskedPan)
                .optionalReplacedOf(replaced == null ? null : replaced)
                .build());

    }
}
