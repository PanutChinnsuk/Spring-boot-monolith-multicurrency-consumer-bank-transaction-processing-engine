package com.project.banksystem.application.backend.banktransaction.cardtransaction.data;

import com.project.banksystem.infrastructure.model.Card;
import com.project.banksystem.infrastructure.model.Transaction;

public class CardTransactionCreateData {
    private Card card;
    private Transaction transaction;

    public CardTransactionCreateData(Card card,
                                     Transaction transaction) {
        this.card = card;
        this.transaction = transaction;
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

        public CardTransactionCreateData build() {
            if (transaction == null) {
                throw new IllegalStateException("Transaction is null.");
            }
            if (card == null) {
                throw new IllegalStateException("Card is null.");
            }

            return new CardTransactionCreateData(card, transaction);
        }
    }

    public Card getCard() {
        return card;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}
