package com.project.banksystem.application.backend.fxrate.calculator;

import com.project.banksystem.application.backend.fxrate.query.FxRateFinder;
import com.project.banksystem.application.config.properties.SystemConfig;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.infrastructure.model.FxRate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class FxProfitCalculator {


    public BigDecimal getFxRevenueTotalProfitAsBaseCurrency(final FxRate fxRate,
                                                            final BigDecimal fromAmount) {

        BigDecimal askRate = fxRate.getAskRate();
        BigDecimal bidRate = fxRate.getBidRate();
        BigDecimal profitRate = askRate.subtract(bidRate);

        return (fromAmount.multiply(profitRate)).divide(askRate, 4, RoundingMode.HALF_UP);
    }
}
