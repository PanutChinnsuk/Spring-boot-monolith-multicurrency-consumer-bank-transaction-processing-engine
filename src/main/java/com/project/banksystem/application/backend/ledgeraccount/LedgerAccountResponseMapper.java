package com.project.banksystem.application.backend.ledgeraccount;

import com.project.banksystem.api.response.read.adminres.transaction.body.body.TransactionLineLedgerAccountAdminSummary;
import com.project.banksystem.infrastructure.model.LedgerAccount;

public final class LedgerAccountResponseMapper {
    private LedgerAccountResponseMapper() {

    }


    public static final TransactionLineLedgerAccountAdminSummary
    toTransactionLineLedgerAccountAdminSummary(final LedgerAccount ledgerAccount) {
        return TransactionLineLedgerAccountAdminSummary.builder()
                .ledgerAccountId(ledgerAccount.getLedgerAccountId())
                .name(ledgerAccount.getName())
                .ledgerType(ledgerAccount.getLedgerType())
                .currency(ledgerAccount.getCurrencyCode())
                .code(ledgerAccount.getCode())
                .build();

    }
}
