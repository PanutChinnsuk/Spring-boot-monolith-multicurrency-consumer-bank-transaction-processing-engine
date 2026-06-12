package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.system.CurrencyCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "loan_payments")
@Immutable
public class LoanPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID paymentId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false, updatable = false)
    private Loan loan;

    @NotNull
    @Positive
    @Column(name = "amount", nullable = false, updatable = false)
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false, updatable = false,
    columnDefinition = "CHAR(3)")
    private CurrencyCode currencyCode;

    @CreationTimestamp
    @Column(name = "paid_at", nullable = false, updatable = false)
    private LocalDateTime paidAt;

    protected LoanPayment() {

    }

    public LoanPayment(Account account,
                       Loan loan,
                       BigDecimal amount,
                       CurrencyCode currencyCode) {
        this.account = account;
        this.loan = loan;
        this.amount = amount;
        this.currencyCode = currencyCode;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Account account;
        Loan loan;
        BigDecimal amount;
        CurrencyCode currencyCode;

        public Builder account(Account account) {
            this.account = account;
            return this;
        }

        public Builder loan(Loan loan) {
            this.loan = loan;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder currencyCode(CurrencyCode currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }

        public LoanPayment build() {
            return new LoanPayment(account, loan, amount, currencyCode);
        }
    }

    public Account getAccount() {
        return account;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Loan getLoan() {
        return loan;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }
}
