package com.project.banksystem.api.restcontroller.loan.payment;

import com.project.banksystem.api.request.wrapper.loan.LoanPaymentRequest;
import com.project.banksystem.api.restcontroller.RestControllerResponder;
import com.project.banksystem.application.orchestrator.loan.payment.PublicLoanPaymentOrchestrator;
import com.project.banksystem.core.response.Result;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAllRoles;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/loan/payment")
@AllowedAllRoles
public class PublicLoanPaymentController {
    private final PublicLoanPaymentOrchestrator publicLoanPaymentOrchestrator;

    public PublicLoanPaymentController(PublicLoanPaymentOrchestrator publicLoanPaymentOrchestrator) {
        this.publicLoanPaymentOrchestrator = publicLoanPaymentOrchestrator;
    }


    @PostMapping("/account")
    public ResponseEntity<Result> payLoanViaAccount() {
        return RestControllerResponder
                .successResponse(
                        publicLoanPaymentOrchestrator.payLoanViaAccount()
                );
    }
}
