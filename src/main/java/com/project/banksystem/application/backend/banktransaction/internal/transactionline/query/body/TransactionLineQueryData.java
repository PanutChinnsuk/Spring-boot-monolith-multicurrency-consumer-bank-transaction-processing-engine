package com.project.banksystem.application.backend.banktransaction.internal.transactionline.query.body;

import com.project.banksystem.infrastructure.model.Transaction;
import com.project.banksystem.infrastructure.model.TransactionLine;

import java.util.List;

public record TransactionLineQueryData(List<TransactionLine> transactionLines,
                                       Transaction transaction) {
}
