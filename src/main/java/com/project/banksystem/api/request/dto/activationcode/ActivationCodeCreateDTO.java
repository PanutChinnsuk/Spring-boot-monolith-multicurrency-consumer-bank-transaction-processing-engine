package com.project.banksystem.api.request.dto.activationcode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class ActivationCodeCreateDTO {
    @NotNull
    private UUID userId;

    @NotBlank
    private String code;


    public ActivationCodeCreateDTO() {

    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getCode() {
        return code;
    }
}
