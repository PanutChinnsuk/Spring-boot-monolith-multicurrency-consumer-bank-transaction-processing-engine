package com.project.banksystem.api.request.dto.loan;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public class LoanRequestDTO {
    @NotNull
    @Positive
    private BigDecimal principalAmount;

    @NotNull
    @Positive
    private Integer termMonths;

    @NotNull
    private UUID accountId;

    public LoanRequestDTO() {

    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public void setPrincipalAmount(BigDecimal principalAmount) {
        this.principalAmount = principalAmount;
    }

    public void setTermMonths(Integer termMonths) {
        this.termMonths = termMonths;
    }

    public BigDecimal getPrincipalAmount() {
        return principalAmount;
    }

    public Integer getTermMonths() {
        return termMonths;
    }

    public UUID getAccountId() {
        return accountId;
    }
}
