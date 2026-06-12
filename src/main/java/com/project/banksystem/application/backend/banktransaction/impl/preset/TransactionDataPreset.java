package com.project.banksystem.application.backend.banktransaction.impl.preset;

import com.project.banksystem.application.backend.banktransaction.body.TransactionContext;
import com.project.banksystem.application.backend.banktransaction.internal.builder.TransactionDataBuilder;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionCreateData;
import com.project.banksystem.core.enums.fxrate.FxDirection;
import com.project.banksystem.core.enums.transaction.TransactionType;
import org.springframework.stereotype.Component;

@Component
public class TransactionDataPreset {



    public TransactionCreateData getDefault(TransactionContext transactionContext,
                                                             FxDirection fxDirection,
                                                             TransactionType transactionType) {
        return TransactionDataBuilder.createTransactionData(
                transactionType,
                fxDirection,
                transactionContext
        );
    }
}
