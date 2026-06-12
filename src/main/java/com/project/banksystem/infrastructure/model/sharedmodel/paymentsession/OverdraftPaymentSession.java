package com.project.banksystem.infrastructure.model.sharedmodel.paymentsession;

import com.project.banksystem.core.enums.jwt.TokenType;
import com.project.banksystem.infrastructure.model.OverdraftedAccount;
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
@Table(name = "overdraft_payment_sessions")
@DynamicUpdate
public class OverdraftPaymentSession implements MoneyTransactionSession {
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
    @Column(name = "payback_amount", nullable = false, updatable = false, precision = 19, scale = 4)
    private BigDecimal paybackAmount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "overdrafted_id", nullable = false, updatable = false)
    private OverdraftedAccount overdraftedAccount;

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

    protected OverdraftPaymentSession() {

    }

    public OverdraftPaymentSession(MoneyTransactionSessionEntityBuilderBody b) {
        this.user = b.user();
        this.tokenType = b.tokenType();
        this.paybackAmount = b.amount();
        this.account = b.account();
        this.expiresAt = b.expiresAt();
        this.deviceIp = b.deviceIp();
        this.overdraftedAccount = b.overdraftedAccount();
    }

    @PrePersist
    private void defaultValue() {
        this.revoked = false;
    }


    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public BigDecimal getAmount() {
        return paybackAmount;
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
        return null;
    }

    @Override
    public void revokeSession() {
        this.revoked = true;
    }

    public OverdraftedAccount getOverdraftedAccount() {
        return overdraftedAccount;
    }
}
