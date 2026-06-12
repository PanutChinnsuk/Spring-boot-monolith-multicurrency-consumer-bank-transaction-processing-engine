package com.project.banksystem.application.backend.moneytransactionsession.overdraft.query;

import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.OverdraftPaymentSession;
import com.project.banksystem.infrastructure.repository.OverdraftPaymentSessionRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OverdraftPaymentSessionFinder {
    private final OverdraftPaymentSessionRepository overdraftPaymentSessionRepository;

    public OverdraftPaymentSessionFinder(OverdraftPaymentSessionRepository overdraftPaymentSessionRepository) {
        this.overdraftPaymentSessionRepository = overdraftPaymentSessionRepository;
    }


    public OverdraftPaymentSession findForPaybackVerification(final UUID paymentSessionId) {
        return overdraftPaymentSessionRepository.findForPaybackVerification(paymentSessionId)
                .orElseThrow(() -> new NotFoundException("Overdraft payment session not found."));
    }
}
