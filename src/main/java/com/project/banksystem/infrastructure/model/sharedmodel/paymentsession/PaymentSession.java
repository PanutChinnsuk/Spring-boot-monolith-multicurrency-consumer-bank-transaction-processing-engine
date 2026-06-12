package com.project.banksystem.infrastructure.model.sharedmodel.paymentsession;

import com.project.banksystem.core.enums.jwt.TokenType;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.model.Account;
import com.project.banksystem.infrastructure.model.Card;
import com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.entitybuilder.MoneyTransactionSessionEntityBuilderBody;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_sessions")
@DynamicUpdate
public class PaymentSession implements MoneyTransactionSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_session_id", columnDefinition = "BINARY(16)")
    private UUID paymentSessionId;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "token_type", nullable = false, updatable = false)
    private TokenType tokenType;

    @NotNull
    @Positive
    @Column(name = "amount", nullable = false, updatable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", updatable = false)
    private Card card;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_card_id", updatable = false)
    private Card targetCard;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_account_id", nullable = false, updatable = false)
    private Account targetAccount;

    @NotNull
    @Column(name = "expires_at", nullable = false, updatable = false)
    private LocalDateTime expiresAt;

    @NotBlank
    @Column(name = "device_ip", nullable = false, updatable = false)
    private String deviceIp;

    @NotNull
    @Column(name = "revoked", nullable = false)
    private Boolean revoked;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected PaymentSession() {

    }

    public PaymentSession(MoneyTransactionSessionEntityBuilderBody b) {
        this.user = b.user();
        this.tokenType = b.tokenType();
        this.amount = b.amount();
        this.card = b.card();
        this.account = b.account();
        this.targetCard = b.targetCard();
        this.targetAccount = b.targetAccount();
        this.expiresAt = b.expiresAt();
        this.deviceIp = b.deviceIp();
    }

    @PrePersist
    private void defaultValue() {
        this.revoked = false;

        if (tokenType == TokenType.CARD_PAYMENT) {
            if (card == null) {
                throw new IllegalStateException("Card is required for card payment session.");
            }

            if (targetCard == null) {
                throw new IllegalStateException("Target card is required for card payment session.");
            }
        }



        if (targetCard == null) {return;}
        Account fromCard = targetCard.getAccount();

        if (fromCard.getAccountId().equals(account.getAccountId())) {
            throw new IllegalStateException("Target account from target card must be different.");
        }

        this.targetAccount = fromCard;
    }


    public boolean isCardPaymentSession() {
        return tokenType == TokenType.CARD_PAYMENT;
    }

    public boolean isAccountPaymentSession() {
        return tokenType == TokenType.ACCOUNT_PAYMENT;
    }




    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Boolean getRevoked() {
        return revoked;
    }

    @Override
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    @Override
    public String getDeviceIp() {
        return deviceIp;
    }

    @Override
    public TokenType getTokenType() {
        return tokenType;
    }

    @Override
    public UUID getPaymentSessionId() {
        return paymentSessionId;
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public Card getCard() {
        return card;
    }

    @Override
    public void revokeSession() {
        this.revoked = true;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public Card getTargetCard() {
        return targetCard;
    }
}
