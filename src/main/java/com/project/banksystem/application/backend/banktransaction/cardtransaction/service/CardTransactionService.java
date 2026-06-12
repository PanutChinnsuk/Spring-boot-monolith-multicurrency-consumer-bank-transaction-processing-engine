package com.project.banksystem.application.backend.banktransaction.cardtransaction.service;

import com.project.banksystem.application.backend.banktransaction.cardtransaction.body.CardTransactionTemplate;
import com.project.banksystem.infrastructure.model.Transaction;

public interface CardTransactionService {
    Transaction sellBaseCurrency(final CardTransactionTemplate cardTransactionTemplate);
    Transaction buyBaseCurrency(final CardTransactionTemplate cardTransactionTemplate);
    Transaction baseTransfer(final CardTransactionTemplate cardTransactionTemplate);
}
