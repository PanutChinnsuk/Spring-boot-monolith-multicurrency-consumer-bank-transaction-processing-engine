package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.system.CurrencyCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "loan_schedules")
@DynamicUpdate
public class LoanSchedule {
    @Id
    @Column(name = "schedule_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID scheduleId;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "loan_id", nullable = false, updatable = false)
    private Loan loan;

    @NotNull
    @Column(name = "due_date", nullable = false, updatable = false)
    private LocalDate dueDate;

    @NotNull
    @Positive
    @Column(name = "amount_due", nullable = false, updatable = false, precision = 19, scale = 4)
    private BigDecimal amountDue;

    @NotNull
    @Positive
    @Column(name = "partial_from_principal", nullable = false, updatable = false, precision = 19, scale = 4)
    private BigDecimal partialFromPrincipal;

    @NotNull
    @PositiveOrZero
    @Column(name = "partial_interest", nullable = false, precision = 19, scale = 4)
    private BigDecimal partialInterest;

    @NotNull
    @PositiveOrZero
    @Column(name = "paid_back_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal paidBackAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false, updatable = false,
    columnDefinition = "CHAR(3)")
    private CurrencyCode currencyCode;

    @NotNull
    @Column(name = "paid", nullable = false)
    private Boolean paid;

    protected LoanSchedule() {

    }

    public LoanSchedule(Loan loan,
                        LocalDate dueDate,
                        BigDecimal amountDue,
                        BigDecimal partialInterest,
                        CurrencyCode currencyCode,
                        BigDecimal partialFromPrincipal) {
        this.scheduleId = UUID.randomUUID();
        this.loan = loan;
        this.dueDate = dueDate;
        this.amountDue = amountDue;
        this.partialInterest = partialInterest;
        this.currencyCode = currencyCode;
        this.partialFromPrincipal = partialFromPrincipal;
        this.paid = false;
        this.paidBackAmount = BigDecimal.ZERO;

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Loan loan;
        LocalDate dueDate;
        BigDecimal amountDue;
        BigDecimal partialInterest;
        CurrencyCode currencyCode;
        BigDecimal partialFromPrincipal;

        public Builder loan(Loan loan) {
            this.loan = loan;
            return this;
        }

        public Builder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder amountDue(BigDecimal amountDue) {
            this.amountDue = amountDue;
            return this;
        }

        public Builder partialInterest(BigDecimal partialInterest) {
            this.partialInterest = partialInterest;
            return this;
        }

        public Builder currencyCode(CurrencyCode currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }

        public Builder partialFromPrincipal(BigDecimal partialFromPrincipal) {
            this.partialFromPrincipal = partialFromPrincipal;
            return this;
        }

        public LoanSchedule build() {
            return new LoanSchedule(loan, dueDate, amountDue, partialInterest,
                    currencyCode, partialFromPrincipal);
        }
    }

    public void changePartialInterest(BigDecimal partialInterest) {
        this.partialInterest = partialInterest;
    }

    public boolean isDue() {
        return this.dueDate.isBefore(LocalDate.now());
    }

    public void paidSchedule() {
        this.paid = true;
    }

    public BigDecimal getAmountDue() {
        return amountDue;
    }

    public void updatePaidbackAmount(BigDecimal amount) {
        this.paidBackAmount = amount;
    }

    public void increasePaidbackAmount(BigDecimal amount) {
        this.paidBackAmount = this.paidBackAmount.add(amount);
    }

    public Boolean getPaid() {
        return paid;
    }

    public Loan getLoan() {
        return loan;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public UUID getScheduleId() {
        return scheduleId;
    }

    public BigDecimal getPaidBackAmount() {
        return paidBackAmount;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public BigDecimal getPartialInterest() {
        return partialInterest;
    }

    public BigDecimal getPartialFromPrincipal() {
        return partialFromPrincipal;
    }
}
