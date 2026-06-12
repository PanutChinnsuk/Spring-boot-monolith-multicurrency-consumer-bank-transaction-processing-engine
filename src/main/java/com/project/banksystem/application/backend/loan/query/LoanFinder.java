package com.project.banksystem.application.backend.loan.query;

import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.Loan;
import com.project.banksystem.infrastructure.repository.LoanRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LoanFinder {
    private final LoanRepository loanRepository;

    public LoanFinder(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan findForPaymentRequest(final UUID loanId) {
        return loanRepository.findForPaymentRequest(loanId)
                .orElseThrow(() -> new NotFoundException("Loan not found."));
    }
}
