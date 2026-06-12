package com.project.banksystem.api.request.dto.account;

import com.project.banksystem.api.request.dto.account.body.AccountBalanceCreateData;
import com.project.banksystem.api.request.dto.account.body.AccountCreateData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class AccountCreateDTO {
    @NotNull
    private UUID customerId;

    @NotNull
    @Valid
    private AccountCreateData accountCreateData;

    @NotNull
    @Valid
    private AccountBalanceCreateData accountBalanceCreateData;

    public AccountCreateDTO() {

    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public void setAccountBalanceCreateData(AccountBalanceCreateData accountBalanceCreateData) {
        this.accountBalanceCreateData = accountBalanceCreateData;
    }

    public void setAccountCreateData(AccountCreateData accountCreateData) {
        this.accountCreateData = accountCreateData;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public AccountBalanceCreateData getAccountBalanceCreateData() {
        return accountBalanceCreateData;
    }

    public AccountCreateData getAccountCreateData() {
        return accountCreateData;
    }
}
