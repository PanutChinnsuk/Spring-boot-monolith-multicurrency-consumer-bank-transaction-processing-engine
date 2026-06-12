package com.project.banksystem.application.backend.banktransaction.cardtransaction.validator;

import com.project.banksystem.core.enums.card.CardStatus;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Transaction;
import com.project.banksystem.infrastructure.repository.CardTransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class CardTransactionValidator {
    private final CardTransactionRepository cardTransactionRepository;

    public CardTransactionValidator(CardTransactionRepository cardTransactionRepository) {
        this.cardTransactionRepository = cardTransactionRepository;
    }

    public void validateIsCardTransactionValid(final Transaction transaction) {
        if (!cardTransactionRepository.isCardFromTransactionValid(transaction.getTransactionId(),
                CardStatus.ACTIVE)) {
            throw new BusinessException("Card used in transaction is not valid.");
        }
    }
}
