package com.project.banksystem.infrastructure.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "card_contacts")
@DynamicUpdate
public class CardContact {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "card_contact_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID cardContactId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false, updatable = false)
    private Card card;

    @CreationTimestamp
    @Column(name = "added_at", nullable = false, updatable = false)
    private LocalDateTime addedAt;

    @NotNull
    @Column(name = "deleted", nullable = false)
    private Boolean deleted;


    protected CardContact() {

    }

    public CardContact(User user,
                       Card card) {
        this.user = user;
        this.card = card;
    }

    @PrePersist
    private void defaultValue() {
        this.deleted = false;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        User user;
        Card card;

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder card(Card card) {
            this.card = card;
            return this;
        }

        public CardContact builder() {
            return new CardContact(user, card);
        }
    }

    public Card getCard() {
        return card;
    }

    public User getUser() {
        return user;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public UUID getCardContactId() {
        return cardContactId;
    }
}
