package com.project.banksystem.application.backend.banktransaction.transaction.query;

import com.project.banksystem.application.backend.banktransaction.internal.transaction.validator.TransactionValidator;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.Transaction;
import com.project.banksystem.infrastructure.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransactionFinder {
    private final TransactionRepository transactionRepository;
    private final TransactionValidator transactionValidator;

    public TransactionFinder(TransactionRepository transactionRepository,
                             TransactionValidator transactionValidator) {
        this.transactionRepository = transactionRepository;
        this.transactionValidator = transactionValidator;
    }

    private Transaction id(final UUID transactionId) {
        return transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction not found."));
    }

    public Transaction findPlainById(final UUID transactionId) {
        return id(transactionId);
    }

    public Transaction findNonClosedById(final UUID transactionId) {
        Transaction transaction = id(transactionId);

        transactionValidator.validateIsTransactionClosed(transaction);

        return transaction;
    }
}
