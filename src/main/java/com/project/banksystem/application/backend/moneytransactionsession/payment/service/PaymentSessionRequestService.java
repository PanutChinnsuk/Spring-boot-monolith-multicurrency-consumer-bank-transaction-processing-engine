package com.project.banksystem.application.backend.moneytransactionsession.payment.service;

import com.project.banksystem.api.request.dto.paymentsession.PaymentSessionRequestDTO;
import com.project.banksystem.api.request.dto.paymentsession.sharedinterface.MoneyTransactionRequestDTO;
import com.project.banksystem.infrastructure.security.session.jwt.body.PaymentSessionContext;

public interface PaymentSessionRequestService {
    String requestPaymentSession(PaymentSessionRequestDTO dto,
                                 String deviceIp);
}
