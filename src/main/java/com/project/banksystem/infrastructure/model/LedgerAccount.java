package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.ledger.LedgerType;
import com.project.banksystem.core.enums.system.CurrencyCode;
import com.project.banksystem.core.enums.transaction.TransactionLineDirection;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Entity
@Table(name = "ledger_accounts")
@DynamicUpdate
public class LedgerAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ledger_account_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID ledgerAccountId;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, updatable = false)
    private LedgerType ledgerType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "economic_value_currency", nullable = false, updatable = false, columnDefinition = "CHAR(3)")
    private CurrencyCode currencyCode;

    @NotBlank
    @Column(name = "code", nullable = false, updatable = false)
    private String code;

    protected LedgerAccount() {

    }

    public LedgerAccount(String name,
                         LedgerType ledgerType,
                         CurrencyCode currencyCode,
                         String code) {
        this.name = name;
        this.ledgerType = ledgerType;
        this.currencyCode = currencyCode;
        this.code = code;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        String name;
        LedgerType ledgerType;
        CurrencyCode currencyCode;
        String code;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder ledgerType(LedgerType ledgerType) {
            this.ledgerType = ledgerType;
            return this;
        }

        public Builder currencyCode(CurrencyCode currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }

        public Builder code(String code)  {
            this.code = code;
            return this;
        }

        public LedgerAccount build() {
            return new LedgerAccount(name, ledgerType, currencyCode, code);
        }
    }

    public boolean isIncrease(TransactionLineDirection direction) {
        boolean isExpenseOrAsset = this.ledgerType == LedgerType.ASSET ||
                this.ledgerType == LedgerType.EXPENSE;

        return (isExpenseOrAsset && direction == TransactionLineDirection.DEBIT) ||
                (!isExpenseOrAsset && direction == TransactionLineDirection.CREDIT);
    }

    public void changeName(String name) {
        this.name = name;
    }


    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public LedgerType getLedgerType() {
        return ledgerType;
    }

    public UUID getLedgerAccountId() {
        return ledgerAccountId;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
