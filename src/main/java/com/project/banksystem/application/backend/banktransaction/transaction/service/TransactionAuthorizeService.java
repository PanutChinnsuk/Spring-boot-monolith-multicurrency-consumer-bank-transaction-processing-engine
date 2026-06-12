package com.project.banksystem.application.backend.banktransaction.transaction.service;

import com.project.banksystem.infrastructure.model.Transaction;

public interface TransactionAuthorizeService {
    void authorizeTransaction(Transaction transaction);
}
