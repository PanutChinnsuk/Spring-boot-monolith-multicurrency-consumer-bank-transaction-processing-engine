package com.project.banksystem.application.backend.banktransaction.transaction.service;

import com.project.banksystem.infrastructure.model.Transaction;

public interface TransactionApprovalService {
    void postTransaction(Transaction transaction);
}
