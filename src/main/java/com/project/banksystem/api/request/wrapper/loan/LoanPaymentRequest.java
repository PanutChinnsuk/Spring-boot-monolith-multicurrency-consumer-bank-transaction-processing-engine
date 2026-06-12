package com.project.banksystem.api.request.wrapper.loan;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public class LoanPaymentRequest {
    @NotNull
    private UUID loanId;

    @NotNull
    private UUID accountId;

    @NotNull
    @Positive
    private BigDecimal amount;


    public LoanPaymentRequest() {

    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setLoanId(UUID loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public UUID getLoanId() {
        return loanId;
    }
}
