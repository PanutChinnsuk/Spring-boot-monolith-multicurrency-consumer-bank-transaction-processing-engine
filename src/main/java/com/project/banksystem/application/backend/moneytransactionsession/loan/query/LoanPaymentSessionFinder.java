package com.project.banksystem.application.backend.moneytransactionsession.loan.query;

import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.LoanPaymentSession;
import com.project.banksystem.infrastructure.repository.LoanPaymentSessionRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LoanPaymentSessionFinder {
    private final LoanPaymentSessionRepository loanPaymentSessionRepository;

    public LoanPaymentSessionFinder(LoanPaymentSessionRepository loanPaymentSessionRepository) {
        this.loanPaymentSessionRepository = loanPaymentSessionRepository;
    }

    public LoanPaymentSession findForPaybackVerification(final UUID paymentSessionId) {
        return loanPaymentSessionRepository.findForPaybackVerification(paymentSessionId)
                .orElseThrow(() -> new NotFoundException("Loan payment session not found."));
    }
}
