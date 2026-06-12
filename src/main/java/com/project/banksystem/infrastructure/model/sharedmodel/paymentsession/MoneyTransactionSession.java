package com.project.banksystem.infrastructure.model.sharedmodel.paymentsession;

import com.project.banksystem.core.enums.jwt.TokenType;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Card;
import com.project.banksystem.infrastructure.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface MoneyTransactionSession {
    LocalDateTime getCreatedAt();

    BigDecimal getAmount();

    User getUser();

    Boolean getRevoked();

    LocalDateTime getExpiresAt();

    String getDeviceIp();

    TokenType getTokenType();

    UUID getPaymentSessionId();

    Account getAccount();

    Card getCard();

    public void revokeSession();
}
