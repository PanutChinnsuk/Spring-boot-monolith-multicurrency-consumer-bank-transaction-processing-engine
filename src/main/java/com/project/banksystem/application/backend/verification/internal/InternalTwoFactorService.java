package com.project.banksystem.application.backend.verification.internal;

import com.project.banksystem.application.backend.email.internal.InternalEmailSenderService;
import com.project.banksystem.application.backend.email.internal.InternalEmailObjectCreateService;
import com.project.banksystem.application.backend.verification.TwoFactorCodeGenerator;
import com.project.banksystem.application.backend.verification.internal.body.TwoFactorDataResultFromRequest;
import com.project.banksystem.application.backend.verification.twofactorcode.internal.InternalTwoFactorCodeCreateService;
import com.project.banksystem.application.backend.verification.twofactorcode.policy.TwoFactorVerificationPolicy;
import com.project.banksystem.core.enums.jwt.TokenType;
import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.http.request.HttpServletRequestIpResolver;
import com.project.banksystem.infrastructure.http.request.HttpServletRequestTokenResolver;
import com.project.banksystem.infrastructure.security.session.jwt.validator.JwtValidator;
import com.project.banksystem.infrastructure.security.session.properties.SessionConfig;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InternalTwoFactorService {
    private final JwtValidator jwtValidator;
    private final InternalEmailSenderService internalEmailSenderService;
    private final SessionConfig sessionConfig;
    private final TwoFactorCodeGenerator twoFactorCodeGenerator;
    private final InternalEmailObjectCreateService internalEmailObjectCreateService;
    private final InternalTwoFactorCodeCreateService internalTwoFactorCodeCreateService;
    private final TwoFactorVerificationPolicy twoFactorVerificationPolicy;

    public InternalTwoFactorService(JwtValidator jwtValidator,
                                    InternalEmailSenderService internalEmailSenderService,
                                    SessionConfig sessionConfig,
                                    TwoFactorCodeGenerator twoFactorCodeGenerator,
                                    InternalEmailObjectCreateService internalEmailObjectCreateService,
                                    InternalTwoFactorCodeCreateService internalTwoFactorCodeCreateService,
                                    TwoFactorVerificationPolicy twoFactorVerificationPolicy) {
        this.jwtValidator = jwtValidator;
        this.internalEmailSenderService = internalEmailSenderService;
        this.sessionConfig = sessionConfig;
        this.twoFactorCodeGenerator = twoFactorCodeGenerator;
        this.internalEmailObjectCreateService = internalEmailObjectCreateService;
        this.internalTwoFactorCodeCreateService = internalTwoFactorCodeCreateService;
        this.twoFactorVerificationPolicy = twoFactorVerificationPolicy;
    }


    public TwoFactorDataResultFromRequest extractDataForVerify(HttpServletRequest httpServletRequest,
                                                               @Nullable final TokenType neededTokenType) {
        String token = HttpServletRequestTokenResolver.getTokenFromHeader(
                httpServletRequest, "TwoFactorToken");

        if (token == null) {
            throw new Forbidden("Invalid two factor token header.");
        }

        Claims claims = jwtValidator.getValidTokenBody(token);

        if (neededTokenType != null) {
            jwtValidator.validateMatchedSessionTokenType(claims, neededTokenType);
        }

        String deviceIp = HttpServletRequestIpResolver.getDeviceIpFromRequest(httpServletRequest);
        UUID codeId = UUID.fromString(claims.getId());
        UUID userIdFromToken = UUID.fromString(claims.getSubject());


        return new TwoFactorDataResultFromRequest(deviceIp, codeId, userIdFromToken);

    }

}
