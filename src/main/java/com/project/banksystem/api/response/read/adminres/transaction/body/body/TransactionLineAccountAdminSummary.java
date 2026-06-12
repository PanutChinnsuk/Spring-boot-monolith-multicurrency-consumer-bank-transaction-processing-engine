package com.project.banksystem.api.response.read.adminres.transaction.body.body;

import com.project.banksystem.core.enums.account.AccountType;
import com.project.banksystem.core.enums.system.CurrencyCode;

import java.util.UUID;

public class TransactionLineAccountAdminSummary {
    private UUID accountId;

    private AccountType accountType;

    private CurrencyCode currencyCode;


    public TransactionLineAccountAdminSummary(UUID accountId,
                                              AccountType accountType,
                                              CurrencyCode currencyCode) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.currencyCode = currencyCode;

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UUID accountId;
        AccountType accountType;
        CurrencyCode currencyCode;


        public Builder accountId(UUID accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder accountType(AccountType accountType) {
            this.accountType = accountType;
            return this;
        }

        public Builder currency(CurrencyCode currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }


        public TransactionLineAccountAdminSummary build() {
            return new TransactionLineAccountAdminSummary(accountId, accountType, currencyCode);
        }
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }


    public UUID getAccountId() {
        return accountId;
    }

    public AccountType getAccountType() {
        return accountType;
    }



}
