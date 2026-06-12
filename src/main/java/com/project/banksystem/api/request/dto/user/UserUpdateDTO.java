package com.project.banksystem.api.request.dto.user;

import com.project.banksystem.infrastructure.annotation.customvalidator.anno.fieldsvalidation.NotBlankIfProvided;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class UserUpdateDTO {
    @NotNull
    private UUID userId;

    @NotBlankIfProvided
    private String userName;

    public UserUpdateDTO() {

    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public UUID getUserId() {
        return userId;
    }
}
