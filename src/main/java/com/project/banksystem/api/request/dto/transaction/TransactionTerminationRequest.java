package com.project.banksystem.api.request.dto.transaction;

import com.project.banksystem.core.enums.transaction.TransactionStatus;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class TransactionTerminationRequest {
    @NotNull
    private UUID transactionId;

    @NotNull
    private TransactionStatus transactionStatus;


    public TransactionTerminationRequest() {

    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }
}
