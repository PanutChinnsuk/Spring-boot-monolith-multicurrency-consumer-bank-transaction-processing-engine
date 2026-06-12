package com.project.banksystem.application.orchestrator.banktransaction.cardtransaction;

import com.project.banksystem.api.request.dto.banktransaction.admin.AdminCardTransactionDTO;
import com.project.banksystem.api.request.dto.banktransaction.publicuse.PublicCardTransactionDTO;
import com.project.banksystem.api.response.read.adminres.transaction.TransactionAdminResponse;
import com.project.banksystem.application.backend.DataResultBuilder;
import com.project.banksystem.application.backend.banktransaction.cardtransaction.body.CardTransactionTemplate;
import com.project.banksystem.application.backend.banktransaction.cardtransaction.service.CardTransactionService;
import com.project.banksystem.application.backend.banktransaction.internal.transaction.TransactionResponseMapper;
import com.project.banksystem.core.response.DataResult;
import com.project.banksystem.core.response.Result;
import com.project.banksystem.infrastructure.model.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
public class PublicCardTransactionOrchestrator {
    private final CardTransactionService cardTransactionService;

    public PublicCardTransactionOrchestrator(CardTransactionService cardTransactionService) {
        this.cardTransactionService = cardTransactionService;
    }


    private CardTransactionTemplate toTemplate() {
        return CardTransactionTemplate.builder()
                .build();
    }

    private Result response(final Function<CardTransactionTemplate, Transaction> function,
                            final String message) {
        CardTransactionTemplate template = toTemplate();
        function.apply(template);

        return DataResultBuilder.build(message);
    }

    @Transactional
    public Result transfer() {

       return response(cardTransactionService::baseTransfer,
               "Successfully submitted card money transfer request.");
    }

    @Transactional
    public Result sellBaseCurrency() {
        return response(cardTransactionService::sellBaseCurrency,
                "Successfully submitted card base to foriegn money transfer request.");
    }

    @Transactional
    public Result buyBaseCurrency() {
        return response(cardTransactionService::buyBaseCurrency,
                "Successfully submitted card foriegn to base money transfer request.");
    }
}
