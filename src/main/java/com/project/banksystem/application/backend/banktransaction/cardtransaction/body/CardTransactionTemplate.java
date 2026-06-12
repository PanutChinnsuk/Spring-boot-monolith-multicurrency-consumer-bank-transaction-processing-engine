package com.project.banksystem.application.backend.banktransaction.cardtransaction.body;

import com.project.banksystem.application.CurrentUserFieldsValidator;
import com.project.banksystem.infrastructure.annotation.clarification.NotRequiredForNonAdmin;
import com.project.banksystem.infrastructure.annotation.clarification.RequiredForAdmin;

import java.math.BigDecimal;
import java.util.UUID;

public class CardTransactionTemplate {
    @RequiredForAdmin
    @NotRequiredForNonAdmin
    private UUID baseCardId;

    @RequiredForAdmin
    @NotRequiredForNonAdmin
    private UUID targetCardId;

    @RequiredForAdmin
    @NotRequiredForNonAdmin
    private BigDecimal amount;

    public CardTransactionTemplate(UUID baseCardId,
                                   UUID targetCardId,
                                   BigDecimal amount) {
        this.baseCardId = baseCardId;
        this.targetCardId = targetCardId;
        this.amount = amount;

    }

    public void setValue(UUID baseCardId,
                         BigDecimal amount,
                         UUID targetCardId) {
        this.baseCardId = baseCardId;
        this.amount = amount;
        this.targetCardId = targetCardId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UUID baseCardId;
        UUID targetCardId;
        BigDecimal amount;

        public Builder baseCardId(UUID baseCardId) {
            this.baseCardId = baseCardId;
            return this;
        }

        public Builder targetCardId(UUID targetCardId) {
            this.targetCardId = targetCardId;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }


        public CardTransactionTemplate build() {
            if (!CurrentUserFieldsValidator.validForAdmin(baseCardId)) {
                throw new IllegalStateException("Base card id is null.");
            }

            if (!CurrentUserFieldsValidator.validForAdmin(targetCardId)) {
                throw new IllegalStateException("Target card id is null.");
            }

            if (!CurrentUserFieldsValidator.validForAdmin(amount)) {
                throw new IllegalStateException("Amount is null.");
            }

            return new CardTransactionTemplate(baseCardId, targetCardId, amount);
        }


    }

    public BigDecimal getAmount() {
        return amount;
    }

    public UUID getTargetCardId() {
        return targetCardId;
    }

    public UUID getBaseCardId() {
        return baseCardId;
    }
}
