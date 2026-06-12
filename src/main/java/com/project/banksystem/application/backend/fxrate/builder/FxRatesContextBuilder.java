package com.project.banksystem.application.backend.fxrate.builder;

import com.project.banksystem.application.backend.fxrate.query.FxRateFinder;
import com.project.banksystem.application.config.properties.SystemConfig;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.FxRate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FxRatesContextBuilder {
    private final FxRateFinder fxRateFinder;
    private final SystemConfig systemConfig;

    public FxRatesContextBuilder(FxRateFinder fxRateFinder,
                                 SystemConfig systemConfig) {
        this.fxRateFinder = fxRateFinder;
        this.systemConfig = systemConfig;
    }

    public Map<CurrencyCode, FxRate> getFxRatesForContext(final Account... nonBaseCurrencyAccounts) {
        Set<CurrencyCode> currencyCodes = Arrays.stream(nonBaseCurrencyAccounts)
                .filter(account -> account
                        .getCurrencyCode() != systemConfig.getBaseCurrencyCurrencyCode())
                .map(Account::getCurrencyCode).collect(Collectors.toSet());

        return fxRateFinder.findByQuoteCurrencySet(currencyCodes);

    }

}
