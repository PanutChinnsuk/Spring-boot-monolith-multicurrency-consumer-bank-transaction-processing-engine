package com.project.banksystem.api.restcontroller.banktransaction.accounttransaction.publicuse;


import com.project.banksystem.api.restcontroller.RestControllerResponder;
import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.orchestrator.banktransaction.accounttransaction.PublicAccountTransactionOrchestrator;
import com.project.banksystem.core.response.Result;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAllRoles;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/transaction/account")
@AllowedAllRoles
public class PublicAccountTransactionController {
    private final PublicAccountTransactionOrchestrator publicAccountTransactionOrchestrator;

    public PublicAccountTransactionController(PublicAccountTransactionOrchestrator publicAccountTransactionOrchestrator) {
        this.publicAccountTransactionOrchestrator = publicAccountTransactionOrchestrator;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Result> transfer() {
        publicAccountTransactionOrchestrator.transfer();

        return RestControllerResponder
                .successResponse(DataResultBuilder
                        .build("Successfully submitted account transaction."));
    }
}
