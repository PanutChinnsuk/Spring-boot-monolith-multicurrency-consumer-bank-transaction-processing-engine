package com.project.banksystem.api.request.wrapper.moneytransactionsession;

import com.project.banksystem.api.request.dto.paymentsession.LoanPaymentSessionRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class LoanPaymentSessionRequest {
    @NotNull
    @Valid
    private LoanPaymentSessionRequestDTO loanPaymentSessionRequestDTO;

    public LoanPaymentSessionRequest() {

    }

    public void setLoanPaymentSessionRequestDTO(LoanPaymentSessionRequestDTO loanPaymentSessionRequestDTO) {
        this.loanPaymentSessionRequestDTO = loanPaymentSessionRequestDTO;
    }

    public LoanPaymentSessionRequestDTO getLoanPaymentSessionRequestDTO() {
        return loanPaymentSessionRequestDTO;
    }
}
