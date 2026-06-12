package com.project.banksystem.api.request.dto.credential;

import com.project.banksystem.infrastructure.annotation.customvalidator.anno.fieldsvalidation.Pin;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CredentialForceUpdatePinDTO {
    @NotNull
    private UUID userId;

    @Pin
    private String pin;




    public CredentialForceUpdatePinDTO() {


    }


    public void setUserId(UUID userId) {
        this.userId = userId;
    }


    public void setPin(String pin) {
        this.pin = pin;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }
}

