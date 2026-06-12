package com.project.banksystem.api.restcontroller.banktransaction.moneytransactionsession;


import com.project.banksystem.api.request.wrapper.moneytransactionsession.LoanPaymentSessionRequest;
import com.project.banksystem.api.request.wrapper.moneytransactionsession.OverdraftPaymentSessionRequest;
import com.project.banksystem.api.request.wrapper.moneytransactionsession.PaymentSessionRequest;
import com.project.banksystem.api.restcontroller.RestControllerResponder;
import com.project.banksystem.application.orchestrator.moneytransactionsession.MoneyTransactionSessionRequestOrchestrator;
import com.project.banksystem.core.response.DataResult;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAllRoles;
import com.project.banksystem.infrastructure.http.request.HttpServletRequestIpResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/payment-session")
@AllowedAllRoles
public class PublicMoneyTransactionSessionRequestController {
    private final MoneyTransactionSessionRequestOrchestrator moneyTransactionSessionRequestOrchestrator;

    public PublicMoneyTransactionSessionRequestController(MoneyTransactionSessionRequestOrchestrator moneyTransactionSessionRequestOrchestrator) {
        this.moneyTransactionSessionRequestOrchestrator = moneyTransactionSessionRequestOrchestrator;
    }



    @PostMapping("/request/transfer")
    public ResponseEntity<DataResult<String>> requestPaymentSession(@RequestBody @Valid
                                                                    PaymentSessionRequest r,
                                                                    HttpServletRequest httpServletRequest) {
        return RestControllerResponder.successResponse(moneyTransactionSessionRequestOrchestrator
                .requestPaymentSession(
                r.getPaymentSessionRequestDTO(),
                HttpServletRequestIpResolver.getDeviceIpFromRequest(httpServletRequest)));
    }

    @PostMapping("/request/overdraft")
    public ResponseEntity<DataResult<String>> requestOverdraftPaymentSession(@RequestBody @Valid
                                                                             OverdraftPaymentSessionRequest r,
                                                                             HttpServletRequest httpServletRequest) {
        return RestControllerResponder.successResponse(moneyTransactionSessionRequestOrchestrator
                .requestOverdraftPaymentSession(
                r.getOverdraftPaymentSessionRequestDTO(),
                HttpServletRequestIpResolver.getDeviceIpFromRequest(httpServletRequest)));
    }


    @PostMapping("/request/loan")
    public ResponseEntity<DataResult<String>> requestLoanPaymentSession(@RequestBody @Valid
                                                                            LoanPaymentSessionRequest r,
                                                                             HttpServletRequest httpServletRequest) {
        return RestControllerResponder.successResponse(moneyTransactionSessionRequestOrchestrator
                .requestLoanPaymentSession(
                        r.getLoanPaymentSessionRequestDTO(),
                        HttpServletRequestIpResolver.getDeviceIpFromRequest(httpServletRequest)));
    }
}
