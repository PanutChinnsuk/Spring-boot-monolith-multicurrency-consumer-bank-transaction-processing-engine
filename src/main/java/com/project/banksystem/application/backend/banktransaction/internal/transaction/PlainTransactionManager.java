package com.project.banksystem.application.backend.banktransaction.internal.transaction;

import com.project.banksystem.infrastructure.model.Transaction;
import com.project.banksystem.infrastructure.repository.TransactionRepository;
import org.springframework.stereotype.Component;



@Component
public class PlainTransactionManager {
    private final TransactionRepository transactionRepository;



    public PlainTransactionManager(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction save(final Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
