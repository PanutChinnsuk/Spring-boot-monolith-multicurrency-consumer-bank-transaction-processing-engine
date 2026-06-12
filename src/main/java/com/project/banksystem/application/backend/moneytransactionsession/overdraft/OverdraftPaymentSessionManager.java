package com.project.banksystem.application.backend.moneytransactionsession.overdraft;

import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.OverdraftPaymentSession;
import com.project.banksystem.infrastructure.repository.OverdraftPaymentSessionRepository;
import org.springframework.stereotype.Component;

@Component
public class OverdraftPaymentSessionManager {
    private final OverdraftPaymentSessionRepository overdraftPaymentSessionRepository;

    public OverdraftPaymentSessionManager(OverdraftPaymentSessionRepository overdraftPaymentSessionRepository) {
        this.overdraftPaymentSessionRepository = overdraftPaymentSessionRepository;
    }

    public OverdraftPaymentSession save(final OverdraftPaymentSession overdraftPaymentSession) {
        return overdraftPaymentSessionRepository.save(overdraftPaymentSession);
    }
}
