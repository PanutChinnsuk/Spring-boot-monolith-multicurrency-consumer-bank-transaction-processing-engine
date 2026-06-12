package com.project.banksystem.application.backend.loan.validator;

import com.project.banksystem.core.enums.loan.LoanRequestStatus;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.LoanRequest;
import org.springframework.stereotype.Component;

@Component
public class LoanRequestStatusValidator {


    public void assertPending(final LoanRequest loanRequest) {
        LoanRequestStatus status = loanRequest.getLoanRequestStatus();
        if (status != LoanRequestStatus.PENDING) {
            throw new BusinessException("Loan request must be at pending state.");
        }
    }


}
