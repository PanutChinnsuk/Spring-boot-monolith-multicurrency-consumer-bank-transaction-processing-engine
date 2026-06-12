package com.project.banksystem.infrastructure.security.session.jwt.validator;

import com.project.banksystem.core.enums.jwt.TokenType;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;

import java.util.EnumSet;

public final class JwtTokenTypeValidator {
    private JwtTokenTypeValidator() {

    }

    private static final EnumSet<TokenType> allowedPaymentTokenTypes = EnumSet.of(
            TokenType.CARD_PAYMENT,
            TokenType.ACCOUNT_PAYMENT
    );

    public static void assertPaymentTokenType(final TokenType tokenType) {
        if (!allowedPaymentTokenTypes.contains(tokenType)) {
            throw new BusinessException("Token type is prohibited.");
        }
    }
}
