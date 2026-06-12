package com.project.banksystem.infrastructure.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "card_contact_reference_codes")
@DynamicUpdate
public class CardContactReferenceCode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ref_code_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID referenceCodeId;

    @NotBlank
    @Column(name = "code", nullable = false, updatable = false, unique = true)
    private String code;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false, updatable = false)
    private Card card;

    @NotNull
    @Column(name = "revoked", nullable = false)
    private Boolean revoked;

    @NotNull
    @Column(name = "expires_at", nullable = false, updatable = false)
    private LocalDateTime expiresAt;


    protected CardContactReferenceCode() {

    }

    public CardContactReferenceCode(String code,
                                    User user,
                                    Card card,
                                    LocalDateTime expiresAt) {
        this.code = code;
        this.user = user;
        this.card = card;
        this.expiresAt = expiresAt;
    }


    @PrePersist
    private void defaultValue() {
        this.revoked = false;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        String code;
        User user;
        Card card;
        LocalDateTime expiresAt;

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder card(Card card) {
            this.card = card;
            return this;
        }

        public Builder expiresAt(LocalDateTime expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public CardContactReferenceCode build() {
            return new CardContactReferenceCode(code, user, card, expiresAt);
        }
    }


    public void revokeCode() {
        this.revoked = true;
    }

    public Card getCard() {
        return card;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public String getCode() {
        return code;
    }

    public Boolean getRevoked() {
        return revoked;
    }

    public UUID getReferenceCodeId() {
        return referenceCodeId;
    }
}
