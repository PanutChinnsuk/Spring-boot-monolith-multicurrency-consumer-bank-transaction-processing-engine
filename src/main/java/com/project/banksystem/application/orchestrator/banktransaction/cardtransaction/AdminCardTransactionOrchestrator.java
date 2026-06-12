package com.project.banksystem.application.orchestrator.banktransaction.cardtransaction;

import com.project.banksystem.api.request.dto.banktransaction.admin.AdminCardTransactionDTO;
import com.project.banksystem.api.response.read.adminres.transaction.TransactionAdminResponse;
import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.backend.banktransaction.cardtransaction.body.CardTransactionTemplate;
import com.project.banksystem.application.backend.banktransaction.cardtransaction.service.CardTransactionService;
import com.project.banksystem.application.backend.banktransaction.internal.transaction.TransactionResponseMapper;
import com.project.banksystem.core.response.DataResult;
import com.project.banksystem.infrastructure.model.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
public class AdminCardTransactionOrchestrator {
    private final CardTransactionService cardTransactionService;

    public AdminCardTransactionOrchestrator(CardTransactionService cardTransactionService) {
        this.cardTransactionService = cardTransactionService;
    }


    private CardTransactionTemplate toTemplate(final AdminCardTransactionDTO dto) {
        return CardTransactionTemplate.builder()
                .baseCardId(dto.getBaseCardId())
                .targetCardId(dto.getTargetCardId())
                .amount(dto.getAmount())
                .build();
    }

    private DataResult<TransactionAdminResponse> response(final Function<CardTransactionTemplate, Transaction> function,
                                                          final AdminCardTransactionDTO dto,
                                                          final String message) {
        CardTransactionTemplate template = toTemplate(dto);

        Transaction transaction = function.apply(template);

        return DataResultBuilder.build(TransactionResponseMapper.toAdminResponse(transaction),
                message);
    }

    @Transactional
    public DataResult<TransactionAdminResponse> transfer(final AdminCardTransactionDTO adminCardTransactionDTO) {
       return response(cardTransactionService::baseTransfer,
               adminCardTransactionDTO,
               "Successfully requested card money transfer.");
    }

    @Transactional
    public DataResult<TransactionAdminResponse> sellBaseCurrency(final AdminCardTransactionDTO adminCardTransactionDTO) {
        return response(cardTransactionService::sellBaseCurrency,
                adminCardTransactionDTO,
                "Successfully requested card base to foreign money transfer.");
    }

    @Transactional
    public DataResult<TransactionAdminResponse> buyBaseCurrency(final AdminCardTransactionDTO adminCardTransactionDTO) {
        return response(cardTransactionService::buyBaseCurrency,
                adminCardTransactionDTO,
                "Successfully requested card foreign to base money transfer.");
    }
}
