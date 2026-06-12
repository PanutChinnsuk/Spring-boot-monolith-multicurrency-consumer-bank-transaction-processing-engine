package com.project.banksystem.application.backend.banktransaction.cardtransaction.internal;

import com.project.banksystem.application.backend.banktransaction.cardtransaction.CardTransactionManager;
import com.project.banksystem.application.backend.banktransaction.cardtransaction.data.CardTransactionCreateData;
import com.project.banksystem.infrastructure.model.CardTransaction;
import org.springframework.stereotype.Service;

@Service
public class InternalCardTransactionCreateService {
    private final CardTransactionManager cardTransactionManager;

    public InternalCardTransactionCreateService(CardTransactionManager cardTransactionManager) {
        this.cardTransactionManager = cardTransactionManager;
    }

    public void createCardTransaction(final CardTransactionCreateData cardTransactionCreateData) {
        cardTransactionManager.save(CardTransaction.builder()
                .card(cardTransactionCreateData.getCard())
                .transaction(cardTransactionCreateData.getTransaction())
                .build());
    }
}
