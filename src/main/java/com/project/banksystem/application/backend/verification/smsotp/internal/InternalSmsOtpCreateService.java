package com.project.banksystem.application.backend.verification.smsotp.internal;

import com.project.banksystem.application.backend.verification.TwoFactorCodeManager;
import com.project.banksystem.application.backend.verification.body.TwoFactorCreatedResult;
import com.project.banksystem.application.backend.verification.builder.VerificationCodeEntityBuilder;
import com.project.banksystem.application.backend.verification.internal.InternalTwoFactorEntityTerminationService;
import com.project.banksystem.application.backend.verification.smsotp.SmsOtpCodeManager;
import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;
import com.project.banksystem.infrastructure.model.EmailObject;
import com.project.banksystem.infrastructure.model.SmsObject;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.SmsOtpCode;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.TwoFactorCode;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.entitybuilder.VerificationCodeEntityBuilderBody;
import com.project.banksystem.infrastructure.security.session.jwt.builder.JwtTokenBuilder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class InternalSmsOtpCreateService {
    private final SmsOtpCodeManager smsOtpCodeManager;
    private final JwtTokenBuilder jwtTokenBuilder;
    private final InternalTwoFactorEntityTerminationService internalTwoFactorEntityTerminationService;
    private final VerificationCodeEntityBuilder verificationCodeEntityBuilder;

    public InternalSmsOtpCreateService(SmsOtpCodeManager smsOtpCodeManager,
                                       JwtTokenBuilder jwtTokenBuilder,
                                       InternalTwoFactorEntityTerminationService internalTwoFactorEntityTerminationService,
                                       VerificationCodeEntityBuilder verificationCodeEntityBuilder) {
        this.smsOtpCodeManager = smsOtpCodeManager;
        this.jwtTokenBuilder = jwtTokenBuilder;
        this.internalTwoFactorEntityTerminationService = internalTwoFactorEntityTerminationService;
        this.verificationCodeEntityBuilder = verificationCodeEntityBuilder;
    }


    public TwoFactorCreatedResult createSmsOtpCode(final User user,
                                                      final String code,
                                                      final String deviceIp,
                                                      final LocalDateTime expiresAt,
                                                      final SmsObject smsObject,
                                                      final Instant expiry,
                                                      final TwoFactorPurpose twoFactorPurpose) {
        UUID userId = user.getUserId();

        internalTwoFactorEntityTerminationService
                .lockAndRevokeAllPreviousSmsOtpCodes(userId, twoFactorPurpose);

        VerificationCodeEntityBuilderBody.Builder defaultBuilder = verificationCodeEntityBuilder.getDefaultBuilder(user,
                code, twoFactorPurpose, deviceIp, expiresAt);

        SmsOtpCode smsOtpCode = smsOtpCodeManager.save(
                new SmsOtpCode(defaultBuilder
                        .smsObject(smsObject)
                        .build()));

        String twoFactorJwtToken = jwtTokenBuilder.generateTwoFactorToken(user,
                smsOtpCode.getCodeId(),
                expiry, twoFactorPurpose.getTokenTypeFromPurpose());

        return new TwoFactorCreatedResult(twoFactorJwtToken);
    }


}
