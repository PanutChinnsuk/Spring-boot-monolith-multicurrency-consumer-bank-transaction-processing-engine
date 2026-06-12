package com.project.banksystem.application.backend.banktransaction.internal.builder;

import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionLineCreateData;
import com.project.banksystem.core.enums.transaction.BalanceImpact;
import com.project.banksystem.core.enums.transaction.TransactionLineDirection;
import com.project.banksystem.infrastructure.model.Account;

import java.math.BigDecimal;

public final class TransactionLineDataBuilder {
    private TransactionLineDataBuilder() {

    }


    public static TransactionLineCreateData createTransactionData(final Account account,
                                                                  final BigDecimal amount,
                                                                  final TransactionLineDirection transactionLineDirection,
                                                                  final String ledgerCode,
                                                                  final BalanceImpact balanceImpact) {
        return TransactionLineCreateData.builder()
                .account(account)
                .amount(amount)
                .direction(transactionLineDirection)
                .ledgerCode(ledgerCode)
                .balanceImpact(balanceImpact)
                .build();
    }
}
