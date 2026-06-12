package com.project.banksystem.application.backend.banktransaction.builder;

import com.project.banksystem.application.backend.banktransaction.body.TransactionContext;
import com.project.banksystem.core.enums.transaction.TransactionPrincipal;

public final class TransactionContextBuilder {
    private TransactionContextBuilder() {

    }


    public static TransactionContext getFor(TransactionPrincipal transactionPrincipal) {
        return new TransactionContext(transactionPrincipal);
    }
}
