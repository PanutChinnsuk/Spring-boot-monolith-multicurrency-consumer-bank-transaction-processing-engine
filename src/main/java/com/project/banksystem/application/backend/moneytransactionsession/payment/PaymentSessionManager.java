package com.project.banksystem.application.backend.moneytransactionsession.payment;

import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.PaymentSession;
import com.project.banksystem.infrastructure.repository.PaymentSessionRepository;
import org.springframework.stereotype.Component;

@Component
public class PaymentSessionManager {
    private final PaymentSessionRepository paymentSessionRepository;

    public PaymentSessionManager(PaymentSessionRepository paymentSessionRepository) {
        this.paymentSessionRepository = paymentSessionRepository;
    }


    public PaymentSession save(final PaymentSession paymentSession) {
        return paymentSessionRepository.save(paymentSession);
    }
}
