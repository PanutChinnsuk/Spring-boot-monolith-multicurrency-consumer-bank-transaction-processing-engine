package com.project.banksystem.application.orchestrator.banktransaction.transaction;

import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.backend.banktransaction.transaction.query.TransactionFinder;
import com.project.banksystem.application.backend.banktransaction.transaction.service.TransactionApprovalService;
import com.project.banksystem.application.backend.banktransaction.transaction.service.TransactionAuthorizeService;
import com.project.banksystem.application.backend.banktransaction.transaction.service.TransactionReverseService;
import com.project.banksystem.application.backend.banktransaction.transaction.service.TransactionTerminationService;
import com.project.banksystem.core.enums.transaction.TransactionStatus;
import com.project.banksystem.core.response.Result;
import com.project.banksystem.infrastructure.model.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class TransactionManagingOrchestrator {
    private final TransactionApprovalService transactionApprovalService;
    private final TransactionTerminationService transactionTerminationService;
    private final TransactionAuthorizeService transactionAuthorizeService;
    private final TransactionReverseService transactionReverseService;
    private final TransactionFinder transactionFinder;

    public TransactionManagingOrchestrator(TransactionApprovalService transactionApprovalService,
                                           TransactionTerminationService transactionTerminationService,
                                           TransactionAuthorizeService transactionAuthorizeService,
                                           TransactionReverseService transactionReverseService,
                                           TransactionFinder transactionFinder) {
        this.transactionAuthorizeService = transactionAuthorizeService;
        this.transactionApprovalService = transactionApprovalService;
        this.transactionReverseService = transactionReverseService;
        this.transactionTerminationService = transactionTerminationService;
        this.transactionFinder = transactionFinder;
    }

    public Transaction getNonClosedTransaction(final UUID transactionId) {
        return transactionFinder.findNonClosedById(transactionId);
    }

    public Transaction getPlain(final UUID transactionId) {
        return transactionFinder.findPlainById(transactionId);
    }

    private Result response(final UUID transactionId,
                            final Consumer<Transaction> voidFunction,
                            final String message) {
        Transaction transaction = getNonClosedTransaction(transactionId);
        voidFunction.accept(transaction);

        return DataResultBuilder.build(message);
    }

    @Transactional
    public Result authorizeTransaction(final UUID transactionId){
        return response(transactionId,
                t -> transactionAuthorizeService.authorizeTransaction(t),
                "Successfully authorized transaction.");
    }

    @Transactional
    public Result postTransaction(final UUID transactionId){
        return response(transactionId,
                t -> transactionApprovalService.postTransaction(t),
                "Successfully posted transaction.");
    }



    @Transactional
    public Transaction reverseTransaction(final UUID transactionId){
        return transactionReverseService
                .reverseTransaction(getNonClosedTransaction(transactionId));
    }

    @Transactional
    public Result terminateTransaction(final UUID transactionId,
                                       final TransactionStatus transactionStatus){
        Transaction transaction = getNonClosedTransaction(transactionId);
        transactionTerminationService.terminateTransaction(transaction, transactionStatus);

        return DataResultBuilder.build("Successfully terminated transaction.");
    }



}
