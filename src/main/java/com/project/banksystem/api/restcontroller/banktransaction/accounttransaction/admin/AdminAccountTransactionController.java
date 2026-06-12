package com.project.banksystem.api.restcontroller.banktransaction.accounttransaction.admin;

import com.project.banksystem.api.request.wrapper.banktransaction.accounttransaction.AdminAccountTransactionRequest;
import com.project.banksystem.api.request.wrapper.banktransaction.accounttransaction.AdminCustomAccountTransactionRequest;
import com.project.banksystem.api.response.read.adminres.transaction.TransactionAdminResponse;
import com.project.banksystem.api.restcontroller.RestControllerResponder;
import com.project.banksystem.application.orchestrator.banktransaction.accounttransaction.AdminAccountTransactionOrchestrator;
import com.project.banksystem.core.response.DataResult;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAdmin;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAllRoles;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/transaction/account")
@AllowedAdmin
public class AdminAccountTransactionController {
    private final AdminAccountTransactionOrchestrator adminAccountTransactionOrchestrator;

    public AdminAccountTransactionController(AdminAccountTransactionOrchestrator adminAccountTransactionOrchestrator) {
        this.adminAccountTransactionOrchestrator = adminAccountTransactionOrchestrator;
    }

    @PostMapping("/transfer")
    public ResponseEntity<DataResult<TransactionAdminResponse>> transfer(@RequestBody @Valid AdminAccountTransactionRequest request) {
        return RestControllerResponder
                .successResponse(adminAccountTransactionOrchestrator
                        .transfer(request.getAdminAccountTransactionDTO()));
    }

    @PostMapping("/sell")
    public ResponseEntity<DataResult<TransactionAdminResponse>> sellBaseCurrency(@RequestBody @Valid AdminAccountTransactionRequest request) {
        return RestControllerResponder
                .successResponse(adminAccountTransactionOrchestrator
                        .sellBaseCurrency(request.getAdminAccountTransactionDTO()));
    }

    @PostMapping("/buy")
    public ResponseEntity<DataResult<TransactionAdminResponse>> buyBaseCurrency(@RequestBody @Valid AdminAccountTransactionRequest request) {
        return RestControllerResponder
                .successResponse(adminAccountTransactionOrchestrator
                        .buyBaseCurrency(request.getAdminAccountTransactionDTO()));
    }

    @PostMapping("/custom")
    public ResponseEntity<DataResult<TransactionAdminResponse>> customTransfer(@RequestBody @Valid AdminCustomAccountTransactionRequest request) {
        return RestControllerResponder
                .successResponse(adminAccountTransactionOrchestrator.customTransfer(
                        request.getCustomTransactionCreateDTO(),
                        request.getCustomTransactionLineCreateDTOS()));
    }



}
