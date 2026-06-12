package com.project.banksystem.api.request.dto.transaction;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class TransactionManagingRequest {
    @NotNull
    private UUID transactionId;


    public TransactionManagingRequest() {

    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }
}
