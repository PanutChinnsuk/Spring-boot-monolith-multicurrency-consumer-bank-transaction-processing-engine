package com.project.banksystem.application.backend.loan.loanpayment.body;

import com.project.banksystem.application.CurrentUserFieldsValidator;
import com.project.banksystem.infrastructure.annotation.clarification.NotRequiredForNonAdmin;
import com.project.banksystem.infrastructure.annotation.clarification.RequiredForAdmin;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Loan;

import java.math.BigDecimal;
import java.util.UUID;

public class LoanPaymentTemplate {

    @RequiredForAdmin
    @NotRequiredForNonAdmin
    private Loan loan;

    @RequiredForAdmin
    @NotRequiredForNonAdmin
    private Account account;

    @RequiredForAdmin
    @NotRequiredForNonAdmin
    private BigDecimal amount;

    public LoanPaymentTemplate(BigDecimal amount,
                               Account account,
                               Loan loan) {
        this.amount = amount;
        this.account = account;
        this.loan = loan;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Loan loan;
        Account account;
        BigDecimal amount;

        public Builder loan(Loan loan) {
            this.loan = loan;
            return this;
        }

        public Builder account(Account account) {
            this.account = account;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public LoanPaymentTemplate build() {
            if (!CurrentUserFieldsValidator.validForAdmin(loan)) {
                throw new IllegalStateException("Loan is null.");
            }

            if (!CurrentUserFieldsValidator.validForAdmin(account)) {
                throw new IllegalStateException("Account is null.");
            }

            if (!CurrentUserFieldsValidator.validForAdmin(amount)) {
                throw new IllegalStateException("Amount is null.");
            }

            return new LoanPaymentTemplate(amount, account, loan);
        }
    }

    public void resolve(Loan loan,
                        Account account,
                        BigDecimal amount) {
        this.loan = loan;
        this.account = account;
        this.amount = amount;
    }

    public Loan getLoan() {
        return loan;
    }

    public Account getAccount() {
        return account;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
