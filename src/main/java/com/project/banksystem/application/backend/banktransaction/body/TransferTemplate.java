package com.project.banksystem.application.backend.banktransaction.body;

import com.project.banksystem.application.CurrentUserFieldsValidator;
import com.project.banksystem.infrastructure.annotation.clarification.NotRequiredForNonAdmin;
import com.project.banksystem.infrastructure.annotation.clarification.RequiredForAdmin;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Account;

import java.math.BigDecimal;

public class TransferTemplate {

    @RequiredForAdmin
    @NotRequiredForNonAdmin
    private Account toAccount;

    @RequiredForAdmin
    @NotRequiredForNonAdmin
    private Account fromAccount;

    @RequiredForAdmin
    @NotRequiredForNonAdmin
    private BigDecimal baseAmount;

    public TransferTemplate(Account fromAccount,
                            Account toAccount,
                            BigDecimal baseAmount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.baseAmount = baseAmount;
    }

    public static Builder builder() {
        return new Builder();
    }



    public static class Builder {
        Account fromAccount;
        Account toAccount;
        BigDecimal baseAmount;

        public Builder fromAccount(Account fromAccount) {
            this.fromAccount = fromAccount;
            return this;
        }

        public Builder toAccount(Account toAccount) {
            this.toAccount = toAccount;
            return this;
        }

        public Builder baseAmount(BigDecimal baseAmount) {
            this.baseAmount = baseAmount;
            return this;
        }
        
        public TransferTemplate build() {


            if (!CurrentUserFieldsValidator.validForAdmin(fromAccount)) {
                throw new IllegalStateException("Base account is null.");
            }
            if (!CurrentUserFieldsValidator.validForAdmin(toAccount)) {
                throw new IllegalStateException("Target account is null.");
            }
            if (!CurrentUserFieldsValidator.validForAdmin(baseAmount)) {
                throw new IllegalStateException("Base amount is null.");
            }

            return new TransferTemplate(fromAccount, toAccount, baseAmount);
         }
    }


    public void resolve(Account fromAccount,
                        BigDecimal baseAmount,
                        Account toAccount) {
        if (fromAccount.getAccountNumber().equals(toAccount.getAccountNumber())) {
            throw new BusinessException("From and to account should be different.");
        }

        this.fromAccount = fromAccount;
        this.baseAmount = baseAmount;
        this.toAccount = toAccount;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public Account getFromAccount() {
        return fromAccount;
    }
}
