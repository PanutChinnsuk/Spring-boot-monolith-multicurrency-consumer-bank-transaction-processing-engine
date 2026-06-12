package com.project.banksystem.application.backend.fxrate.body;

import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.model.FxRate;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class FxRatesContext {
    private Map<CurrencyCode, FxRate> fxRates = Collections.emptyMap();


    public FxRatesContext(Map<CurrencyCode, FxRate> fxRates) {
        this.fxRates = fxRates;
    }

    public Map<CurrencyCode, FxRate> getFxRates() {
        return fxRates;
    }
}
