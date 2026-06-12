package com.project.banksystem.application.backend.banktransaction.transaction.service;

import com.project.banksystem.core.enums.transaction.TransactionStatus;
import com.project.banksystem.infrastructure.model.Transaction;

public interface TransactionTerminationService {
    void terminateTransaction(Transaction transaction,
                              TransactionStatus transactionStatus);
}
