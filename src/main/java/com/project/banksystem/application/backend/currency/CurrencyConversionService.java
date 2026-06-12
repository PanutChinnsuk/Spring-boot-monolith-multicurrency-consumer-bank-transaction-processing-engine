package com.project.banksystem.application.backend.currency;

import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.core.enums.fxrate.FxDirection;
import com.project.banksystem.infrastructure.model.FxRate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

@Service
public class CurrencyConversionService {



    private BigDecimal getMultipliedFxRate(final BigDecimal amount,
                                          final BigDecimal rate,
                                          final CurrencyCode targetCurrency) {
        int rounding = Currency.getInstance(targetCurrency.name()).getDefaultFractionDigits();

        return amount.multiply(rate).setScale(rounding, RoundingMode.HALF_UP);
    }

    private BigDecimal getDividedFxRate(final BigDecimal amount,
                                          final BigDecimal rate,
                                          final CurrencyCode targetCurrency) {
        int rounding = Currency.getInstance(targetCurrency.name()).getDefaultFractionDigits();

        return amount.divide(rate, rounding, RoundingMode.HALF_UP);
    }

    public BigDecimal getConvertedAmountForAccount(FxRate fxRate,
                                                                    BigDecimal amount,
                                                                    FxDirection fxDirection,
                                                                    CurrencyCode targetCurrencyCode) {
        return getMultipliedFxRate(amount,
                fxRate.getCurrencyRate(fxDirection),
                targetCurrencyCode);
    }

    public BigDecimal getBaseAmountFromConvertedAmount(FxRate fxRate,
                                                   BigDecimal amount,
                                                   FxDirection fxDirection,
                                                   CurrencyCode targetCurrencyCode) {
        return getDividedFxRate(amount,
                fxRate.getCurrencyRate(fxDirection),
                targetCurrencyCode);
    }
}
