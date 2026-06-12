package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.overdraftpayback.OverdraftPaybackLineDirection;
import com.project.banksystem.core.enums.overdraftpayback.OverdraftPaybackRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "overdraft_payback_lines")
@Immutable
public class OverdraftPaybackLine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "line_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID lineId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ledger_account_id", nullable = false, updatable = false)
    private LedgerAccount ledgerAccount;

    @NotNull
    @PositiveOrZero
    @Column(name = "monetary_amount", precision = 19, scale = 4, nullable = false, updatable = false)
    private BigDecimal monetaryAmount;

    @NotNull
    @PositiveOrZero
    @Column(name = "converted_amount", precision = 19, scale = 4, nullable = false, updatable = false)
    private BigDecimal convertedAmount;

    @NotNull
    @PositiveOrZero
    @Column(name = "final_amount", precision = 19, scale = 4, nullable = false, updatable = false)
    private BigDecimal finalAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payback_direction", nullable = false, updatable = false)
    private OverdraftPaybackLineDirection overdraftPaybackLineDirection;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payback_role", nullable = false, updatable = false)
    private OverdraftPaybackRole overdraftPaybackRole;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false, updatable = false)
    private OverdraftPaybackRequest overdraftPaybackRequest;


    protected OverdraftPaybackLine() {

    }


    public OverdraftPaybackLine(Account account,
                                LedgerAccount ledgerAccount,
                                BigDecimal monetaryAmount,
                                BigDecimal convertedAmount,
                                BigDecimal finalAmount,
                                OverdraftPaybackLineDirection overdraftPaybackLineDirection,
                                OverdraftPaybackRole overdraftPaybackRole,
                                OverdraftPaybackRequest overdraftPaybackRequest) {
        this.account = account;
        this.ledgerAccount = ledgerAccount;
        this.monetaryAmount = monetaryAmount;
        this.finalAmount = finalAmount;
        this.convertedAmount = convertedAmount;
        this.overdraftPaybackLineDirection = overdraftPaybackLineDirection;
        this.overdraftPaybackRole = overdraftPaybackRole;
        this.overdraftPaybackRequest = overdraftPaybackRequest;
    }

    @PrePersist
    private void defaultValue() {
        if (overdraftPaybackLineDirection != OverdraftPaybackLineDirection.PAYBACK) {return;}

        if (monetaryAmount.signum() <= 0) {
            throw new IllegalStateException("Monetary amount should exists for payback side.");
        }
    }

    @PreUpdate
    private void preventUpdate() {
        throw new IllegalStateException("Model is immutable.");
    }

    public static Builder builder() {
        return new Builder();
    }



    public static class Builder {
        Account account;
        LedgerAccount ledgerAccount;
        BigDecimal monetaryAmount;
        BigDecimal convertedAmount;
        BigDecimal finalAmount;
        OverdraftPaybackLineDirection overdraftPaybackLineDirection;
        OverdraftPaybackRole overdraftPaybackRole;
        OverdraftPaybackRequest overdraftPaybackRequest;

        public Builder account(Account account) {
            this.account = account;
            return this;
        }

        public Builder ledgerAccount(LedgerAccount ledgerAccount) {
            this.ledgerAccount = ledgerAccount;
            return this;
        }

        public Builder monetaryAmount(BigDecimal monetaryAmount) {
            this.monetaryAmount = monetaryAmount;
            return this;
        }

        public Builder convertedAmount(BigDecimal convertedAmount) {
            this.convertedAmount = convertedAmount;
            return this;
        }

        public Builder finalAmount(BigDecimal finalAmount) {
            this.finalAmount = finalAmount;
            return this;
        }

        public Builder overdraftPaybackLineDirection(OverdraftPaybackLineDirection overdraftPaybackLineDirection) {
            this.overdraftPaybackLineDirection = overdraftPaybackLineDirection;
            return this;
        }

        public Builder overdraftPaybackRole(OverdraftPaybackRole overdraftPaybackRole) {
            this.overdraftPaybackRole = overdraftPaybackRole;
            return this;
        }

        public Builder overdraftPaybackRequest(OverdraftPaybackRequest overdraftPaybackRequest) {
            this.overdraftPaybackRequest = overdraftPaybackRequest;
            return this;
        }

        public OverdraftPaybackLine build() {
            return new OverdraftPaybackLine(account, ledgerAccount,
                    monetaryAmount, convertedAmount,
                    finalAmount, overdraftPaybackLineDirection,
                    overdraftPaybackRole, overdraftPaybackRequest);
        }
    }



    public Account getAccount() {
        return account;
    }

    public LedgerAccount getLedgerAccount() {
        return ledgerAccount;
    }

    public UUID getLineId() {
        return lineId;
    }

    public OverdraftPaybackLineDirection getOverdraftPaybackLineDirection() {
        return overdraftPaybackLineDirection;
    }

    public BigDecimal getMonetaryAmount() {
        return monetaryAmount;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public OverdraftPaybackRequest getOverdraftPaybackRequest() {
        return overdraftPaybackRequest;
    }

    public OverdraftPaybackRole getOverdraftPaybackRole() {
        return overdraftPaybackRole;
    }
}
