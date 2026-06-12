package com.project.banksystem.application.orchestrator.moneytransactionsession;

import com.project.banksystem.api.request.dto.paymentsession.LoanPaymentSessionRequestDTO;
import com.project.banksystem.api.request.dto.paymentsession.OverdraftPaymentSessionRequestDTO;
import com.project.banksystem.api.request.dto.paymentsession.PaymentSessionRequestDTO;
import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.backend.moneytransactionsession.loan.service.LoanPaymentSessionRequestService;
import com.project.banksystem.application.backend.moneytransactionsession.overdraft.service.OverdraftPaymentSessionRequestService;
import com.project.banksystem.application.backend.moneytransactionsession.payment.service.PaymentSessionRequestService;
import com.project.banksystem.core.enums.paymentsession.PaymentSessionType;
import com.project.banksystem.core.response.DataResult;
import com.project.banksystem.infrastructure.security.session.jwt.body.PaymentSessionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MoneyTransactionSessionRequestOrchestrator {
    private final PaymentSessionRequestService paymentSessionRequestService;
    private final OverdraftPaymentSessionRequestService overdraftPaymentSessionRequestService;
    private final LoanPaymentSessionRequestService loanPaymentSessionRequestService;

    public MoneyTransactionSessionRequestOrchestrator(PaymentSessionRequestService paymentSessionRequestService,
                                                      OverdraftPaymentSessionRequestService overdraftPaymentSessionRequestService,
                                                      LoanPaymentSessionRequestService loanPaymentSessionRequestService) {
        this.paymentSessionRequestService = paymentSessionRequestService;
        this.overdraftPaymentSessionRequestService = overdraftPaymentSessionRequestService;
        this.loanPaymentSessionRequestService = loanPaymentSessionRequestService;
    }



    @Transactional
    public DataResult<String> requestPaymentSession
            (final PaymentSessionRequestDTO paymentSessionRequestDTO,
             final String deviceIp) {
        return DataResultBuilder.build(
                paymentSessionRequestService.requestPaymentSession(paymentSessionRequestDTO,
                        deviceIp),
                "Successfully requested payment session."
        );
    }

    @Transactional
    public DataResult<String> requestOverdraftPaymentSession
            (final OverdraftPaymentSessionRequestDTO overdraftPaymentSessionRequestDTO,
             final String deviceIp) {
        return DataResultBuilder.build(
                overdraftPaymentSessionRequestService
                        .requestPaymentSession(overdraftPaymentSessionRequestDTO,
                        deviceIp),
                "Successfully requested overdraft payment session."
        );
    }

    @Transactional
    public DataResult<String> requestLoanPaymentSession
            (final LoanPaymentSessionRequestDTO loanPaymentSessionRequestDTO,
             final String deviceIp) {
        return DataResultBuilder.build(
                loanPaymentSessionRequestService
                        .requestPaymentSession(loanPaymentSessionRequestDTO,
                                deviceIp),
                "Successfully requested loan payment session."
        );
    }
}
