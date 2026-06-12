package com.project.banksystem.infrastructure.model;

import com.mysql.cj.MysqlType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "audit_logs")
@Immutable
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "audit_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID auditId;

    @NotNull
    @Column(name = "user_actor_id", columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID userActorId;

    @NotBlank
    @Column(name = "action", nullable = false, updatable = false)
    private String action;

    @NotBlank
    @Column(name = "entity", nullable = false, updatable = false)
    private String entity;

    @NotBlank
    @Column(name = "entity_id", nullable = false, updatable = false)
    private String entityId;

    @NotNull
    @Column(name = "success", nullable = false, updatable = false)
    private Boolean success;


    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected AuditLog() {

    }

    public AuditLog(UUID userActorId,
                    String action,
                    String entity,
                    Boolean success,
                    String entityId) {
        this.userActorId = userActorId;
        this.action = action;
        this.entity = entity;
        this.success = success;
        this.entityId = entityId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UUID userActorId;
        String action;
        String entity;
        Boolean success;
        String entityId;

        public Builder userActorId(UUID userActorId) {
            this.userActorId = userActorId;
            return this;
        }

        public Builder action(String action) {
            this.action = action;
            return this;
        }

        public Builder entity(String entity) {
            this.entity = entity;
            return this;
        }

        public Builder success(Boolean success) {
            this.success = success;
            return this;
        }

        public Builder entityId(String entityId) {
            this.entityId = entityId;
            return this;
        }


        public AuditLog build() {
            return new AuditLog(userActorId, action, entity, success, entityId);
        }
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getAction() {
        return action;
    }


    public String getEntity() {
        return entity;
    }

    public UUID getUserActorId() {
        return userActorId;
    }

    public UUID getAuditId() {
        return auditId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getEntityId() {
        return entityId;
    }
}
