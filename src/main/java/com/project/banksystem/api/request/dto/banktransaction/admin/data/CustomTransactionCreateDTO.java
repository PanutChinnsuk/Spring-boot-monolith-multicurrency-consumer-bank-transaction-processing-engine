package com.project.banksystem.api.request.dto.banktransaction.admin.data;

import com.project.banksystem.core.enums.fxrate.FxDirection;
import com.project.banksystem.core.enums.transaction.TransactionPrincipal;
import com.project.banksystem.core.enums.transaction.TransactionType;
import jakarta.validation.constraints.NotNull;

public class CustomTransactionCreateDTO {
    @NotNull
    private TransactionType transactionType;

    @NotNull
    private FxDirection fxDirection;

    @NotNull
    private TransactionPrincipal transactionPrincipal;

    public CustomTransactionCreateDTO() {

    }

    public void setFxDirection(FxDirection fxDirection) {
        this.fxDirection = fxDirection;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setTransactionPrincipal(TransactionPrincipal transactionPrincipal) {
        this.transactionPrincipal = transactionPrincipal;
    }

    public TransactionPrincipal getTransactionPrincipal() {
        return transactionPrincipal;
    }

    public FxDirection getFxDirection() {
        return fxDirection;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}
