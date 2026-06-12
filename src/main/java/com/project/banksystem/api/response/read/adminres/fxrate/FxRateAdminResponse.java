package com.project.banksystem.api.response.read.adminres.fxrate;

import com.project.banksystem.core.enums.system.CurrencyCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class FxRateAdminResponse {
    private UUID fxId;
    private CurrencyCode baseCurrency;
    private CurrencyCode quoteCurrency;
    private BigDecimal bidRate;
    private BigDecimal askRate;
    private BigDecimal midRate;
    private LocalDateTime timestamp;


    public FxRateAdminResponse(UUID fxId,
                               CurrencyCode baseCurrency,
                               CurrencyCode quoteCurrency,
                               BigDecimal bidRate,
                               BigDecimal askRate,
                               BigDecimal midRate,
                               LocalDateTime timestamp) {
        this.fxId = fxId;
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.bidRate = bidRate;
        this.askRate = askRate;
        this.midRate = midRate;
        this.timestamp = timestamp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UUID fxId;
        CurrencyCode baseCurrency;
        CurrencyCode quoteCurrency;
        BigDecimal bidRate;
        BigDecimal askRate;
        BigDecimal midRate;
        LocalDateTime timestamp;

        public Builder fxId(UUID fxId) {
            this.fxId = fxId;
            return this;
        }

        public Builder baseCurrency(CurrencyCode baseCurrency) {
            this.baseCurrency = baseCurrency;
            return this;
        }

        public Builder quoteCurrency(CurrencyCode quoteCurrency) {
            this.quoteCurrency = quoteCurrency;
            return this;
        }

        public Builder bidRate(BigDecimal bidRate) {
            this.bidRate = bidRate;
            return this;
        }

        public Builder askRate(BigDecimal askRate) {
            this.askRate = askRate;
            return this;
        }

        public Builder midRate(BigDecimal midRate) {
            this.midRate = midRate;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public FxRateAdminResponse build() {
            return new FxRateAdminResponse(fxId, baseCurrency, quoteCurrency, bidRate,
                    askRate, midRate, timestamp);
        }
    }

    public BigDecimal getMidRate() {
        return midRate;
    }

    public BigDecimal getBidRate() {
        return bidRate;
    }

    public CurrencyCode getQuoteCurrency() {
        return quoteCurrency;
    }

    public BigDecimal getAskRate() {
        return askRate;
    }

    public CurrencyCode getBaseCurrency() {
        return baseCurrency;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public UUID getFxId() {
        return fxId;
    }
}
