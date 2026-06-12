package com.project.banksystem.application.backend.fxrate.query;

import com.project.banksystem.application.config.properties.SystemConfig;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.FxRate;
import com.project.banksystem.infrastructure.repository.FxRateRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FxRateFinder {
    private final FxRateRepository fxRateRepository;
    private final SystemConfig systemConfig;

    public FxRateFinder(FxRateRepository fxRateRepository,
                        SystemConfig systemConfig) {
        this.fxRateRepository = fxRateRepository;
        this.systemConfig = systemConfig;
    }

    public FxRate findLatest(final CurrencyCode quoteCurrency) {
        return fxRateRepository.findLatest(systemConfig.getBaseCurrencyCurrencyCode().name(),
                        quoteCurrency.name()).orElseThrow(() -> new NotFoundException("Fx rate not found."));
    }

    public Map<CurrencyCode,FxRate> findByQuoteCurrencySet(Set<CurrencyCode> quoteCurrencies) {
        List<FxRate> fxRates = fxRateRepository.findByBaseCurrencyAndQuoteCurrencyIn(
                systemConfig.getBaseCurrencyCurrencyCode(),
                quoteCurrencies);

        if (fxRates.isEmpty()) {
            throw new NotFoundException("Fx rates not found.");
        }

        Map<CurrencyCode,FxRate> map = fxRates.stream()
                .collect(Collectors.toMap(
                        FxRate::getQuoteCurrency,
                        Function.identity(),
                        (a,b) -> a
                ));

        if (map.size() != quoteCurrencies.size()) {
            throw new NotFoundException("One or more fx rate not found.");
        }

        return map;
    }
}
