package com.project.banksystem.core.enums.overdraftpayback;

public enum OverdraftPaybackRole {
    PAYBACK_PRINCIPAL,
    PAYBACK_COLLECTOR,
    PAYBACK_OVERPAID_CLEARING,
    PAYBACK_FEE;


    public boolean isPaybackPrincipal() {
        return this == PAYBACK_PRINCIPAL;
    }

    public boolean isOverpaid_CLEARING() {
        return this == PAYBACK_OVERPAID_CLEARING;
    }

    public boolean isFeeRole() {
        return this == PAYBACK_FEE;
    }
}
