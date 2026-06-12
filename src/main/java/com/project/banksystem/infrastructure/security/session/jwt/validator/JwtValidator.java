package com.project.banksystem.infrastructure.security.session.jwt.validator;

import com.project.banksystem.core.enums.jwt.TokenType;
import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.exception.ex.Unauthorized;
import com.project.banksystem.infrastructure.security.key.ApiKeyProvider;
import com.project.banksystem.infrastructure.security.session.jwt.builder.JwtTokenBuilder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {
    private final ApiKeyProvider apiKeyProvider;

    public JwtValidator(ApiKeyProvider apiKeyProvider) {
        this.apiKeyProvider = apiKeyProvider;
    }


    private Claims claims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(apiKeyProvider.getApiKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public void validateMatchedSessionTokenType(final Claims tokenBody,
                                                final TokenType neededTokenType) {
        Object object = tokenBody.get(JwtTokenBuilder.JWT_CLAIM_TOKEN_TYPE);
        TokenType tokenType = TokenType.valueOf(object.toString());


        if (tokenType != neededTokenType) {
            throw new Forbidden("Mismatched token type.");
        }
    }


    public Claims getValidTokenBody(final String token) {
        try {
           return claims(token);
        }catch (JwtException jwtException) {
            throw new Unauthorized("Invalid token body.");
        }
    }

    public Claims getValidTokenBodyWithMatchedTokenType(final String token,
                                                        final TokenType neededTokenType) {
        try {
            Claims tokenBody = claims(token);
            validateMatchedSessionTokenType(tokenBody, neededTokenType);
            return tokenBody;
        }catch (JwtException jwtException) {
            throw new Unauthorized("Invalid token body.");
        }
    }





}
