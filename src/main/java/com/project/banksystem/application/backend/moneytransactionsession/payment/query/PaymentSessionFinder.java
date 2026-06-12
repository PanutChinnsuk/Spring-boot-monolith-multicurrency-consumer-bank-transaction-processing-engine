package com.project.banksystem.application.backend.moneytransactionsession.payment.query;

import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.PaymentSession;
import com.project.banksystem.infrastructure.repository.PaymentSessionRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentSessionFinder {
    private final PaymentSessionRepository paymentSessionRepository;

    public PaymentSessionFinder(PaymentSessionRepository paymentSessionRepository) {
        this.paymentSessionRepository = paymentSessionRepository;
    }

    public PaymentSession findById(final UUID paymentSessionId) {
        return paymentSessionRepository.findByPaymentSessionId(paymentSessionId)
                .orElseThrow(() -> new NotFoundException("Payment session not found."));
    }
}
