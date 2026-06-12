package com.project.banksystem.application.backend.moneytransactionsession.builder;

import com.project.banksystem.core.enums.jwt.TokenType;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Card;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.PaymentSession;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.entitybuilder.MoneyTransactionSessionEntityBuilderBody;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.entitybuilder.MoneyTransactionSessionEntityBuilderBody.Builder;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class MoneyTransactionSessionEntityBuilder {


    public Builder getDefaultBuilder(final User user,
                              final BigDecimal amount,
                              final String deviceIp,
                              final LocalDateTime expiryAsLocalDateTime) {
      return MoneyTransactionSessionEntityBuilderBody.builder()
              .user(user)
              .amount(amount)
              .deviceIp(deviceIp)
              .expiresAt(expiryAsLocalDateTime);
    }
}
