package com.project.banksystem.application.backend.banktransaction.internal.builder.body;

import com.project.banksystem.infrastructure.model.Transaction;

public class TransactionReversedContext {
    private Transaction reversedOf;

    public TransactionReversedContext(Transaction reversedOf) {
        this.reversedOf = reversedOf;
    }


    public Transaction getReversedOf() {
        return reversedOf;
    }
}
