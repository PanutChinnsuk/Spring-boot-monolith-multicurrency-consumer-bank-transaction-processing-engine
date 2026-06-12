package com.project.banksystem.application.backend.banktransaction.cardtransaction.query;

import com.project.banksystem.application.backend.banktransaction.internal.transaction.validator.TransactionStatusValidator;
import com.project.banksystem.infrastructure.model.CardTransaction;
import com.project.banksystem.infrastructure.repository.CardTransactionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CardTransactionFinder {
    private final CardTransactionRepository cardTransactionRepository;

    public CardTransactionFinder(CardTransactionRepository cardTransactionRepository) {
        this.cardTransactionRepository = cardTransactionRepository;
    }

    public List<CardTransaction> findAllCancellableByCardId(final UUID cardId) {
        return cardTransactionRepository.getAllNonClosedCardTransactionByCardId(cardId,
                TransactionStatusValidator.getCancellableStatuses());
    }


}
