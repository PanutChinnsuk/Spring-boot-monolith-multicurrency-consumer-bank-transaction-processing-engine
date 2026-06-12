package com.project.banksystem.api.request.dto.paymentsession;

import com.project.banksystem.api.request.dto.paymentsession.sharedinterface.MoneyTransactionRequestDTO;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.fieldsvalidation.Pin;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public class OverdraftPaymentSessionRequestDTO implements MoneyTransactionRequestDTO {
    @Pin
    private String pin;

    @NotNull
    @Positive
    private BigDecimal paybackAmount;

    @NotNull
    private UUID overdraftId;

    public OverdraftPaymentSessionRequestDTO() {

    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setPaybackAmount(BigDecimal paybackAmount) {
        this.paybackAmount = paybackAmount;
    }

    public void setOverdraftId(UUID overdraftId) {
        this.overdraftId = overdraftId;
    }

    @Override
    public BigDecimal getAmount() {
        return paybackAmount;
    }

    @Override
    public UUID getCardId() {
        return null;
    }

    @Override
    public UUID getAccountId() {
        return null;
    }

    public UUID getOverdraftId() {
        return overdraftId;
    }

    @Override
    public String getPin() {
        return pin;
    }
}
