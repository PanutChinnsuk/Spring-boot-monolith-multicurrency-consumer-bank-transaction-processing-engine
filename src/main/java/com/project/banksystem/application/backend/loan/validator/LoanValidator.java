package com.project.banksystem.application.backend.loan.validator;

import com.project.banksystem.core.enums.loan.LoanStatus;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanValidator {

    public void validateLoanClosed(final Loan loan) {
        LoanStatus loanStatus = loan.getLoanStatus();

        if (loanStatus == LoanStatus.CLOSED) {
            throw new BusinessException("Loan is closed.");
        }
    }
}
