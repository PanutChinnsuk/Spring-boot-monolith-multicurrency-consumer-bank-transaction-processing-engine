package com.project.banksystem.application.backend.verification.internal;

import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;
import com.project.banksystem.infrastructure.customerfuncinterface.MultiFunction;
import com.project.banksystem.infrastructure.repository.SmsOtpCodeRepository;
import com.project.banksystem.infrastructure.repository.TwoFactorCodeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InternalTwoFactorEntityTerminationService {
    private final TwoFactorCodeRepository twoFactorCodeRepository;
    private final SmsOtpCodeRepository smsOtpCodeRepository;

    public InternalTwoFactorEntityTerminationService(TwoFactorCodeRepository twoFactorCodeRepository,
                                                     SmsOtpCodeRepository smsOtpCodeRepository) {
        this.twoFactorCodeRepository = twoFactorCodeRepository;
        this.smsOtpCodeRepository = smsOtpCodeRepository;
    }

    private Set<UUID> get(final List<UUID> list) {
        return new HashSet<>(list);
    }


    public void lockAndRevokeAllPreviousTwoFactorCodes(final UUID userId,
                                                       final TwoFactorPurpose twoFactorPurpose) {

        twoFactorCodeRepository.revokeAllPreviousTwoFactorCodes(
                get(twoFactorCodeRepository.getAllActiveByUserId(userId,
                LocalDateTime.now(),
                twoFactorPurpose))
        );

    }

    public void lockAndRevokeAllPreviousSmsOtpCodes(final UUID userId,
                                                       final TwoFactorPurpose twoFactorPurpose) {


        smsOtpCodeRepository.revokeAllPreviousTwoFactorCodes(
                get(smsOtpCodeRepository.getAllActiveByUserId(userId,
                        LocalDateTime.now(),
                        twoFactorPurpose))
        );
    }
}
