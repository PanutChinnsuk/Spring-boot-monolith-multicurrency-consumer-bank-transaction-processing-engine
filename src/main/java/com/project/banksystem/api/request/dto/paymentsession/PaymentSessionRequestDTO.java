package com.project.banksystem.api.request.dto.paymentsession;

import com.project.banksystem.api.request.dto.paymentsession.sharedinterface.MoneyTransactionRequestDTO;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.fieldsvalidation.Pin;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentSessionRequestDTO implements MoneyTransactionRequestDTO {
    @Pin
    private String pin;

    @NotNull
    @Positive
    private BigDecimal amount;

    @Nullable
    private UUID cardId;

    @Nullable
    private UUID accountId;

    @Nullable
    private UUID targetCardId;

    @Nullable
    private String targetAccountNumber;

    public PaymentSessionRequestDTO() {

    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCardId(@Nullable UUID cardId) {
        this.cardId = cardId;
    }

    public void setAccountId(@Nullable UUID accountId) {
        this.accountId = accountId;
    }

    public void setTargetCardId(@Nullable UUID targetCardId) {
        this.targetCardId = targetCardId;
    }

    public void setTargetAccountNumber(@Nullable String targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public UUID getCardId() {
        return cardId;
    }

    @Override
    public UUID getAccountId() {
        return accountId;
    }

    @Override
    public String getPin() {
        return pin;
    }

    @Nullable
    public UUID getTargetCardId() {
        return targetCardId;
    }

    @Nullable
    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }
}
