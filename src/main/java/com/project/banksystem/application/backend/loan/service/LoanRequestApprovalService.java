package com.project.banksystem.application.backend.loan.service;


import com.project.banksystem.infrastructure.model.Loan;
import com.project.banksystem.infrastructure.model.LoanRequest;

import java.util.UUID;

public interface LoanRequestApprovalService {
    Loan approveLoanRequest(UUID loanRequestId);
    LoanRequest rejectLoanRequest(UUID loanRequestId);
}
