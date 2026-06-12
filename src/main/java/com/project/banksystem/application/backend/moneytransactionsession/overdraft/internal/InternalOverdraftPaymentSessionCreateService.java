package com.project.banksystem.application.backend.moneytransactionsession.overdraft.internal;

import com.project.banksystem.application.backend.moneytransactionsession.builder.MoneyTransactionSessionEntityBuilder;
import com.project.banksystem.application.backend.moneytransactionsession.overdraft.OverdraftPaymentSessionManager;
import com.project.banksystem.core.enums.jwt.TokenType;
import com.project.banksystem.infrastructure.model.OverdraftedAccount;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.OverdraftPaymentSession;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.entitybuilder.MoneyTransactionSessionEntityBuilderBody.Builder;

@Service
public class InternalOverdraftPaymentSessionCreateService {
    private final OverdraftPaymentSessionManager overdraftPaymentSessionManager;
    private final MoneyTransactionSessionEntityBuilder moneyTransactionSessionEntityBuilder;

    public InternalOverdraftPaymentSessionCreateService(OverdraftPaymentSessionManager overdraftPaymentSessionManager,
                                                        MoneyTransactionSessionEntityBuilder moneyTransactionSessionEntityBuilder) {
        this.overdraftPaymentSessionManager = overdraftPaymentSessionManager;
        this.moneyTransactionSessionEntityBuilder = moneyTransactionSessionEntityBuilder;
    }


    public OverdraftPaymentSession createPaymentSession(final User user,
                                                        final BigDecimal paybackAmount,
                                                        final String deviceIp,
                                                        final OverdraftedAccount overdraftedAccount,
                                                        final LocalDateTime expiryAsLocalDateTime) {
        Builder defaultBuilder =
                moneyTransactionSessionEntityBuilder.getDefaultBuilder(user, paybackAmount,
                        deviceIp, expiryAsLocalDateTime);

        return overdraftPaymentSessionManager.save(new OverdraftPaymentSession(
                defaultBuilder
                        .overdraftedAccount(overdraftedAccount)
                        .account(overdraftedAccount.getAccount())
                        .tokenType(TokenType.OVERDRAFT_ACCOUNT_PAYBACK)
                        .build()
        ));
    }
}
