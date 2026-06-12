package com.project.banksystem.api.request.dto.account.body;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class AccountBalanceCreateData {
    @NotNull
    @PositiveOrZero
    private BigDecimal ledgerBalance;

    @NotNull
    @PositiveOrZero
    private BigDecimal availableBalance;

    public AccountBalanceCreateData() {

    }

    public void setLedgerBalance(BigDecimal ledgerBalance) {
        this.ledgerBalance = ledgerBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public BigDecimal getLedgerBalance() {
        return ledgerBalance;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }
}
