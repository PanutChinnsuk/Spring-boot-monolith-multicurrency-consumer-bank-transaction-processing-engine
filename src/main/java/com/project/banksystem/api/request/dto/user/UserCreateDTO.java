package com.project.banksystem.api.request.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class UserCreateDTO {
    @NotNull
    private UUID customerId;

    @NotBlank
    private String userName;


    public UserCreateDTO() {

    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public UUID getCustomerId() {
        return customerId;
    }



    public String getUserName() {
        return userName;
    }
}
