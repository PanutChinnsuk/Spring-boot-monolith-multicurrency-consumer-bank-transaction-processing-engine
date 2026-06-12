package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.fxrate.FxDirection;
import com.project.banksystem.core.enums.loan.LoanStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "loans")
@DynamicUpdate
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "loan_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID loanId;

    @Version
    @Column(nullable = false)
    private Long version;

    @OneToMany(mappedBy = "loan", fetch = FetchType.LAZY)
    private Set<LoanSchedule> loanSchedules;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fx_id", updatable = false)
    private FxRate usedFxRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "fx_direction", updatable = false)
    private FxDirection usedFxDirection;

    @NotNull
    @PositiveOrZero
    @Column(name = "principal", nullable = false, updatable = false, precision = 19, scale = 4)
    private BigDecimal principal;

    @NotNull
    @PositiveOrZero
    @Column(name = "interest_rate", nullable = false, updatable = false, precision = 5, scale = 2)
    private BigDecimal interestRate;

    @NotNull
    @PositiveOrZero
    @Column(name = "term_months", nullable = false, updatable = false)
    private Integer termMonths;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LoanStatus loanStatus;

    @NotNull
    @Column(name = "next_due_date", nullable = false)
    private LocalDate nextDueDate;

    @NotNull
    @PositiveOrZero
    @Column(name = "days_past_due", nullable = false)
    private Integer daysPastDue;

    @NotNull
    @Column(name = "starts_at", nullable = false, updatable = false)
    private LocalDate startsAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    protected Loan() {

    }

    public Loan(Customer customer,
                Account account,
                BigDecimal principal,
                BigDecimal interestRate,
                Integer termMonths,
                LocalDate nextDueDate,
                LocalDate startsAt,
                FxRate usedFxRate,
                FxDirection usedFxDirection) {
        this.customer = customer;
        this.account = account;
        this.principal = principal;
        this.interestRate = interestRate;
        this.termMonths = termMonths;
        this.nextDueDate = nextDueDate;
        this.startsAt = startsAt;
        this.usedFxRate = usedFxRate;
        this.usedFxDirection = usedFxDirection;
    }

    @PrePersist
    private void defaultValue() {

        this.daysPastDue = 0;
        this.loanStatus = LoanStatus.ACTIVE;


        if (usedFxRate != null && usedFxDirection == null) {
            throw new IllegalStateException("Used fx direction must exist if used fx rate" +
                    " exists.");
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Customer customer;
        Account account;
        BigDecimal principal;
        BigDecimal interestRate;
        Integer termMonths;
        LocalDate nextDueDate;
        LocalDate startsAt;
        FxRate usedFxRate;
        FxDirection usedFxDirection;

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder account(Account account) {
            this.account = account;
            return this;
        }

        public Builder principal(BigDecimal principal) {
            this.principal = principal;
            return this;
        }

        public Builder interestRate(BigDecimal interestRate) {
            this.interestRate = interestRate;
            return this;
        }

        public Builder termMonths(Integer termMonths) {
            this.termMonths = termMonths;
            return this;
        }


        public Builder nextDueDate(LocalDate nextDueDate) {
            this.nextDueDate = nextDueDate;
            return this;
        }

        public Builder startsAt(LocalDate startsAt) {
            this.startsAt = startsAt;
            return this;
        }

        public Builder usedFxRate(FxRate usedFxRate) {
            this.usedFxRate = usedFxRate;
            return this;
        }

        public Builder usedFxDirection(FxDirection usedFxDirection) {
            this.usedFxDirection = usedFxDirection;
            return this;
        }

        public Loan build() {
            return new Loan(customer, account,
                    principal, interestRate, termMonths, nextDueDate, startsAt,
                    usedFxRate, usedFxDirection);
        }
    }

    public void forceLoanSchedulesMemoryPersist(Set<LoanSchedule> loanSchedules) {
        if (this.loanSchedules != null) {
            throw new IllegalStateException("Loan schedules already exist in the memory" +
                    " for loan.");
        }
        this.loanSchedules = loanSchedules;
    }


    public void changeLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public void changeNextDueDate(LocalDate nextDueDate) {
        this.nextDueDate = nextDueDate;
    }

    public void changeDaysPastDue(Integer daysPastDue) {
        this.daysPastDue = daysPastDue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public Integer getDaysPastDue() {
        return daysPastDue;
    }

    public Integer getTermMonths() {
        return termMonths;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public LocalDate getNextDueDate() {
        return nextDueDate;
    }

    public Account getAccount() {
        return account;
    }

    public UUID getLoanId() {
        return loanId;
    }

    public Set<LoanSchedule> getLoanSchedules() {
        return loanSchedules;
    }

    public LocalDate getStartsAt() {
        return startsAt;
    }

    public FxRate getUsedFxRate() {
        return usedFxRate;
    }

    public FxDirection getUsedFxDirection() {
        return usedFxDirection;
    }
}
