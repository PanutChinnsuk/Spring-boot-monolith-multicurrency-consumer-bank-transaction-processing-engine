package com.project.banksystem.core.enums.twofactorcode;

import com.project.banksystem.core.enums.jwt.TokenType;

import java.util.EnumSet;

public enum TwoFactorPurpose {
    AUTHENTICATION,
    PASSWORD_CHANGE,
    EMAIL_ADDRESS_VERIFICATION,
    PHONE_NUMBER_VERIFICATION;

    public boolean isEmailSpecific() {
        return this == EMAIL_ADDRESS_VERIFICATION;
    }

    public boolean isPhoneSpecific() {
        return this == PHONE_NUMBER_VERIFICATION;
    }

    public String getSubject() {
        return switch (this) {
            case AUTHENTICATION -> "User Authentication 2-Step Verification";
            case PASSWORD_CHANGE -> "Password Change 2-Step Verification";
            case EMAIL_ADDRESS_VERIFICATION -> "Email Address 2-Step Verification";
            case PHONE_NUMBER_VERIFICATION -> "Phone Number 2-Step Verification";
        };
    }


    public boolean isRequiredVerifiedPrincipal() {
        return switch (this) {
            case AUTHENTICATION, PASSWORD_CHANGE  -> true;
            case EMAIL_ADDRESS_VERIFICATION, PHONE_NUMBER_VERIFICATION -> false;
        };
    }

    public TokenType getTokenTypeFromPurpose() {
        return switch (this) {
            case EMAIL_ADDRESS_VERIFICATION -> TokenType.TWO_FACTOR_EMAIL_ADDRESS;
            case AUTHENTICATION -> TokenType.TWO_FACTOR_LOGIN;
            case PASSWORD_CHANGE -> TokenType.TWO_FACTOR_PASSWORD_RESET;
            case PHONE_NUMBER_VERIFICATION -> TokenType.TWO_FACTOR_PHONE_NUMBER;
        };
    }
}
