package com.project.banksystem.api.restcontroller.banktransaction.accountoverdrafttransaction;

import com.project.banksystem.api.restcontroller.RestControllerResponder;
import com.project.banksystem.application.orchestrator.overdraft.PublicOverdraftPaymentOrchestrator;
import com.project.banksystem.core.response.Result;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAllRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/transaction/overdraft")
@AllowedAllRoles
public class OverdraftPaymentController {
    private final PublicOverdraftPaymentOrchestrator publicOverdraftPaymentOrchestrator;

    public OverdraftPaymentController(PublicOverdraftPaymentOrchestrator publicOverdraftPaymentOrchestrator) {
        this.publicOverdraftPaymentOrchestrator = publicOverdraftPaymentOrchestrator;
    }


    @GetMapping("/payback")
    public ResponseEntity<Result> payback() {
        return RestControllerResponder.successResponse(
                publicOverdraftPaymentOrchestrator.payback()
        );
    }
}
