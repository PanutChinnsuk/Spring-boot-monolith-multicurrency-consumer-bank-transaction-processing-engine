package com.project.banksystem.application.backend.loan.request.query;

import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.LoanRequest;
import com.project.banksystem.infrastructure.repository.LoanRequestRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LoanRequestFinder {
    private final LoanRequestRepository loanRequestRepository;

    public LoanRequestFinder(LoanRequestRepository loanRequestRepository) {
        this.loanRequestRepository = loanRequestRepository;
    }


    public LoanRequest findForApprove(final UUID loanRequestId) {
        return loanRequestRepository.findForApprove(loanRequestId)
                .orElseThrow(() -> new NotFoundException("Loan request not found."));
    }
}
