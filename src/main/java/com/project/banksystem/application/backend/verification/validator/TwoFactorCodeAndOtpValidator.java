package com.project.banksystem.application.backend.verification.validator;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.backend.PasswordEncoderService;
import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.VerificationCode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.UUID;

@Component
public class TwoFactorCodeAndOtpValidator {
    private final PasswordEncoderService passwordEncoderService;

    public TwoFactorCodeAndOtpValidator(PasswordEncoderService passwordEncoderService) {
        this.passwordEncoderService = passwordEncoderService;
    }

    private final EnumSet<TwoFactorPurpose> notAllowedOnManualRequest = EnumSet.of(
            TwoFactorPurpose.AUTHENTICATION
    );

    public void validateEmailOnly(final TwoFactorPurpose twoFactorPurpose) {
        if (twoFactorPurpose.isPhoneSpecific()) {
            throw new BusinessException("Request is invalid as two factor is for email only.");
        }
    }

    public void validateAllowedTwoFactorPurpose(final TwoFactorPurpose twoFactorPurpose) {
        if (notAllowedOnManualRequest.contains(twoFactorPurpose)) {
            throw new BusinessException("Prohibited two factor request.");
        }
    }


    public void validatePhoneOnly(final TwoFactorPurpose twoFactorPurpose) {
        if (twoFactorPurpose.isEmailSpecific()) {
            throw new BusinessException("Request is invalid as two factor is for sms only.");
        }
    }

    public void validateTwoFactorCode(final VerificationCode verificationCodeEntity,
                                      final User user,
                                      final String deviceIp,
                                      final UUID userIdFromToken,
                                      final String verificationCode,
                                      final TwoFactorPurpose twoFactorPurpose) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoFactorCodeExpiry = verificationCodeEntity.getExpiresAt();

        UUID userId = user.getUserId();

        boolean isExpired = twoFactorCodeExpiry.isBefore(now) ||
                twoFactorCodeExpiry.isEqual(now);
        boolean isRevoked = verificationCodeEntity.getRevoked();
        boolean userIdMatched = userId.equals(userIdFromToken);
        boolean userIdMatchedCurrentUserId = userId.equals(CurrentUser.getCurrentUserId());
        boolean ipMatched = verificationCodeEntity.getDeviceIp().equals(deviceIp);
        boolean isCodeMatched = passwordEncoderService.isMatched(verificationCode,
                verificationCodeEntity.getCode());
        boolean isPurposeMatched = verificationCodeEntity.getTwoFactorPurpose() ==
                twoFactorPurpose;

        if (isExpired || isRevoked || !userIdMatched || !ipMatched || !isCodeMatched ||
        !isPurposeMatched || !userIdMatchedCurrentUserId) {
            throw new Forbidden("Invalid two factor code.");
        }


    }
}
