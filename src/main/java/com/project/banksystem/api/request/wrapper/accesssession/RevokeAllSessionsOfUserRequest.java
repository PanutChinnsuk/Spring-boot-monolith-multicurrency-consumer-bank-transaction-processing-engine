package com.project.banksystem.api.request.wrapper.accesssession;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


public class RevokeAllSessionsOfUserRequest {
    @NotNull
    private UUID userId;

    public RevokeAllSessionsOfUserRequest() {

    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}
