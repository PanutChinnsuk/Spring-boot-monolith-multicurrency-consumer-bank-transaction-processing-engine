package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.loan.LoanRequestStatus;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "loan_requests")
@DynamicUpdate
public class LoanRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "loan_request_id", columnDefinition = "BINARY(16)")
    private UUID loanRequestId;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;

    @NotNull
    @Positive
    @Column(name = "requested_principal", nullable = false, updatable = false, precision = 19, scale = 4)
    private BigDecimal requestedPrincipal;

    @NotNull
    @Positive
    @Column(name = "requested_interest_rate", nullable = false, updatable = false, precision = 5, scale = 2)
    private BigDecimal requestedInterestRate;

    @NotNull
    @Positive
    @Column(name = "requested_term_months", nullable = false, updatable = false)
    private Integer requestedTermMonths;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LoanRequestStatus loanRequestStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_loan_id")
    private Loan loan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    private User user;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewdAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected LoanRequest() {

    }

    public LoanRequest(Customer customer,
                       Account account,
                       BigDecimal requestedPrincipal,
                       BigDecimal requestedInterestRate,
                       Integer requestedTermMonths) {
        this.customer = customer;
        this.account = account;
        this.requestedPrincipal = requestedPrincipal;
        this.requestedInterestRate = requestedInterestRate;
        this.requestedTermMonths = requestedTermMonths;
    }

    @PrePersist
    private void defaultValue() {
        this.loanRequestStatus = LoanRequestStatus.PENDING;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Customer customer;
        Account account;
        BigDecimal requestedPrincipal;
        BigDecimal requestedInterestRate;
        Integer requestedTermMonths;

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder account(Account account) {
            this.account = account;
            return this;
        }

        public Builder requestedPrincipal(BigDecimal requestedPrincipal) {
            this.requestedPrincipal = requestedPrincipal;
            return this;
        }

        public Builder requestedInterestRate(BigDecimal requestedInterestRate) {
            this.requestedInterestRate = requestedInterestRate;
            return this;
        }

        public Builder requestedTermMonths(Integer requestedTermMonths) {
            this.requestedTermMonths = requestedTermMonths;
            return this;
        }

        public LoanRequest build() {
            return new LoanRequest(customer, account,
                    requestedPrincipal, requestedInterestRate,
                    requestedTermMonths);
        }
    }

    public void setApprovedLoan(Loan loan) {
        if (this.loan != null) {
            throw new IllegalStateException("Request already have approved loan.");
        }
        this.loan = loan;
    }


    public void changeLoanRequestStatus(LoanRequestStatus loanRequestStatus) {
        this.loanRequestStatus = loanRequestStatus;
    }

    public void setReviewedUser(User user) {
        if (this.user != null) {
            throw new BusinessException("This loan request has already been reviewed.");
        }

        this.user = user;
        this.reviewdAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Account getAccount() {
        return account;
    }

    public Loan getLoan() {
        return loan;
    }

    public BigDecimal getRequestedInterestRate() {
        return requestedInterestRate;
    }

    public BigDecimal getRequestedPrincipal() {
        return requestedPrincipal;
    }

    public Integer getRequestedTermMonths() {
        return requestedTermMonths;
    }

    public LoanRequestStatus getLoanRequestStatus() {
        return loanRequestStatus;
    }

    public LocalDateTime getReviewdAt() {
        return reviewdAt;
    }

    public UUID getLoanRequestId() {
        return loanRequestId;
    }
}
