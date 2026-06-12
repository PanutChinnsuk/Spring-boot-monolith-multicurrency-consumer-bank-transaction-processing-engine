package com.project.banksystem.application.backend.banktransaction.service;

import com.project.banksystem.application.backend.banktransaction.body.TransactionContext;
import com.project.banksystem.application.backend.banktransaction.body.TransferTemplate;
import com.project.banksystem.infrastructure.model.Transaction;

public interface TransferService {
    Transaction transfer(TransferTemplate transferTemplate,
                                TransactionContext transactionContext);
}
