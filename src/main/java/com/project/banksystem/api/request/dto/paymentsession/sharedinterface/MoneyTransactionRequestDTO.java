package com.project.banksystem.api.request.dto.paymentsession.sharedinterface;

import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.util.UUID;

public interface MoneyTransactionRequestDTO {
    BigDecimal getAmount();

    UUID getCardId();

    UUID getAccountId();

    String getPin();
}
