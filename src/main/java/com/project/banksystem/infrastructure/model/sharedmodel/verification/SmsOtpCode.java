package com.project.banksystem.infrastructure.model.sharedmodel.verification;

import com.project.banksystem.core.enums.twofactorcode.TwoFactorPurpose;
import com.project.banksystem.infrastructure.model.EmailObject;
import com.project.banksystem.infrastructure.model.SmsObject;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.entitybuilder.VerificationCodeEntityBuilderBody;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sms_otp_codes")
@DynamicUpdate
public class SmsOtpCode implements VerificationCode{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "code_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID codeId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @NotBlank
    @Column(name = "code", nullable = false, updatable = false)
    private String code;

    @NotBlank
    @Column(name = "device_ip", nullable = false, updatable = false)
    private String deviceIp;

    @NotNull
    @Column(name = "revoked", nullable = false)
    private Boolean revoked;

    @NotNull
    @Column(name = "expires_at", nullable = false, updatable = false)
    private LocalDateTime expiresAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sms_id", nullable = false, updatable = false)
    private SmsObject smsObject;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "two_factor_purpose", nullable = false, updatable = false)
    private TwoFactorPurpose twoFactorPurpose;

    protected SmsOtpCode() {

    }

    public SmsOtpCode(VerificationCodeEntityBuilderBody body) {
        this.user = body.user();
        this.code = body.code();
        this.deviceIp = body.deviceIp();
        this.expiresAt = body.expiresAt();
        this.smsObject = body.smsObject();
        this.twoFactorPurpose = body.twoFactorPurpose();
    }

    @PrePersist
    private void defaultValue() {
        this.revoked = false;
    }



    @Override
    public void revokeTwoFactorCode() {
        this.revoked = true;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public String getDeviceIp() {
        return deviceIp;
    }

    @Override
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    @Override
    public Boolean getRevoked() {
        return revoked;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public EmailObject getEmailObject() {
        return null;
    }

    @Override
    public SmsObject getSmsObject() {
        return smsObject;
    }

    @Override
    public UUID getCodeId() {
        return codeId;
    }

    @Override
    public TwoFactorPurpose getTwoFactorPurpose() {
        return twoFactorPurpose;
    }
}
