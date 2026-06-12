package com.project.banksystem.infrastructure.security.session.jwt.builder;

import com.project.banksystem.core.enums.jwt.TokenType;
import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.security.session.properties.SessionConfig;
import com.project.banksystem.infrastructure.security.session.jwt.validator.JwtTokenTypeValidator;
import com.project.banksystem.infrastructure.security.key.ApiKeyProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Component
public class JwtTokenBuilder {
    private final ApiKeyProvider apiKeyProvider;
    private final SessionConfig sessionConfig;

    public JwtTokenBuilder(ApiKeyProvider apiKeyProvider,
                           SessionConfig sessionConfig) {
        this.apiKeyProvider = apiKeyProvider;
        this.sessionConfig = sessionConfig;
    }

    public static final String JWT_CLAIM_TOKEN_TYPE = "tokenType";

    private JwtBuilder token(final User user,
                         final Instant expiry,
                             final UUID id,
                             final TokenType tokenType) {
        return Jwts.builder()
                .setSubject(user.getUserId().toString())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(expiry))
                .setId(id.toString())
                .claim(JWT_CLAIM_TOKEN_TYPE, tokenType);
    }

    private String finalizeToken(final JwtBuilder jwtBuilder) {
        return jwtBuilder
                .signWith(apiKeyProvider.getApiKey())
                .compact();
    }



    public String generateAccessToken(final User user,
                                      final Customer customer,
                                      final Set<String> roles,
                                      final UUID sessionId,
                                      final Instant expiry,
                                      final Long version) {
        return finalizeToken(token(user, expiry, sessionId, TokenType.ACCESS)
                .claim("customerId", customer.getCustomerId())
                .claim("roles", roles)
                .claim("version", version));
    }


    public String generatePaymentToken(final User user,
                                       final UUID paymentSessionId,
                                       final Instant expiry,
                                       final TokenType tokenType,
                                       final BigDecimal amountForResolve,
                                       final UUID cardIdForResolve,
                                       final UUID targetCardIdForResolve) {
        JwtTokenTypeValidator.assertPaymentTokenType(tokenType);


        return finalizeToken(token(user, expiry, paymentSessionId, tokenType)
                .claim("resolvedCardId", cardIdForResolve)
                .claim("resolvedAmount", amountForResolve)
                .claim("resolvedTargetCardId", targetCardIdForResolve));
    }

    public String generateOverdraftPaymentToken(final User user,
                                                final UUID overdraftPaymentSessionId,
                                                final Instant expiry,
                                                final TokenType tokenType) {

        return finalizeToken(token(user, expiry, overdraftPaymentSessionId,
                tokenType));
    }

    public String generateLoanPaymentToken(final User user,
                                           final UUID loanPaymentSessionId,
                                           final Instant expiry,
                                           final TokenType tokenType) {
        return finalizeToken(token(user, expiry, loanPaymentSessionId,
                tokenType));
    }

    public String generateRefreshToken(final User user,
                                       final UUID refreshTokenId,
                                       final Instant expiry) {
        return finalizeToken(token(user, expiry, refreshTokenId, TokenType.REFRESH));
    }


    public String generateTwoFactorToken(final User user,
                                         final UUID twoFactorCodeId,
                                         final Instant expiry,
                                         final TokenType tokenType) {
        return finalizeToken(token(user, expiry, twoFactorCodeId, tokenType));
    }

}
