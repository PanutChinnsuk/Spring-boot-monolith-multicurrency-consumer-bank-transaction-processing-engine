package com.project.banksystem.infrastructure.model.sharedmodel.paymentsession.entitybuilder;

import com.project.banksystem.core.enums.jwt.TokenType;
import com.project.banksystem.infrastructure.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MoneyTransactionSessionEntityBuilderBody {
    private User user;
    private TokenType tokenType;
    private BigDecimal amount;
    private Card card;
    private Account account;
    private Card targetCard;
    private Account targetAccount;
    private LocalDateTime expiresAt;
    private String deviceIp;
    private OverdraftedAccount overdraftedAccount;
    private Loan loan;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final MoneyTransactionSessionEntityBuilderBody body =
                new MoneyTransactionSessionEntityBuilderBody();

        public Builder user(User user) {
            body.user = user;
            return this;
        }

        public Builder tokenType(TokenType tokenType) {
            body.tokenType = tokenType;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            body.amount = amount;
            return this;
        }

        public Builder card(Card card) {
            body.card = card;
            return this;
        }

        public Builder account(Account account) {
            body.account = account;
            return this;
        }

        public Builder targetCard(Card targetCard) {
            body.targetCard = targetCard;
            return this;
        }

        public Builder targetAccount(Account targetAccount) {
            body.targetAccount = targetAccount;
            return this;
        }

        public Builder expiresAt(LocalDateTime expiresAt) {
            body.expiresAt = expiresAt;
            return this;
        }

        public Builder deviceIp(String deviceIp) {
            body.deviceIp = deviceIp;
            return this;
        }

        public Builder overdraftedAccount(OverdraftedAccount overdraftedAccount) {
            body.overdraftedAccount = overdraftedAccount;
            return this;
        }

        public Builder loan(Loan loan) {
            body.loan = loan;
            return this;
        }

        public MoneyTransactionSessionEntityBuilderBody build() {
            return body;
        }
    }

    public User user() {
        return user;
    }

    public TokenType tokenType() {
        return tokenType;
    }

    public BigDecimal amount() {
        return amount;
    }

    public Card card() {
        return card;
    }

    public Account account() {
        return account;
    }

    public Card targetCard() {
        return targetCard;
    }

    public Account targetAccount() {
        return targetAccount;
    }

    public LocalDateTime expiresAt() {
        return expiresAt;
    }

    public String deviceIp() {
        return deviceIp;
    }

    public OverdraftedAccount overdraftedAccount() {
        return overdraftedAccount;
    }

    public Loan loan() {
        return loan;
    }
}
