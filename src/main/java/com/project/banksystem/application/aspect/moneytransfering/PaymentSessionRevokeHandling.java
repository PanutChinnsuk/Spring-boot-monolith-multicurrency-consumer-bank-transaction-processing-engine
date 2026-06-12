package com.project.banksystem.application.aspect.moneytransfering;

import com.project.banksystem.application.spring.SpringTransactionSynchronization;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.MoneyTransactionSession;
import com.project.banksystem.infrastructure.security.session.caching.caffeine.InMemoryCaffeineJwtTokenBlacklist;
import org.springframework.stereotype.Component;

@Component
public class PaymentSessionRevokeHandling {
    private final InMemoryCaffeineJwtTokenBlacklist inMemoryCaffeineJwtTokenBlacklist;

    public PaymentSessionRevokeHandling(InMemoryCaffeineJwtTokenBlacklist inMemoryCaffeineJwtTokenBlacklist) {
        this.inMemoryCaffeineJwtTokenBlacklist = inMemoryCaffeineJwtTokenBlacklist;
    }

    public void consumePaymentSession(final MoneyTransactionSession session,
                         final String token) {
        session.revokeSession();
        SpringTransactionSynchronization.runAfterCommit(
                () -> inMemoryCaffeineJwtTokenBlacklist.blacklistToken(token)
        );
    }


}

