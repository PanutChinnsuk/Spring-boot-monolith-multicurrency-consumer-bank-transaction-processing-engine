package com.project.banksystem.api.request.dto.auth;

import jakarta.validation.constraints.NotBlank;

public class UserLoginDTO {
    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    public UserLoginDTO() {

    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
