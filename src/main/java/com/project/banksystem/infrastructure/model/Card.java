package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.card.CardStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "card_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID cardId;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;

    @NotBlank
    @Column(name = "pan_encrypt", nullable = false, unique = true)
    private String panEncrypt;

    @NotBlank
    @Column(name = "masked_pan", nullable = false, updatable = false)
    private String maskedPan;

    @NotNull
    @Column(name = "expiry", nullable = false, updatable = false)
    private LocalDate expiry;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @Column(name = "delivered_expiresAt")
    private LocalDateTime deliveredExpiresAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CardStatus cardStatus;

    @NotNull
    @Positive
    @Column(name = "key_version", nullable = false)
    private Integer keyVersion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "replaced_of", updatable = false)
    private Card replacedOf;

    protected Card() {

    }

    public Card(Account account,
                String maskedPan,
                LocalDate expiry,
                String panEncrypt,
                Integer keyVersion,
                Card optionalReplacedOf) {
        this.account = account;
        this.maskedPan = maskedPan;
        this.expiry = expiry;
        this.panEncrypt = panEncrypt;
        this.keyVersion = keyVersion;
        this.replacedOf = optionalReplacedOf;
    }

    @PrePersist
    private void defaultValue() {
        this.cardStatus = CardStatus.REQUESTED;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Account account;
        String maskedPan;
        LocalDate expiry;
        String panEncrypt;
        Integer keyVersion;
        Card optionalReplacedOf;

        public Builder account(Account account) {
            this.account = account;
            return this;
        }

        public Builder maskedPan(String maskedPan) {
            this.maskedPan = maskedPan;
            return this;
        }

        public Builder expiry(LocalDate expiry) {
            this.expiry = expiry;
            return this;
        }

        public Builder panEncrypt(String panEncrypt) {
            this.panEncrypt = panEncrypt;
            return this;
        }

        public Builder keyVersion(Integer keyVersion) {
            this.keyVersion = keyVersion;
            return this;
        }

        public Builder optionalReplacedOf(Card optionalReplacedOf) {
            this.optionalReplacedOf = optionalReplacedOf;
            return this;
        }

        public Card build() {
            return new Card(account, maskedPan, expiry, panEncrypt, keyVersion,
                    optionalReplacedOf);
        }
    }

    public void changeCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public void changeKeyVersion(Integer keyVersion) {
        this.keyVersion = keyVersion;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt, int deliveredStatusExpiresDay) {
        if (this.deliveredAt != null) {
            throw new IllegalStateException("Card already have delivered time set.");
        }

        this.deliveredAt = deliveredAt;
        this.deliveredExpiresAt = deliveredAt.plusDays(deliveredStatusExpiresDay);
    }

    public void migratePan(String pan) {
        this.panEncrypt = pan;
    }

    public String getPanEncrypt() {
        return panEncrypt;
    }

    public Account getAccount() {
        return account;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public LocalDate getExpiry() {
        return expiry;
    }

    public String getMaskedPan() {
        return maskedPan;
    }

    public UUID getCardId() {
        return cardId;
    }

    public Integer getKeyVersion() {
        return keyVersion;
    }

    public Card getReplacedOf() {
        return replacedOf;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public LocalDateTime getDeliveredExpiresAt() {
        return deliveredExpiresAt;
    }
}
