package com.project.banksystem.application.backend.loan.loanpayment;

import com.project.banksystem.infrastructure.model.LoanPayment;
import com.project.banksystem.infrastructure.repository.LoanPaymentRepository;
import org.springframework.stereotype.Component;

@Component
public class LoanPaymentManager {
    private final LoanPaymentRepository loanPaymentRepository;

    public LoanPaymentManager(LoanPaymentRepository loanPaymentRepository) {
        this.loanPaymentRepository = loanPaymentRepository;
    }

    public LoanPayment save(final LoanPayment loanPayment) {
        return loanPaymentRepository.save(loanPayment);
    }
}
