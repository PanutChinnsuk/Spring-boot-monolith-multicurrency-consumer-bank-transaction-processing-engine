package com.project.banksystem.api.request.dto.fxrate;

import com.project.banksystem.core.enums.system.CurrencyCode;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class FxRateCreateDTO {
    @NotNull
    private CurrencyCode quoteCurrency;

    @NotNull
    @PositiveOrZero
    private BigDecimal bidRate;

    @NotNull
    @PositiveOrZero
    private BigDecimal askRate;

    @NotNull
    @PositiveOrZero
    private BigDecimal treasuryRate;

    public FxRateCreateDTO() {

    }

    public void setAskRate(BigDecimal askRate) {
        this.askRate = askRate;
    }


    public void setBidRate(BigDecimal bidRate) {
        this.bidRate = bidRate;
    }

    public void setQuoteCurrency(CurrencyCode quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public void setTreasuryRate(BigDecimal treasuryRate) {
        this.treasuryRate = treasuryRate;
    }

    public BigDecimal getAskRate() {
        return askRate;
    }

    public BigDecimal getBidRate() {
        return bidRate;
    }

    public CurrencyCode getQuoteCurrency() {
        return quoteCurrency;
    }

    public BigDecimal getTreasuryRate() {
        return treasuryRate;
    }
}
