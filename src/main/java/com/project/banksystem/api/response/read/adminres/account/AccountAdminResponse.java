package com.project.banksystem.api.response.read.adminres.account;

import com.project.banksystem.api.response.read.adminres.account.body.AccountBalanceAdminSummary;
import com.project.banksystem.api.response.read.adminres.customer.body.CustomerAdminSummary;
import com.project.banksystem.core.enums.account.AccountStatus;
import com.project.banksystem.core.enums.account.AccountType;
import com.project.banksystem.core.enums.system.CurrencyCode;

import java.time.LocalDateTime;
import java.util.UUID;

public class AccountAdminResponse {
    private UUID accountId;

    private AccountBalanceAdminSummary accountBalanceAdminSummary;

    private CustomerAdminSummary customerAdminSummary;

    private AccountType accountType;

    private CurrencyCode currencyCode;

    private AccountStatus accountStatus;

    private LocalDateTime openedAt;

    public AccountAdminResponse(UUID accountId,
                                AccountBalanceAdminSummary accountBalanceAdminSummary,
                                CustomerAdminSummary customerAdminSummary,
                                AccountType accountType,
                                CurrencyCode currencyCode,
                                AccountStatus accountStatus,
                                LocalDateTime openedAt) {
        this.accountId = accountId;
        this.accountBalanceAdminSummary = accountBalanceAdminSummary;
        this.customerAdminSummary = customerAdminSummary;
        this.accountType = accountType;
        this.currencyCode = currencyCode;
        this.accountStatus = accountStatus;
        this.openedAt = openedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UUID accountId;
        AccountBalanceAdminSummary accountBalanceAdminSummary;
        CustomerAdminSummary customerAdminSummary;
        AccountType accountType;
        CurrencyCode currencyCode;
        AccountStatus accountStatus;
        LocalDateTime openedAt;

        public Builder accountId(UUID accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder accountBalanceAdminSummary(AccountBalanceAdminSummary accountBalanceAdminSummary) {
            this.accountBalanceAdminSummary = accountBalanceAdminSummary;
            return this;
        }

        public Builder customerAdminSummary(CustomerAdminSummary customerAdminSummary) {
            this.customerAdminSummary = customerAdminSummary;
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

        public Builder accountStatus(AccountStatus accountStatus) {
            this.accountStatus = accountStatus;
            return this;
        }

        public Builder openedAt(LocalDateTime openedAt) {
            this.openedAt = openedAt;
            return this;
        }

        public AccountAdminResponse build() {
            return new AccountAdminResponse(accountId, accountBalanceAdminSummary, customerAdminSummary,
                    accountType, currencyCode, accountStatus, openedAt);
        }
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public AccountBalanceAdminSummary getAccountBalanceAdminSummary() {
        return accountBalanceAdminSummary;
    }

    public LocalDateTime getOpenedAt() {
        return openedAt;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public CustomerAdminSummary getCustomerAdminSummary() {
        return customerAdminSummary;
    }


}
