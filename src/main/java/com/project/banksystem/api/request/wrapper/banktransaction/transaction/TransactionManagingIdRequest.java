package com.project.banksystem.api.request.wrapper.banktransaction.transaction;

import java.util.UUID;

public class TransactionManagingIdRequest {
    private UUID transactionId;

    public TransactionManagingIdRequest() {

    }


    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public UUID getTransactionId() {
        return transactionId;
    }
}
