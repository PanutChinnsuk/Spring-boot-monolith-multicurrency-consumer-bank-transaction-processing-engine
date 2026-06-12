package com.project.banksystem.application.backend.banktransaction.body;

import com.project.banksystem.infrastructure.model.Account;

import java.math.BigDecimal;
import java.util.Map;

public class ConvertedCurrencyContext {
    private Map<Account, BigDecimal> converteds;

    public ConvertedCurrencyContext(Map<Account,BigDecimal> converteds) {
        this.converteds = converteds;
    }

    public Map<Account, BigDecimal> getConverteds() {
        return converteds;
    }
}
