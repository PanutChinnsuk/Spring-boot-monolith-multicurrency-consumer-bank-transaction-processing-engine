package com.project.banksystem.application.orchestrator.banktransaction.accounttransaction;

import com.project.banksystem.api.request.dto.banktransaction.admin.AdminAccountTransactionDTO;
import com.project.banksystem.api.request.dto.banktransaction.admin.data.CustomTransactionCreateDTO;
import com.project.banksystem.api.request.dto.banktransaction.admin.data.CustomTransactionLineCreateDTO;
import com.project.banksystem.api.response.read.adminres.transaction.TransactionAdminResponse;
import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.backend.banktransaction.body.TransactionContext;
import com.project.banksystem.application.backend.banktransaction.body.TransferTemplate;
import com.project.banksystem.application.backend.banktransaction.builder.TransactionContextBuilder;
import com.project.banksystem.application.backend.banktransaction.internal.transaction.TransactionResponseMapper;
import com.project.banksystem.application.backend.banktransaction.query.TransactionPaymentMapper;
import com.project.banksystem.application.backend.banktransaction.service.AdminCustomTransactionService;
import com.project.banksystem.application.backend.banktransaction.service.ForeignExchangeTransferService;
import com.project.banksystem.application.backend.banktransaction.service.TransferService;
import com.project.banksystem.core.enums.transaction.TransactionPrincipal;
import com.project.banksystem.core.response.DataResult;
import com.project.banksystem.infrastructure.model.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.BiFunction;

@Service
public class AdminAccountTransactionOrchestrator {
    private final TransferService transferService;
    private final ForeignExchangeTransferService foreignExchangeTransferService;
    private final TransactionPaymentMapper transactionPaymentMapper;
    private final AdminCustomTransactionService adminCustomTransactionService;

    public AdminAccountTransactionOrchestrator(TransferService transferService,
                                                ForeignExchangeTransferService foreignExchangeTransferService,
                                                TransactionPaymentMapper transactionPaymentMapper,
                                                AdminCustomTransactionService adminCustomTransactionService) {
        this.transferService = transferService;
        this.foreignExchangeTransferService = foreignExchangeTransferService;
        this.transactionPaymentMapper = transactionPaymentMapper;
        this.adminCustomTransactionService = adminCustomTransactionService;
    }

    private TransferTemplate toTemplate(final AdminAccountTransactionDTO adminAccountTransactionDTO) {
        return transactionPaymentMapper.adminTransferTemplate(
                adminAccountTransactionDTO.getFromAccountNumber(),
                adminAccountTransactionDTO.getTargetAccountNumber(),
                adminAccountTransactionDTO.getAmount()
        );
    }

    private DataResult<TransactionAdminResponse> response(final Transaction transaction,
                                                          final String message) {
        TransactionAdminResponse response = TransactionResponseMapper.toAdminResponse(transaction);

        return DataResultBuilder.build(response, message);
    }

    private DataResult<TransactionAdminResponse> wrappedResponse(final BiFunction<TransferTemplate, TransactionContext,
            Transaction> function, final AdminAccountTransactionDTO dto, final String message) {

        Transaction transaction = function.apply(toTemplate(dto),
                TransactionContextBuilder.getFor(TransactionPrincipal.ACCOUNT_FOR_TRANSACTION));

        return response(transaction, message);
    }

    @Transactional
    public DataResult<TransactionAdminResponse> transfer(final AdminAccountTransactionDTO adminAccountTransactionDTO) {
        return wrappedResponse(transferService::transfer,
                adminAccountTransactionDTO, "Successfully requested money transfer.");
    }

    @Transactional
    public DataResult<TransactionAdminResponse> sellBaseCurrency(final AdminAccountTransactionDTO adminAccountTransactionDTO) {
        return wrappedResponse(foreignExchangeTransferService::sellBaseCurrency,
                adminAccountTransactionDTO,
                "Successfully requested base to foreign money transfer.");
    }

    @Transactional
    public DataResult<TransactionAdminResponse> buyBaseCurrency(final AdminAccountTransactionDTO adminAccountTransactionDTO) {
        return wrappedResponse(foreignExchangeTransferService::buyBaseCurrency,
                adminAccountTransactionDTO,
                "Successfully requested foreign to base money transfer.");
    }

    @Transactional
    public DataResult<TransactionAdminResponse> customTransfer(final CustomTransactionCreateDTO customTransactionCreateDTO,
                                                               final List<CustomTransactionLineCreateDTO> customTransactionLineCreateDTOS) {
        Transaction transaction = adminCustomTransactionService.customTransaction(
                customTransactionCreateDTO,
                customTransactionLineCreateDTOS);

        return response(transaction, "Successfully requested custom money transfer.");
    }
}
