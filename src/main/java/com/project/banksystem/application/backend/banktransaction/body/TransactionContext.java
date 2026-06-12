package com.project.banksystem.application.backend.banktransaction.body;

import com.project.banksystem.application.backend.banktransaction.cardtransaction.body.CardTransactionTemplate;
import com.project.banksystem.core.enums.transaction.TransactionPrincipal;
import jakarta.annotation.Nullable;

public class TransactionContext {
    private TransactionPrincipal transactionPrincipal;

    @Nullable
    private CardTransactionTemplate cardTransactionTemplate;

    public TransactionContext(TransactionPrincipal transactionPrincipal) {
        this.transactionPrincipal = transactionPrincipal;
    }

    public void setCardTransactionTemplate(@Nullable CardTransactionTemplate cardTransactionTemplate) {
        this.cardTransactionTemplate = cardTransactionTemplate;
    }

    public TransactionPrincipal getTransactionPrincipal() {
        return transactionPrincipal;
    }

    @Nullable
    public CardTransactionTemplate getCardTransactionTemplate() {
        return cardTransactionTemplate;
    }
}
