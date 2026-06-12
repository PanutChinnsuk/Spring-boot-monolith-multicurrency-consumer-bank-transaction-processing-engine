package com.project.banksystem.api.response.read.adminres.account.body;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class AccountBalanceAdminSummary {


    private BigDecimal ledgerBalance;
    private BigDecimal availableBalance;
    private LocalDateTime updatedAt;

    public AccountBalanceAdminSummary(BigDecimal ledgerBalance,
                                      BigDecimal availableBalance,
                                      LocalDateTime updatedAt) {
        this.ledgerBalance = ledgerBalance;
        this.availableBalance = availableBalance;
        this.updatedAt = updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        BigDecimal ledgerBalance;
        BigDecimal availableBalance;
        LocalDateTime updatedAt;




        public Builder ledgerBalance(BigDecimal ledgerBalance) {
            this.ledgerBalance = ledgerBalance;
            return this;
        }

        public Builder availableBalance(BigDecimal availableBalance) {
            this.availableBalance = availableBalance;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public AccountBalanceAdminSummary build() {
            return new AccountBalanceAdminSummary(ledgerBalance,
                    availableBalance, updatedAt);
        }

    }





    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public BigDecimal getLedgerBalance() {
        return ledgerBalance;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
