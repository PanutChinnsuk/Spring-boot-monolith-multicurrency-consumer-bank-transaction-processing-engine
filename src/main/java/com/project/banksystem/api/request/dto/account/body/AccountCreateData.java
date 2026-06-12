package com.project.banksystem.api.request.dto.account.body;

import com.project.banksystem.core.enums.account.AccountType;
import com.project.banksystem.core.enums.system.CurrencyCode;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AccountCreateData {
    @NotNull
    private AccountType accountType;

    @NotNull
    private CurrencyCode currencyCode;

    @NotNull
    private Boolean requiredApproval;

    @NotNull
    private LocalDateTime openedAt;


    public AccountCreateData() {

    }

    public void setCurrency(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setRequiredApproval(Boolean requiredApproval) {
        this.requiredApproval = requiredApproval;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void setOpenedAt(LocalDateTime openedAt) {
        this.openedAt = openedAt;
    }

    public Boolean getRequiredApproval() {
        return requiredApproval;
    }

    public AccountType getAccountType() {
        return accountType;
    }


    public LocalDateTime getOpenedAt() {
        return openedAt;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }
}
