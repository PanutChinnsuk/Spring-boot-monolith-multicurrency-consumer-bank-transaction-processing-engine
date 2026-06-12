package com.project.banksystem.infrastructure.model.sharedmodel.verification.entitybuilder;

import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;
import com.project.banksystem.infrastructure.model.EmailObject;
import com.project.banksystem.infrastructure.model.SmsObject;
import com.project.banksystem.infrastructure.model.User;

import java.time.LocalDateTime;

public class VerificationCodeEntityBuilderBody {
    private User user;
    private String code;
    private String deviceIp;
    private LocalDateTime expiresAt;
    private TwoFactorPurpose twoFactorPurpose;

    private SmsObject smsObject;
    private EmailObject emailObject;
    
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final VerificationCodeEntityBuilderBody body =
                new VerificationCodeEntityBuilderBody();



        public Builder user(User user) {
            body.user = user;
            return this;
        }

        public Builder code(String code) {
            body.code = code;
            return this;
        }

        public Builder deviceIp(String deviceIp) {
            body.deviceIp = deviceIp;
            return this;
        }


        public Builder expiresAt(LocalDateTime expiresAt) {
            body.expiresAt = expiresAt;
            return this;
        }


        public Builder twoFactorPurpose(TwoFactorPurpose twoFactorPurpose) {
            body.twoFactorPurpose = twoFactorPurpose;
            return this;
        }

        public Builder smsObject(SmsObject smsObject) {
            body.smsObject = smsObject;
            return this;
        }

        public Builder emailObject(EmailObject emailObject) {
            body.emailObject = emailObject;
            return this;
        }

        public VerificationCodeEntityBuilderBody build() {
            return body;
        }
    }



    public User user() {
        return user;
    }

    public String code() {
        return code;
    }

    public String deviceIp() {
        return deviceIp;
    }



    public LocalDateTime expiresAt() {
        return expiresAt;
    }



    public TwoFactorPurpose twoFactorPurpose() {
        return twoFactorPurpose;
    }

    public SmsObject smsObject() {
        return smsObject;
    }

    public EmailObject emailObject() {
        return emailObject;
    }
}