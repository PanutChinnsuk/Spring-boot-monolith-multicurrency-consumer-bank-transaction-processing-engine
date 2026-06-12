package com.project.banksystem.application.backend.banktransaction.internal.transactionline.validator.body;

import com.project.banksystem.core.enums.transaction.TransactionLineDirection;

import java.math.BigDecimal;

public class TotalCreditAndDebit {
    private TransactionLineDirection transactionLineDirection;
    private BigDecimal amountOfBaseCurrency;

    public TotalCreditAndDebit(TransactionLineDirection transactionLineDirection,
                               BigDecimal amountOfBaseCurrency) {
        this.transactionLineDirection = transactionLineDirection;
        this.amountOfBaseCurrency = amountOfBaseCurrency;
    }

    public TransactionLineDirection getTransactionLineDirection() {
        return transactionLineDirection;
    }

    public BigDecimal getAmountOfBaseCurrency() {
        return amountOfBaseCurrency;
    }
}
