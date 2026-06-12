package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.email.EmailType;
import com.project.banksystem.core.enums.sms.SmsType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sms_objects")
@Immutable
public class SmsObject {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "sms_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID smsId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sms_type", nullable = false, updatable = false)
    private SmsType smsType;

    @NotBlank
    @Column(name = "subject", nullable = false, updatable = false)
    private String subject;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sent_to_user_id", nullable = false, updatable = false)
    private User user;

    @NotBlank
    @Column(name = "sent_to", nullable = false, updatable = false)
    private String sentTo;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected SmsObject() {

    }

    public SmsObject(SmsType smsType,
                     String subject,
                     User user,
                     String sentTo) {
        this.smsType = smsType;
        this.subject = subject;
        this.user = user;
        this.sentTo = sentTo;
    }

    @PreUpdate
    private void preventUpdate() {
        throw new IllegalStateException("Model is immutable.");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        SmsType smsType;
        String subject;
        User user;
        String sentTo;

        public Builder smsType(SmsType smsType) {
            this.smsType = smsType;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder sentTo(String sentTo) {
            this.sentTo = sentTo;
            return this;
        }

        public SmsObject build() {
            return new SmsObject(smsType, subject, user, sentTo);
        }
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getSubject() {
        return subject;
    }

    public SmsType getSmsType() {
        return smsType;
    }

    public String getSentTo() {
        return sentTo;
    }

    public UUID getSmsId() {
        return smsId;
    }
}
