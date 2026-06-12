package com.project.banksystem.infrastructure.security.session.jwt.body;

import com.project.banksystem.core.enums.paymentsession.PaymentSessionType;

public class PaymentSessionContext {
    private PaymentSessionType paymentSessionType;

    public PaymentSessionContext(PaymentSessionType paymentSessionType) {
        this.paymentSessionType = paymentSessionType;
    }

    public boolean isLoanPayment() {
        return paymentSessionType == PaymentSessionType.LOAN_PAYMENT;
    }
}
