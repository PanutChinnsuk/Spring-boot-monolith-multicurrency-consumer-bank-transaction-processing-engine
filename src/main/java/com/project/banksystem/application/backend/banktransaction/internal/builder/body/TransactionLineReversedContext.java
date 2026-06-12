package com.project.banksystem.application.backend.banktransaction.internal.builder.body;

import com.project.banksystem.application.backend.banktransaction.internal.builder.body.data.TransactionLineCreateData;

import java.math.BigDecimal;
import java.util.Map;

public class TransactionLineReversedContext {
    Map<TransactionLineCreateData, BigDecimal> previousConvertedAmount;

    public TransactionLineReversedContext(Map<TransactionLineCreateData, BigDecimal> previousConvertedAmount) {
        this.previousConvertedAmount = previousConvertedAmount;
    }


    public Map<TransactionLineCreateData, BigDecimal> getPreviousConvertedAmount() {
        return previousConvertedAmount;
    }
}
