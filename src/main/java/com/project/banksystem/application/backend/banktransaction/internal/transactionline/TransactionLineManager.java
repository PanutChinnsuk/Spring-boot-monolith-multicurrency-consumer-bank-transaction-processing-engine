package com.project.banksystem.application.backend.banktransaction.internal.transactionline;

import com.project.banksystem.infrastructure.model.TransactionLine;
import com.project.banksystem.infrastructure.repository.TransactionLineRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionLineManager {
    private final TransactionLineRepository transactionLineRepository;

    public TransactionLineManager(TransactionLineRepository transactionLineRepository) {
        this.transactionLineRepository = transactionLineRepository;
    }

    public List<TransactionLine> saveAll(final List<TransactionLine> transactionLines) {
        return transactionLineRepository.saveAll(transactionLines);
    }
}
