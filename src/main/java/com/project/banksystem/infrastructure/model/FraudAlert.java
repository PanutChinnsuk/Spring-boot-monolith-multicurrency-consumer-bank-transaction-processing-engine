package com.project.banksystem.infrastructure.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "fraud_alerts")
@Immutable
public class FraudAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "alert_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID alertId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transaction_id", nullable = false, updatable = false)
    private Transaction transaction;

    @NotBlank
    @Column(name = "reason", nullable = false, updatable = false)
    private String reason;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected FraudAlert() {

    }

    public FraudAlert(Customer customer,
                      Transaction transaction,
                      String reason) {
        this.customer = customer;
        this.transaction = transaction;
        this.reason = reason;
    }

    @PreUpdate
    private void preventUpdate() {
        throw new IllegalStateException("Model is immutable.");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Customer customer;
        Transaction transaction;
        String reason;

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder transaction(Transaction transaction) {
            this.transaction = transaction;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public FraudAlert build() {
            return new FraudAlert(customer, transaction, reason);
        }
    }


    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getReason() {
        return reason;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public UUID getAlertId() {
        return alertId;
    }
}
