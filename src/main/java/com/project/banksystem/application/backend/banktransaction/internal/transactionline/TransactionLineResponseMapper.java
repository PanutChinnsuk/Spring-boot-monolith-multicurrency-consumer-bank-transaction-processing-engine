package com.project.banksystem.application.backend.banktransaction.internal.transactionline;

import com.project.banksystem.api.response.read.adminres.transaction.body.TransactionLineAdminSummary;
import com.project.banksystem.application.backend.account.AccountResponseMapper;
import com.project.banksystem.application.backend.ledgeraccount.LedgerAccountResponseMapper;
import com.project.banksystem.infrastructure.model.TransactionLine;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public final class TransactionLineResponseMapper {
    private TransactionLineResponseMapper() {

    }

    public static final Set<TransactionLineAdminSummary>
    toTransactionLineAdminSummaries(final Set<TransactionLine> transactionLines,
                                    final UUID transactionId) {
        return transactionLines.stream().map(tl -> {
            return TransactionLineAdminSummary.builder()
                    .transactionId(transactionId)
                    .lineId(tl.getLineId())

                    .transactionLineAccountAdminSummary(AccountResponseMapper
                            .toTransactionLineAccountAdminSummary(tl.getAccount()))

                    .transactionLineLedgerAccountAdminSummary(LedgerAccountResponseMapper
                            .toTransactionLineLedgerAccountAdminSummary(tl.getLedgerAccount()))

                    .transactionLineDirection(tl.getTransactionLineDirection())
                    .amount(tl.getAmount())
                    .build();
        }).collect(Collectors.toSet());
    }
}
