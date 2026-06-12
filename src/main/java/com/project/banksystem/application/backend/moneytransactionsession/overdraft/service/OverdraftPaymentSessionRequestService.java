package com.project.banksystem.application.backend.moneytransactionsession.overdraft.service;

import com.project.banksystem.api.request.dto.paymentsession.OverdraftPaymentSessionRequestDTO;

public interface OverdraftPaymentSessionRequestService {
    String requestPaymentSession(OverdraftPaymentSessionRequestDTO dto,
                                 String deviceIp);
}
