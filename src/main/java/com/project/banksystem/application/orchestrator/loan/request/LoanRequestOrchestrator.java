package com.project.banksystem.application.orchestrator.loan.request;

import com.project.banksystem.api.request.dto.loan.LoanRequestDTO;
import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.backend.loan.request.service.LoanRequestService;
import com.project.banksystem.application.backend.loan.service.LoanRequestApprovalService;
import com.project.banksystem.core.response.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class LoanRequestOrchestrator {
    private final LoanRequestService loanRequestService;
    private final LoanRequestApprovalService loanRequestApprovalService;

    public LoanRequestOrchestrator(LoanRequestService loanRequestService,
                                   LoanRequestApprovalService loanRequestApprovalService) {
        this.loanRequestService = loanRequestService;
        this.loanRequestApprovalService = loanRequestApprovalService;
    }


    @Transactional
    public Result requestLoan(final LoanRequestDTO loanRequestDTO) {
        return DataResultBuilder.build("Successfully requested loan. " +
                loanRequestService.requestLoan(loanRequestDTO).getLoanRequestId());
    }

    @Transactional
    public Result approveLoanRequest(final UUID loanRequestId) {
        return DataResultBuilder.build("Successfully approved loan request. " +
                loanRequestApprovalService.approveLoanRequest(loanRequestId).getLoanId());
    }

    @Transactional
    public Result rejectLoanRequest(final UUID loanRequestId) {
        loanRequestApprovalService.rejectLoanRequest(loanRequestId);

        return DataResultBuilder.build("Successfully rejected loan request.");
    }
}
