package com.project.banksystem.application.backend.moneytransactionsession.loan;

import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.LoanPaymentSession;
import com.project.banksystem.infrastructure.repository.LoanPaymentSessionRepository;
import org.springframework.stereotype.Component;

@Component
public class LoanPaymentSessionManager {
    private final LoanPaymentSessionRepository loanPaymentSessionRepository;

    public LoanPaymentSessionManager(LoanPaymentSessionRepository loanPaymentSessionRepository) {
        this.loanPaymentSessionRepository = loanPaymentSessionRepository;
    }

    public LoanPaymentSession save(final LoanPaymentSession loanPaymentSession) {
        return loanPaymentSessionRepository.save(loanPaymentSession);
    }
}
