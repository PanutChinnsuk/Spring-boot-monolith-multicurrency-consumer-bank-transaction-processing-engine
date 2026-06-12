package com.project.banksystem.api.request.dto.banktransaction.admin;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public class AdminCardTransactionDTO {
    @NotNull
    private UUID baseCardId;

    @NotNull
    private UUID targetCardId;

    @NotNull
    @Positive
    private BigDecimal amount;

    public AdminCardTransactionDTO() {

    }

    public void setTargetCardId(UUID targetCardId) {
        this.targetCardId = targetCardId;
    }

    public void setBaseCardId(UUID baseCardId) {
        this.baseCardId = baseCardId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getTargetCardId() {
        return targetCardId;
    }

    public UUID getBaseCardId() {
        return baseCardId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
