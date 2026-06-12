package com.project.banksystem.api.restcontroller.banktransaction.cardtransaction.publicuse;

import com.project.banksystem.api.request.wrapper.banktransaction.cardtransaction.AdminCardTransactionRequest;
import com.project.banksystem.api.response.read.adminres.transaction.TransactionAdminResponse;
import com.project.banksystem.api.restcontroller.RestControllerResponder;
import com.project.banksystem.application.orchestrator.banktransaction.cardtransaction.AdminCardTransactionOrchestrator;
import com.project.banksystem.application.orchestrator.banktransaction.cardtransaction.PublicCardTransactionOrchestrator;
import com.project.banksystem.core.response.DataResult;
import com.project.banksystem.core.response.Result;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAdmin;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAllRoles;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/transaction/card")
@AllowedAllRoles
public class PublicCardTransactionController {
    private final PublicCardTransactionOrchestrator publicCardTransactionOrchestrator;

    public PublicCardTransactionController(PublicCardTransactionOrchestrator publicCardTransactionOrchestrator) {
        this.publicCardTransactionOrchestrator = publicCardTransactionOrchestrator;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Result> transfer() {
        return RestControllerResponder.successResponse(
                publicCardTransactionOrchestrator.transfer());
    }

    @PostMapping("/sell")
    public ResponseEntity<Result> sellBaseCurrency() {
        return RestControllerResponder.successResponse(
                publicCardTransactionOrchestrator.sellBaseCurrency());
    }

    @PostMapping("/buy")
    public ResponseEntity<Result> buyBaseCurrency() {
        return RestControllerResponder.successResponse(
                publicCardTransactionOrchestrator.buyBaseCurrency());
    }


}
