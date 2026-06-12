package com.project.banksystem.api.restcontroller.banktransaction.transaction;

import com.project.banksystem.api.request.dto.transaction.TransactionManagingRequest;
import com.project.banksystem.api.request.dto.transaction.TransactionTerminationRequest;
import com.project.banksystem.api.restcontroller.RestControllerResponder;
import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.orchestrator.banktransaction.transaction.TransactionManagingOrchestrator;
import com.project.banksystem.core.response.Result;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAdmin;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/transaction/management")
@AllowedAdmin
public class TransactionManagingController {
    private final TransactionManagingOrchestrator transactionManagingOrchestrator;

    public TransactionManagingController(TransactionManagingOrchestrator transactionManagingOrchestrator) {
        this.transactionManagingOrchestrator = transactionManagingOrchestrator;
    }


    @PostMapping("/authorize")
    public ResponseEntity<Result> authorizeTransaction(@RequestBody @Valid
                                                       TransactionManagingRequest r) {
        return RestControllerResponder
                .successResponse(transactionManagingOrchestrator.authorizeTransaction(
                        r.getTransactionId()
                ));
    }

    @PostMapping("/post")
    public ResponseEntity<Result> postTransaction(@RequestBody @Valid
                                                       TransactionManagingRequest r) {
        return RestControllerResponder
                .successResponse(transactionManagingOrchestrator.postTransaction(
                        r.getTransactionId()
                ));
    }

    @PostMapping("/reverse")
    public ResponseEntity<Result> reverseTransaction(@RequestBody @Valid
                                                  TransactionManagingRequest r) {
        transactionManagingOrchestrator.reverseTransaction(
                r.getTransactionId());
        return RestControllerResponder.successResponse(
                DataResultBuilder.build("Successfully reversed a transaction."));
    }

    @PostMapping("/terminate")
    public ResponseEntity<Result> terminateTransaction(@RequestBody @Valid
                                                       TransactionTerminationRequest r) {
        return RestControllerResponder
                .successResponse(transactionManagingOrchestrator.terminateTransaction(
                        r.getTransactionId(),
                        r.getTransactionStatus()
                ));
    }
}
