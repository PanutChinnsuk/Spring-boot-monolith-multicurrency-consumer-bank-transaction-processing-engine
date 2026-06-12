package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.core.enums.fxrate.FxDirection;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "fx_rates", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"base_currency", "quote_currency", "timestamp"})
})
@Immutable
public class FxRate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "fx_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID fxId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "base_currency", nullable = false, updatable = false, columnDefinition = "CHAR(3)")
    private CurrencyCode baseCurrency;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "quote_currency", nullable = false, updatable = false, columnDefinition = "CHAR(3)")
    private CurrencyCode quoteCurrency;

    @NotNull
    @PositiveOrZero
    @Column(name = "bid_rate", nullable = false, updatable = false, precision = 19, scale = 8)
    private BigDecimal bidRate;

    @NotNull
    @PositiveOrZero
    @Column(name = "ask_rate", nullable = false, updatable = false, precision = 19, scale = 8)
    private BigDecimal askRate;

    @NotNull
    @PositiveOrZero
    @Column(name = "mid_rate", nullable = false, updatable = false, precision = 19, scale = 8)
    private BigDecimal midRate;

    @NotNull
    @PositiveOrZero
    @Column(name = "treasury_rate", nullable = false, updatable = false, precision = 19, scale = 8)
    private BigDecimal treasuryRate;

    @NotNull
    @Column(name = "timestamp", nullable = false, updatable = false, unique = true)
    private LocalDateTime timestamp;

    protected FxRate() {

    }

    public FxRate(CurrencyCode baseCurrency,
                  CurrencyCode quoteCurrency,
                  BigDecimal bidRate,
                  BigDecimal askRate,
                  BigDecimal treasuryRate,
                  LocalDateTime timestamp) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.bidRate = bidRate;
        this.askRate = askRate;
        this.treasuryRate = treasuryRate;
        this.timestamp = timestamp;
    }

    @PreUpdate
    private void preventUpdate() {
        throw new IllegalStateException("Model is immutable.");
    }

    @PrePersist
    private void defaultValue() {
        this.midRate = (this.bidRate.add(this.askRate))
                        .divide(BigDecimal.valueOf(2), 8, RoundingMode.HALF_UP);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        CurrencyCode baseCurrency;
        CurrencyCode quoteCurrency;
        BigDecimal bidRate;
        BigDecimal askRate;
        BigDecimal treasuryRate;
        LocalDateTime timestamp;

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

        public Builder treasuryRate(BigDecimal treasuryRate) {
            this.treasuryRate = treasuryRate;
            return this;
        }


        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public FxRate build() {
            if (askRate.compareTo(bidRate) < 0) {
                throw new IllegalStateException("Fx ask rate can't be lower than bid rate.");
            }

            return new FxRate(baseCurrency, quoteCurrency, bidRate, askRate, treasuryRate,
                    timestamp);
        }
    }




    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public BigDecimal getAskRate() {
        return askRate;
    }

    public BigDecimal getBidRate() {
        return bidRate;
    }

    public BigDecimal getMidRate() {
        return midRate;
    }

    public BigDecimal getCurrencyRate(final FxDirection fxDirection) {
        if (fxDirection == FxDirection.ASK) {
            return this.askRate;
        }else if (fxDirection == FxDirection.BID) {
            return this.bidRate;
        } else if (fxDirection == FxDirection.TREASURY) {
            return this.treasuryRate;
        }

        throw new IllegalStateException("Failed to get currency rate.");
    }

    public CurrencyCode getBaseCurrency() {
        return baseCurrency;
    }

    public CurrencyCode getQuoteCurrency() {
        return quoteCurrency;
    }

    public UUID getFxId() {
        return fxId;
    }

    public BigDecimal getTreasuryRate() {
        return treasuryRate;
    }
}
