package com.project.banksystem.infrastructure.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

@Entity
@Table(name = "card_transactions")
@Immutable
public class CardTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "card_tx_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID cardTxId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "card_id", nullable = false, updatable = false)
    private Card card;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transaction_id", nullable = false, updatable = false)
    private Transaction transaction;

    protected CardTransaction() {

    }

    public CardTransaction(Card card,
                           Transaction transaction) {
        this.card = card;
        this.transaction = transaction;
    }

    @PreUpdate
    private void preventUpdate() {
        throw new IllegalStateException("Model is immutable.");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Card card;
        Transaction transaction;

        public Builder card(Card card) {
            this.card = card;
            return this;
        }

        public Builder transaction(Transaction transaction) {
            this.transaction = transaction;
            return this;
        }

        public CardTransaction build() {
            return new CardTransaction(card, transaction);
        }
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Card getCard() {
        return card;
    }

    public UUID getCardTxId() {
        return cardTxId;
    }
}
