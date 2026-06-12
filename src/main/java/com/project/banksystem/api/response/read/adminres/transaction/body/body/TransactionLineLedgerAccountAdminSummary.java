package com.project.banksystem.api.response.read.adminres.transaction.body.body;

import com.project.banksystem.core.enums.ledger.LedgerType;
import com.project.banksystem.core.enums.system.CurrencyCode;

import java.util.UUID;

public class TransactionLineLedgerAccountAdminSummary {
    private UUID ledgerAccountId;
    private String name;
    private LedgerType ledgerType;
    private CurrencyCode currencyCode;
    private String code;

    public TransactionLineLedgerAccountAdminSummary(UUID ledgerAccountId,
                                                    String name,
                                                    LedgerType ledgerType,
                                                    CurrencyCode currencyCode,
                                                    String code) {
        this.ledgerAccountId = ledgerAccountId;
        this.name = name;
        this.ledgerType = ledgerType;
        this.currencyCode = currencyCode;
        this.code = code;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UUID ledgerAccountId;
        String name;
        LedgerType ledgerType;
        CurrencyCode currencyCode;
        String code;

        public Builder ledgerAccountId(UUID ledgerAccountId) {
            this.ledgerAccountId = ledgerAccountId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder ledgerType(LedgerType ledgerType) {
            this.ledgerType = ledgerType;
            return this;
        }

        public Builder currency(CurrencyCode currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public TransactionLineLedgerAccountAdminSummary build() {
            return new TransactionLineLedgerAccountAdminSummary(ledgerAccountId, name, ledgerType, currencyCode, code);
        }
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public UUID getLedgerAccountId() {
        return ledgerAccountId;
    }

    public LedgerType getLedgerType() {
        return ledgerType;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
