package com.project.banksystem.api.request.wrapper.moneytransactionsession;

import com.project.banksystem.api.request.dto.paymentsession.OverdraftPaymentSessionRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class OverdraftPaymentSessionRequest {

    @NotNull
    @Valid
    private OverdraftPaymentSessionRequestDTO overdraftPaymentSessionRequestDTO;

    public OverdraftPaymentSessionRequest() {

    }

    public void setOverdraftPaymentSessionRequestDTO(OverdraftPaymentSessionRequestDTO overdraftPaymentSessionRequestDTO) {
        this.overdraftPaymentSessionRequestDTO = overdraftPaymentSessionRequestDTO;
    }

    public OverdraftPaymentSessionRequestDTO getOverdraftPaymentSessionRequestDTO() {
        return overdraftPaymentSessionRequestDTO;
    }
}
