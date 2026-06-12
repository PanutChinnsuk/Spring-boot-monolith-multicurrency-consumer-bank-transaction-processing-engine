package com.project.banksystem.application.backend.banktransaction.validator;

import com.project.banksystem.application.config.properties.SystemConfig;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Account;
import org.springframework.stereotype.Component;

@Component
public class TransferValidator {
    private final SystemConfig systemConfig;

    public TransferValidator(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }


    public void validateCanTransfer(final Account toAccount,
                                    final CurrencyCode currencyCode) {
        if (toAccount.getCurrencyCode() != systemConfig.getBaseCurrencyCurrencyCode()) {
            throw new BusinessException("Target account currency is not the same as" +
                    " base account currency for direct transfer.");
        }
    }
}
