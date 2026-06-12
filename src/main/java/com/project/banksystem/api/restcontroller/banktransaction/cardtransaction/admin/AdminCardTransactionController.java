package com.project.banksystem.api.restcontroller.banktransaction.cardtransaction.admin;

import com.project.banksystem.api.request.wrapper.banktransaction.cardtransaction.AdminCardTransactionRequest;
import com.project.banksystem.api.response.read.adminres.transaction.TransactionAdminResponse;
import com.project.banksystem.api.restcontroller.RestControllerResponder;
import com.project.banksystem.application.orchestrator.banktransaction.cardtransaction.AdminCardTransactionOrchestrator;
import com.project.banksystem.core.response.DataResult;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAdmin;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/transaction/card")
@AllowedAdmin
public class AdminCardTransactionController {
    private final AdminCardTransactionOrchestrator adminCardTransactionOrchestrator;

    public AdminCardTransactionController(AdminCardTransactionOrchestrator adminCardTransactionOrchestrator) {
        this.adminCardTransactionOrchestrator = adminCardTransactionOrchestrator;
    }

    @PostMapping("/transfer")
    public ResponseEntity<DataResult<TransactionAdminResponse>> transfer(@RequestBody @Valid AdminCardTransactionRequest request) {
        return RestControllerResponder.successResponse(
                adminCardTransactionOrchestrator.transfer(request.getAdminCardTransactionDTO()));
    }

    @PostMapping("/sell")
    public ResponseEntity<DataResult<TransactionAdminResponse>> sellBaseCurrency(@RequestBody @Valid AdminCardTransactionRequest request) {
        return RestControllerResponder.successResponse(
                adminCardTransactionOrchestrator.sellBaseCurrency(request.getAdminCardTransactionDTO()));
    }

    @PostMapping("/buy")
    public ResponseEntity<DataResult<TransactionAdminResponse>> buyBaseCurrency(@RequestBody @Valid AdminCardTransactionRequest request) {
        return RestControllerResponder.successResponse(
                adminCardTransactionOrchestrator.buyBaseCurrency(request.getAdminCardTransactionDTO()));
    }
}
