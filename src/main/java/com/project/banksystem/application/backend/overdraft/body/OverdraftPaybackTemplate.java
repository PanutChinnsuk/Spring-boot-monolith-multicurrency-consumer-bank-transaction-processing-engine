package com.project.banksystem.application.backend.overdraft.body;

import com.project.banksystem.application.CurrentUserFieldsValidator;
import com.project.banksystem.infrastructure.annotation.clarification.NotRequiredForNonAdmin;
import com.project.banksystem.infrastructure.annotation.clarification.RequiredForAdmin;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.OverdraftedAccount;

import java.math.BigDecimal;

public class OverdraftPaybackTemplate {
    @RequiredForAdmin
    @NotRequiredForNonAdmin
    private BigDecimal paybackAmount;

    private Account account;

    @RequiredForAdmin
    @NotRequiredForNonAdmin
    private OverdraftedAccount overdraftedAccount;


    public OverdraftPaybackTemplate(BigDecimal paybackAmount,
                                    OverdraftedAccount overdraftedAccount) {
        this.paybackAmount = paybackAmount;
        this.overdraftedAccount = overdraftedAccount;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        BigDecimal paybackAmount;
        OverdraftedAccount overdraftedAccount;

        public Builder paybackAmount(BigDecimal paybackAmount) {
            this.paybackAmount = paybackAmount;
            return this;
        }

        public Builder overdraftedAccount(OverdraftedAccount overdraftedAccount) {
            this.overdraftedAccount = overdraftedAccount;
            return this;
        }

        public OverdraftPaybackTemplate build() {
            if (!CurrentUserFieldsValidator.validForAdmin(paybackAmount)) {
                throw new IllegalStateException("Payback amount is null.");
            }

            if (!CurrentUserFieldsValidator.validForAdmin(overdraftedAccount)) {
                throw new IllegalStateException("Overdrafted account is null.");
            }

            return new OverdraftPaybackTemplate(paybackAmount, overdraftedAccount);
        }
    }

    public void resolve(BigDecimal paybackAmount,
                        Account account,
                        OverdraftedAccount overdraftedAccount) {
        if (!account.getAccountId().equals(overdraftedAccount.getAccount().getAccountId())) {
            throw new IllegalStateException("Account used to payback should be the same from" +
                    " overdraft.");
        }

        this.paybackAmount = paybackAmount;
        this.account = account;
        this.overdraftedAccount = overdraftedAccount;
    }

    public OverdraftedAccount getOverdraftedAccount() {
        return overdraftedAccount;
    }

    public BigDecimal getPaybackAmount() {
        return paybackAmount;
    }

    public Account getAccount() {
        return account;
    }
}
