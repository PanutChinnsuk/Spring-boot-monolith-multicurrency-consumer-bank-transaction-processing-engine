package com.project.banksystem.application.backend.banktransaction.internal.transaction.service;

import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionCreateData;
import com.project.banksystem.application.config.properties.TransactionConfig;
import com.project.banksystem.infrastructure.model.Transaction;
import com.project.banksystem.application.backend.banktransaction.internal.transaction.PlainTransactionManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class TransactionCreateService {
    private final PlainTransactionManager plainTransactionManager;
    private final TransactionConfig transactionConfig;

    public TransactionCreateService(PlainTransactionManager plainTransactionManager,
                                    TransactionConfig transactionConfig) {
        this.plainTransactionManager = plainTransactionManager;
        this.transactionConfig = transactionConfig;
    }


    public Transaction createMainTransaction(final TransactionCreateData transactionCreateData) {
        LocalDateTime expiresAt = LocalDateTime.now().plus(
                transactionConfig.getExpiresDay(), ChronoUnit.DAYS
        );

        return plainTransactionManager.save(Transaction.builder()
                .transactionType(transactionCreateData.getTransactionType())
                .fxDirection(transactionCreateData.getFxDirection())
                .transactionPrincipal(transactionCreateData.getTransactionPrincipal())
                .optionalReversedOf(transactionCreateData.getOptionalReversedOf())
                .expiresAt(expiresAt)
                .build());
    }
}
