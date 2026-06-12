package com.project.banksystem.application.backend.banktransaction.internal.builder;

import com.project.banksystem.application.backend.banktransaction.body.TransactionContext;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionCreateData;
import com.project.banksystem.core.enums.fxrate.FxDirection;
import com.project.banksystem.core.enums.transaction.TransactionType;

public final class TransactionDataBuilder {
    private TransactionDataBuilder() {

    }


    public static TransactionCreateData createTransactionData(final TransactionType transactionType,
                                                              final FxDirection fxDirection,
                                                              final TransactionContext transactionContext) {


        return TransactionCreateData.builder()
                .transactionType(TransactionType.TRANSFER)
                .fxDirection(fxDirection)
                .transactionPrincipal(transactionContext.getTransactionPrincipal())
                .build();
    }
}
