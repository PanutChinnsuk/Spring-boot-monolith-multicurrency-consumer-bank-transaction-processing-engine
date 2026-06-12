package com.project.banksystem.application.backend.banktransaction.cardtransaction;

import com.project.banksystem.infrastructure.model.CardTransaction;
import com.project.banksystem.infrastructure.repository.CardTransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class CardTransactionManager {
    private final CardTransactionRepository cardTransactionRepository;

    public CardTransactionManager(CardTransactionRepository cardTransactionRepository) {
        this.cardTransactionRepository = cardTransactionRepository;
    }

    public CardTransaction save(final CardTransaction cardTransaction) {
        return cardTransactionRepository.save(cardTransaction);
    }
}
