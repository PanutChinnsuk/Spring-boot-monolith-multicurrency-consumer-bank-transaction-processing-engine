package com.project.banksystem.api.request.wrapper.accesssession;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class RevokeSessionBySessionIdRequest {
    @NotNull
    private UUID accessSessionId;

    public RevokeSessionBySessionIdRequest() {

    }

    public void setAccessSessionId(UUID accessSessionId) {
        this.accessSessionId = accessSessionId;
    }

    public UUID getAccessSessionId() {
        return accessSessionId;
    }
}
