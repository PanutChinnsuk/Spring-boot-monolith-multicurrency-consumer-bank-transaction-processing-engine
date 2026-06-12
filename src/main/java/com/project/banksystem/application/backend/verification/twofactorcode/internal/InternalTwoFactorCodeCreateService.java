package com.project.banksystem.application.backend.verification.twofactorcode.internal;

import com.project.banksystem.application.backend.PasswordEncoderService;
import com.project.banksystem.application.backend.verification.TwoFactorCodeManager;
import com.project.banksystem.application.backend.verification.builder.VerificationCodeEntityBuilder;
import com.project.banksystem.application.backend.verification.internal.InternalTwoFactorEntityTerminationService;
import com.project.banksystem.application.backend.verification.body.TwoFactorCreatedResult;
import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;
import com.project.banksystem.infrastructure.model.EmailObject;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.TwoFactorCode;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.security.session.jwt.builder.JwtTokenBuilder;
import org.springframework.stereotype.Service;

import com.project.banksystem.infrastructure.model.sharedmodel.verification.entitybuilder.VerificationCodeEntityBuilderBody.Builder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class InternalTwoFactorCodeCreateService {
    private final TwoFactorCodeManager twoFactorCodeManager;
    private final JwtTokenBuilder jwtTokenBuilder;
    private final InternalTwoFactorEntityTerminationService internalTwoFactorEntityTerminationService;
    private final VerificationCodeEntityBuilder verificationCodeEntityBuilder;

    public InternalTwoFactorCodeCreateService(TwoFactorCodeManager twoFactorCodeManager,
                                              JwtTokenBuilder jwtTokenBuilder,
                                              InternalTwoFactorEntityTerminationService internalTwoFactorEntityTerminationService,
                                              VerificationCodeEntityBuilder verificationCodeEntityBuilder) {
        this.twoFactorCodeManager = twoFactorCodeManager;
        this.jwtTokenBuilder = jwtTokenBuilder;
        this.internalTwoFactorEntityTerminationService = internalTwoFactorEntityTerminationService;
        this.verificationCodeEntityBuilder = verificationCodeEntityBuilder;
    }



    public TwoFactorCreatedResult createTwoFactorCode(final User user,
                                                      final String code,
                                                      final String deviceIp,
                                                      final LocalDateTime expiresAt,
                                                      final EmailObject emailObject,
                                                      final Instant expiry,
                                                      final TwoFactorPurpose twoFactorPurpose) {
        UUID userId = user.getUserId();

        internalTwoFactorEntityTerminationService
                .lockAndRevokeAllPreviousTwoFactorCodes(userId, twoFactorPurpose);

        Builder defaultBuilder = verificationCodeEntityBuilder.getDefaultBuilder(user,
                code, twoFactorPurpose, deviceIp, expiresAt);
        
        TwoFactorCode twoFactorCode = twoFactorCodeManager.save(
                new TwoFactorCode(defaultBuilder
                        .emailObject(emailObject)
                        .build()));

        String twoFactorJwtToken = jwtTokenBuilder.generateTwoFactorToken(user,
                twoFactorCode.getCodeId(),
                expiry, twoFactorPurpose.getTokenTypeFromPurpose());



        return new TwoFactorCreatedResult(twoFactorJwtToken);
    }
}
