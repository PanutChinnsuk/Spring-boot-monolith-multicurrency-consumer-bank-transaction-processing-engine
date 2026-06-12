package com.project.banksystem.api.request.wrapper.moneytransactionsession;

import com.project.banksystem.api.request.dto.paymentsession.PaymentSessionRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class PaymentSessionRequest {
    @Valid
    @NotNull
    private PaymentSessionRequestDTO paymentSessionRequestDTO;

    public PaymentSessionRequest() {}

    public void setPaymentSessionRequestDTO(PaymentSessionRequestDTO paymentSessionRequestDTO) {
        this.paymentSessionRequestDTO = paymentSessionRequestDTO;
    }

    public PaymentSessionRequestDTO getPaymentSessionRequestDTO() {
        return paymentSessionRequestDTO;
    }
}
