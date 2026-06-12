package com.project.banksystem.application.backend.banktransaction.internal.transaction;

import com.project.banksystem.api.response.read.adminres.transaction.TransactionAdminResponse;
import com.project.banksystem.application.backend.banktransaction.internal.transactionline.TransactionLineResponseMapper;
import com.project.banksystem.infrastructure.model.Transaction;
import com.project.banksystem.infrastructure.model.TransactionLine;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

public final class TransactionResponseMapper {
    private TransactionResponseMapper() {

    }


    public static final TransactionAdminResponse toAdminResponse(final Transaction transaction) {
        Set<TransactionLine> transactionLines = transaction.getTransactionLines();
        UUID transactionId = transaction.getTransactionId();

        return TransactionAdminResponse.builder()
                .transactionId(transactionId)
                .transactionLineAdminSummaries(TransactionLineResponseMapper
                        .toTransactionLineAdminSummaries(transactionLines, transactionId))
                .transactionType(transaction.getTransactionType())
                .transactionStatus(transaction.getTransactionStatus())
                .initiatedAt(transaction.getInitiatedAt())
                .postedAt(transaction.getPostedAt())
                .build();
    }
}
