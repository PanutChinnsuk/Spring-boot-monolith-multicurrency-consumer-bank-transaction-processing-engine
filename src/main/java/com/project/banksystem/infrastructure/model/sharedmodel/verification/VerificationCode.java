package com.project.banksystem.infrastructure.model.sharedmodel.verification;

import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;
import com.project.banksystem.infrastructure.model.CustomerContact;
import com.project.banksystem.infrastructure.model.EmailObject;
import com.project.banksystem.infrastructure.model.SmsObject;
import com.project.banksystem.infrastructure.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

public interface VerificationCode {
    void revokeTwoFactorCode();
    LocalDateTime getCreatedAt();

    User getUser();

    String getDeviceIp();

    LocalDateTime getExpiresAt();

    Boolean getRevoked();

    String getCode();

    EmailObject getEmailObject();

    UUID getCodeId();

    TwoFactorPurpose getTwoFactorPurpose();

    SmsObject getSmsObject();
}
