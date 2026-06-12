package com.project.banksystem.application.backend.moneytransactionsession.payment.internal;

import com.project.banksystem.application.backend.moneytransactionsession.builder.MoneyTransactionSessionEntityBuilder;
import com.project.banksystem.application.backend.moneytransactionsession.payment.PaymentSessionManager;
import com.project.banksystem.core.enums.jwt.TokenType;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Card;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.PaymentSession;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.entitybuilder.MoneyTransactionSessionEntityBuilderBody;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;


import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.entitybuilder.MoneyTransactionSessionEntityBuilderBody.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class InternalPaymentSessionCreateService {
    private final PaymentSessionManager paymentSessionManager;
    private final MoneyTransactionSessionEntityBuilder moneyTransactionSessionEntityBuilder;

    public InternalPaymentSessionCreateService(PaymentSessionManager paymentSessionManager,
                                               MoneyTransactionSessionEntityBuilder moneyTransactionSessionEntityBuilder) {
        this.paymentSessionManager = paymentSessionManager;
        this.moneyTransactionSessionEntityBuilder = moneyTransactionSessionEntityBuilder;
    }




    public PaymentSession createPaymentSession(final User user,
                                               final BigDecimal amount,
                                               final String deviceIp,
                                               final @Nullable Card card,
                                               final @Nullable Account account,
                                               final @Nullable Card targetCard,
                                               final @Nullable Account targetAccount,
                                               final LocalDateTime expiryAsLocalDateTime) {
        TokenType tokenType = card == null
                ? TokenType.ACCOUNT_PAYMENT
                : TokenType.CARD_PAYMENT;

        Builder defaultBuilder = moneyTransactionSessionEntityBuilder
               .getDefaultBuilder(user, amount, deviceIp, expiryAsLocalDateTime);

        return paymentSessionManager.save(new PaymentSession(defaultBuilder
                .card(card)
                .account(account)
                .targetCard(targetCard)
                .targetAccount(targetAccount)
                .tokenType(tokenType)
                .build()));
    }
}
