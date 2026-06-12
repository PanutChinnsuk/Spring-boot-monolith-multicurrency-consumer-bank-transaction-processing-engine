package com.project.banksystem.application.backend.moneytransactionsession.loan.service;


import com.project.banksystem.api.request.dto.paymentsession.LoanPaymentSessionRequestDTO;

public interface LoanPaymentSessionRequestService {
    String requestPaymentSession(LoanPaymentSessionRequestDTO dto,
                                 String deviceIp);
}
