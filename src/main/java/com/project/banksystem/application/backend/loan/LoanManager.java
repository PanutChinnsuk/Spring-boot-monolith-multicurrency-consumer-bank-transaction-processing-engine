package com.project.banksystem.application.backend.loan;

import com.project.banksystem.infrastructure.model.Loan;
import com.project.banksystem.infrastructure.repository.LoanRepository;
import org.springframework.stereotype.Component;

@Component
public class LoanManager {
    private final LoanRepository loanRepository;

    public LoanManager(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }


    public Loan save(final Loan loan) {
        return loanRepository.save(loan);
    }
}
