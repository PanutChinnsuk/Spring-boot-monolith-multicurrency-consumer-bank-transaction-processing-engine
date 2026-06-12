package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.email.EmailType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "email_objects")
@Immutable
public class EmailObject {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "email_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID emailId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "email_type", nullable = false, updatable = false)
    private EmailType emailType;

    @NotBlank
    @Column(name = "subject", nullable = false, updatable = false)
    private String subject;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sent_to_user_id", nullable = false, updatable = false)
    private User user;

    @NotBlank
    @Email
    @Column(name = "sent_to", nullable = false, updatable = false)
    private String sentTo;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected EmailObject() {

    }

    public EmailObject(EmailType emailType,
                       String subject,
                       User user,
                       String sentTo) {
        this.emailType = emailType;
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
        EmailType emailType;
        String subject;
        User user;
        String sentTo;

        public Builder emailType(EmailType emailType) {
            this.emailType = emailType;
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

        public EmailObject build() {
            return new EmailObject(emailType, subject, user, sentTo);
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

    public EmailType getEmailType() {
        return emailType;
    }

    public String getSentTo() {
        return sentTo;
    }

    public UUID getEmailId() {
        return emailId;
    }
}
