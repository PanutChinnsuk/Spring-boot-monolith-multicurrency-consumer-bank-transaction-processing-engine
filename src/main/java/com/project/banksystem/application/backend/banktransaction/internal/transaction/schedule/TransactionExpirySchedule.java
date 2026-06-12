package com.project.banksystem.application.backend.banktransaction.internal.transaction.schedule;

import com.project.banksystem.core.enums.transaction.TransactionStatus;
import com.project.banksystem.infrastructure.repository.TransactionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class TransactionExpirySchedule {
    private final TransactionRepository transactionRepository;

    public TransactionExpirySchedule(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Scheduled(fixedDelay = 1 * 60 * 60 * 1000)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void setExpired() {
        transactionRepository.expiresAllUnusedPendings(
                TransactionStatus.EXPIRED,
                LocalDateTime.now(),
                TransactionStatus.PENDING
        );
    }
}
