package com.project.banksystem.application.backend.banktransaction.impl.preset;

import com.project.banksystem.application.backend.banktransaction.internal.builder.TransactionLineDataBuilder;
import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionLineCreateData;
import com.project.banksystem.application.backend.ledgeraccount.map.LedgerMap;
import com.project.banksystem.core.enums.ledger.LedgerKey;
import com.project.banksystem.core.enums.transaction.BalanceImpact;
import com.project.banksystem.core.enums.transaction.TransactionLineDirection;
import com.project.banksystem.infrastructure.model.Account;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TransferLineDataPreset {



    public List<TransactionLineCreateData> defaultNonFxTransfer(final BigDecimal amount,
                                                                final Account fromAccount,
                                                                final Account toAccount) {
        String transferCode = LedgerMap.getLedgerCodeByKey(LedgerKey.TRANSFER);

        List<TransactionLineCreateData> lines = List.of(
                TransactionLineDataBuilder.createTransactionData(
                        fromAccount,
                        amount,
                        TransactionLineDirection.DEBIT,
                        transferCode,
                        BalanceImpact.RESERVE_DEBIT
                ),
                TransactionLineDataBuilder.createTransactionData(
                        toAccount,
                        amount,
                        TransactionLineDirection.CREDIT,
                        transferCode,
                        BalanceImpact.NO_CHECK
                )
        );


        return lines;
    }
}
