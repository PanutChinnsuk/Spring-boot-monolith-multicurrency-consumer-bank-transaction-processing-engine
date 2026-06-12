package com.project.banksystem.core.enums.transaction;

public enum BalanceImpact {
    RESERVE_DEBIT, // use for check if account balance has enough reserve
    DIRECT_DEBIT, // use for check if account available balance is enough for direct subtraction
    NO_CHECK, // use for check if account is the receiver
    COMPUTATIONAL, // does not affects in processing layer only their to balance the entrance
    INTERNAL_DIRECT_DEBIT,
    INTERNAL_NO_CHECK;


    public boolean isDeductingBalance() {
        return this == RESERVE_DEBIT
                || this == DIRECT_DEBIT
                || this == INTERNAL_DIRECT_DEBIT;
    }

    public boolean isInternal() {
        return this == INTERNAL_DIRECT_DEBIT
                || this == INTERNAL_NO_CHECK;
    }

    public boolean isNoCheck() {
        return this == NO_CHECK
                || this == INTERNAL_NO_CHECK;
    }

    public boolean isDirectDebit() {
        return this == INTERNAL_DIRECT_DEBIT
                || this == DIRECT_DEBIT;
    }

    public boolean isReserveDebit() {
        return this == RESERVE_DEBIT;
    }

}
