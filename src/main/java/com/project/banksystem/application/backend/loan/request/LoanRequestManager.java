package com.project.banksystem.application.backend.loan.request;

import com.project.banksystem.infrastructure.model.LoanRequest;
import com.project.banksystem.infrastructure.repository.LoanRequestRepository;
import org.springframework.stereotype.Component;

@Component
public class LoanRequestManager {
    private final LoanRequestRepository loanRequestRepository;

    public LoanRequestManager(LoanRequestRepository loanRequestRepository) {
        this.loanRequestRepository = loanRequestRepository;
    }

    public LoanRequest save(final LoanRequest loanRequest) {
        return loanRequestRepository.save(loanRequest);
    }
}
