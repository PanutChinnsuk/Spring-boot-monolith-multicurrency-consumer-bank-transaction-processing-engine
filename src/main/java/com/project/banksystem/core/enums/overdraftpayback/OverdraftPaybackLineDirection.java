package com.project.banksystem.core.enums.overdraftpayback;

public enum OverdraftPaybackLineDirection {
    PAYBACK,
    GAIN;


    public boolean isPaybackSide() {
        return this == PAYBACK;
    }

    public boolean isGainSide() {
        return this == GAIN;
    }
}
