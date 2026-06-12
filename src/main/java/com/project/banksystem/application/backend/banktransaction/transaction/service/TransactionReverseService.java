package com.project.banksystem.application.backend.banktransaction.transaction.service;

import com.project.banksystem.infrastructure.model.Transaction;

public interface TransactionReverseService {
    Transaction reverseTransaction(Transaction transaction);
}
