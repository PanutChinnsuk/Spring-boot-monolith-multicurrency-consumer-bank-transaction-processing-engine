package com.project.banksystem.application.orchestrator.banktransaction.accounttransaction;

import com.project.banksystem.api.request.dto.banktransaction.publicuse.PublicAccountTransactionDTO;
import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.backend.banktransaction.body.TransactionContext;
import com.project.banksystem.application.backend.banktransaction.body.TransferTemplate;
import com.project.banksystem.application.backend.banktransaction.builder.TransactionContextBuilder;
import com.project.banksystem.application.backend.banktransaction.query.TransactionPaymentMapper;
import com.project.banksystem.application.backend.banktransaction.service.ForeignExchangeTransferService;
import com.project.banksystem.application.backend.banktransaction.service.TransferService;
import com.project.banksystem.core.enums.transaction.TransactionPrincipal;
import com.project.banksystem.core.response.Result;
import com.project.banksystem.infrastructure.model.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.BiFunction;

@Service
public class PublicAccountTransactionOrchestrator {
    private final TransferService transferService;
    private final ForeignExchangeTransferService foreignExchangeTransferService;
    private final TransactionPaymentMapper transactionPaymentMapper;

    public PublicAccountTransactionOrchestrator(TransferService transferService,
                                                ForeignExchangeTransferService foreignExchangeTransferService,
                                                TransactionPaymentMapper transactionPaymentMapper) {
        this.transferService = transferService;
        this.foreignExchangeTransferService = foreignExchangeTransferService;
        this.transactionPaymentMapper = transactionPaymentMapper;
    }

    private TransferTemplate getTemplate() {
        return transactionPaymentMapper.publicTransferTemplate();
    }

    private Result response(final BiFunction<TransferTemplate, TransactionContext,
            Transaction> function, final String message) {

        function.apply(getTemplate(), TransactionContextBuilder
                .getFor(TransactionPrincipal.ACCOUNT_FOR_TRANSACTION));

        return DataResultBuilder.build(message);
    }

    @Transactional
    public Result transfer() {
        return response(transferService::transfer,
                "Successfully requested money transfer.");
    }

    @Transactional
    public Result sellBaseCurrency() {
        return response(foreignExchangeTransferService::sellBaseCurrency,
                "Successfully requested base to foreign money transfer.");
    }

    @Transactional
    public Result buyBaseCurrency() {
        return response(foreignExchangeTransferService::buyBaseCurrency,
                "Successfully requested foreign to base money transfer.");
    }
}
