package com.project.banksystem.core.enums.transaction;

public enum TransactionPrincipal {
    ACCOUNT_FOR_TRANSACTION,
    CARD_FOR_TRANSACTION,
    ACCOUNT_FOR_LOAN_TRANSACTION;

    public boolean isAccountPrincipal() {
        return this == ACCOUNT_FOR_TRANSACTION ||
                this == ACCOUNT_FOR_LOAN_TRANSACTION;
    }

    public boolean isCardPrincipal() {
        return this == CARD_FOR_TRANSACTION;
    }
}
