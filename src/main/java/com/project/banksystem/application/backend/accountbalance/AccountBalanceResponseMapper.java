package com.project.banksystem.application.backend.accountbalance;

import com.project.banksystem.api.response.read.adminres.account.body.AccountBalanceAdminSummary;
import com.project.banksystem.infrastructure.model.AccountBalance;

public final class AccountBalanceResponseMapper {
    private AccountBalanceResponseMapper() {

    }

    public static final AccountBalanceAdminSummary toAccountBalanceAdminSummary(final AccountBalance accountBalance) {
        return AccountBalanceAdminSummary.builder()
                .ledgerBalance(accountBalance.getLedgerBalance())
                .availableBalance(accountBalance.getAvailableBalance())
                .updatedAt(accountBalance.getUpdatedAt())
                .build();
    }

    public static final AccountBalanceAdminSummary toAdminDTO(AccountBalance accountBalance) {
        return AccountBalanceAdminSummary.builder()
                .availableBalance(accountBalance.getAvailableBalance())
                .ledgerBalance(accountBalance.getLedgerBalance())
                .updatedAt(accountBalance.getUpdatedAt())
                .build();
    }


}
