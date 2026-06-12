package com.project.banksystem.api.request.wrapper.loan;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class LoanApprovalRequest {
    @NotNull
    private UUID loanRequestId;

    public LoanApprovalRequest() {

    }

    public void setLoanRequestId(UUID loanRequestId) {
        this.loanRequestId = loanRequestId;
    }

    public UUID getLoanRequestId() {
        return loanRequestId;
    }
}
