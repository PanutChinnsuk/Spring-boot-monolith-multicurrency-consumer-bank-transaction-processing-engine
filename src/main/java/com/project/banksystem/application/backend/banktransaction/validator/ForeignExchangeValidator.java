package com.project.banksystem.application.backend.banktransaction.validator;

import com.project.banksystem.application.config.properties.SystemConfig;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Account;
import org.springframework.stereotype.Component;

@Component
public class ForeignExchangeValidator {
    private final SystemConfig systemConfig;

    public ForeignExchangeValidator(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }


    public void validateCanBaseToForeign (final Account fromAccount,
                                              final Account toAccount) {
        if (fromAccount.getCurrencyCode() != systemConfig.getBaseCurrencyCurrencyCode()) {
            throw new BusinessException("Base account currency is not base currency.");
        }
        if (toAccount.getCurrencyCode() == systemConfig.getBaseCurrencyCurrencyCode()) {
            throw new BusinessException("Target account must be foreign currency.");
        }
    }

    public void validateCanForeignToBase(final Account fromAccount,
                                              final Account toAccount) {
        if (fromAccount.getCurrencyCode() == systemConfig.getBaseCurrencyCurrencyCode()) {
            throw new BusinessException("Base account currency is base currency.");
        }
        if (toAccount.getCurrencyCode() != systemConfig.getBaseCurrencyCurrencyCode()) {
            throw new BusinessException("Target account must be base currency.");
        }
    }
}
