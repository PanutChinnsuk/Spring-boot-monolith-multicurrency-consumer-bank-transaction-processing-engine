package com.project.banksystem.application.backend.banktransaction.internal.builder.body.data;

import com.project.banksystem.core.enums.fxrate.FxDirection;
import com.project.banksystem.core.enums.transaction.TransactionPrincipal;
import com.project.banksystem.core.enums.transaction.TransactionType;
import com.project.banksystem.infrastructure.model.Transaction;

public class TransactionCreateData {
    private TransactionType transactionType;
    private FxDirection fxDirection;
    private TransactionPrincipal transactionPrincipal;
    private Transaction optionalReversedOf;

    public TransactionCreateData(TransactionType transactionType,
                                 FxDirection fxDirection,
                                 TransactionPrincipal transactionPrincipal) {
        this.transactionType = transactionType;
        this.fxDirection = fxDirection;
        this.transactionPrincipal = transactionPrincipal;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        TransactionType transactionType;
        FxDirection fxDirection;
        TransactionPrincipal transactionPrincipal;

        public Builder transactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public Builder fxDirection(FxDirection fxDirection) {
            this.fxDirection = fxDirection;
            return this;
        }

        public Builder transactionPrincipal(TransactionPrincipal transactionPrincipal) {
            this.transactionPrincipal = transactionPrincipal;
            return this;
        }


        public TransactionCreateData build() {
            if (transactionType == null) {
                throw new IllegalStateException("Transaction type is null.");
            }

            if (fxDirection == null) {
                throw new IllegalStateException("Fx direction is null.");
            }

            if (transactionPrincipal == null) {
                throw new IllegalStateException("Transaction principal is null.");
            }

            return new TransactionCreateData(transactionType, fxDirection, transactionPrincipal);
        }
    }

    public void addOptionalReversedOfForContext(Transaction transaction) {
        this.optionalReversedOf = transaction;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public FxDirection getFxDirection() {
        return fxDirection;
    }

    public Transaction getOptionalReversedOf() {
        return optionalReversedOf;
    }

    public TransactionPrincipal getTransactionPrincipal() {
        return transactionPrincipal;
    }
}

