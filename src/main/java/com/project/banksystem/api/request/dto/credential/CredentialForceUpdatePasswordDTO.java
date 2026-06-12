package com.project.banksystem.api.request.dto.credential;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CredentialForceUpdatePasswordDTO {
    @NotNull
    private UUID userId;

    @NotBlank
    private String password;




    public CredentialForceUpdatePasswordDTO() {


    }


    public void setUserId(UUID userId) {
        this.userId = userId;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}

