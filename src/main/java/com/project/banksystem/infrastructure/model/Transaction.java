package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.fxrate.FxDirection;
import com.project.banksystem.core.enums.transaction.TransactionPrincipal;
import com.project.banksystem.core.enums.transaction.TransactionStatus;
import com.project.banksystem.core.enums.transaction.TransactionType;
import com.project.banksystem.infrastructure.exception.ex.SystemMisusedException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;



@Entity
@Table(name = "transactions")
@DynamicUpdate
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID transactionId;

    @OneToMany(mappedBy = "transaction", fetch = FetchType.LAZY)
    private Set<TransactionLine> transactionLines;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, updatable = false)
    private TransactionType transactionType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus transactionStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "fx_direction", nullable = false, updatable = false)
    private FxDirection fxDirection;

    @NotNull
    @Column(name = "initiated_at", nullable = false, updatable = false)
    private LocalDateTime initiatedAt;

    @NotNull
    @Column(name = "expires_at", nullable = false, updatable = false)
    private LocalDateTime expiresAt;

    @Column(name = "posted_at")
    private LocalDateTime postedAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_principal", nullable = false, updatable = false)
    private TransactionPrincipal transactionPrincipal;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reversed_of", updatable = false)
    private Transaction reversedOf;


    protected Transaction() {

    }

    public Transaction(TransactionType transactionType,
                       FxDirection fxDirection,
                       TransactionPrincipal transactionPrincipal,
                       Transaction reversedOf,
                       LocalDateTime expiresAt) {
        this.transactionType = transactionType;
        this.fxDirection = fxDirection;
        this.transactionPrincipal = transactionPrincipal;
        this.reversedOf = reversedOf;
        this.expiresAt = expiresAt;
    }

    @PrePersist
    private void defaultValue() {
        this.transactionStatus = TransactionStatus.PENDING;
        this.initiatedAt = LocalDateTime.now();
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        TransactionType transactionType;
        FxDirection fxDirection;
        TransactionPrincipal transactionPrincipal;
        Transaction optionalReversedOf;
        LocalDateTime expiresAt;


        public Builder transactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public Builder fxDirection(FxDirection fxDirection) {
            this.fxDirection = fxDirection;
            return this;
        }

        public Builder transactionPrincipal(TransactionPrincipal transactionPrincipal) {
            this.transactionPrincipal = transactionPrincipal;
            return this;
        }

        public Builder optionalReversedOf(Transaction optionalReversedOf) {
            this.optionalReversedOf = optionalReversedOf;
            return this;
        }

        public Builder expiresAt(LocalDateTime expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public Transaction build() {
            return new Transaction(transactionType, fxDirection, transactionPrincipal,
                    optionalReversedOf, expiresAt);
        }
    }

    public void setPosted() {
        this.postedAt = LocalDateTime.now();
    }

    public void changeTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public boolean isCardTransaction() {
        return this.transactionPrincipal.isCardPrincipal();
    }

    public boolean isReversedTransaction() {
        return this.reversedOf != null;
    }

    public void forceMemoryPersistTransactionLines(Set<TransactionLine> transactionLines) {
        this.transactionLines = transactionLines;
    }

    public void changePostedAt(LocalDateTime postedAt) {
        this.postedAt = postedAt;
    }

    public LocalDateTime getInitiatedAt() {
        return initiatedAt;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public Set<TransactionLine> getTransactionLines() {
        return transactionLines;
    }

    public FxDirection getFxDirection() {
        return fxDirection;
    }

    public TransactionPrincipal getTransactionPrincipal() {
        return transactionPrincipal;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public Transaction getReferencedReversedTransaction() {
        if (this.reversedOf == null) {
            throw new SystemMisusedException("Reversed referenced transaction entity does not exist" +
                    " in a reversed transaction.");
        }
        return reversedOf;
    }
}
