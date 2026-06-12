package com.project.banksystem.application.backend.accountbalance;

import com.project.banksystem.application.config.properties.SystemConfig;
import com.project.banksystem.infrastructure.model.Account;
import org.springframework.stereotype.Component;

@Component
public class BaseCurrencyCompare {
    private final SystemConfig systemConfig;


    public BaseCurrencyCompare(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }


    public boolean isAccountBasedCurrency(final Account account) {
        return account.getCurrencyCode() == systemConfig.getBaseCurrencyCurrencyCode();
    }
}
