package com.project.banksystem.application.spring;

import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Arrays;

@Component
public final class SpringTransactionSynchronization {
    private SpringTransactionSynchronization() {

    }



    public static void runAfterCommit(Runnable... runnables) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                Arrays.stream(runnables).forEach(r -> r.run());
            }
        });
    }

    public static void runOnFailedCommit(Runnable... runnables) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if (status != STATUS_COMMITTED) {
                    Arrays.stream(runnables).forEach(r -> r.run());
                }
            }
        });
    }
}
