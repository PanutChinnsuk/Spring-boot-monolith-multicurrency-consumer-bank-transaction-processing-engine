package com.project.banksystem.application.backend.card.validator;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.backend.account.validator.AccountValidator;
import com.project.banksystem.application.config.properties.CardEncryptionConfig;
import com.project.banksystem.core.enums.card.CardStatus;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Card;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.repository.CardRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CardValidator {
    private final CardEncryptionConfig cardEncryptionConfig;
    private final AccountValidator accountValidator;
    private final CardRepository cardRepository;

    public CardValidator(CardEncryptionConfig cardEncryptionConfig,
                         AccountValidator accountValidator,
                         CardRepository cardRepository) {
        this.cardEncryptionConfig = cardEncryptionConfig;
        this.accountValidator = accountValidator;
        this.cardRepository = cardRepository;
    }



    public void validateCardOperational(final Card card) {
        if (card.getCardStatus() != CardStatus.ACTIVE) {
            throw new BusinessException("Card is not operational.");
        }


        accountValidator.validateAccountOperational(card.getAccount());
    }

    public void validateCardHaveLastestEncryptionKey(final Card card) {
        if (card.getKeyVersion() != cardEncryptionConfig.getKeyVersion()) {
            throw new BusinessException("Card has outdated encryption key.");
        }
    }

    public void validateCardDoesNotHaveLastestKey(final Card card) {
        if (card.getKeyVersion() == cardEncryptionConfig.getKeyVersion()) {
            throw new BusinessException("Card already have lastest encryption key.");
        }
    }

    public void validateCardBelongToCustomer(final UUID cardId,
                                             final UUID customerId) {
        if (!cardRepository.isCardBelongToCustomer(cardId, customerId)) {
            throw new BusinessException("Card does not belong to customer.");
        }
    }

    public void validateCardBelongToCustomer(final Card card,
                                             final UUID customerId) {
        UUID cardCustomerId = card.getAccount().getCustomer().getCustomerId();

        if (!cardCustomerId.equals(customerId)) {
            throw new BusinessException("Card does not belong to customer.");
        }
    }






}
