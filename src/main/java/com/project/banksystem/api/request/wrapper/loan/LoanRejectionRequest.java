package com.project.banksystem.api.request.wrapper.loan;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class LoanRejectionRequest {
    @NotNull
    private UUID loanRequestId;

    public LoanRejectionRequest() {

    }

    public void setLoanRequestId(UUID loanRequestId) {
        this.loanRequestId = loanRequestId;
    }

    public UUID getLoanRequestId() {
        return loanRequestId;
    }
}
