package com.project.banksystem.api.request.dto.banktransaction.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class AdminAccountTransactionDTO {

    @NotBlank
    private String fromAccountNumber;

    @NotBlank
    private String targetAccountNumber;

    @NotNull
    @Positive
    private BigDecimal amount;

    public AdminAccountTransactionDTO() {

    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public void setTargetAccountNumber(String targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }

    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }
}
