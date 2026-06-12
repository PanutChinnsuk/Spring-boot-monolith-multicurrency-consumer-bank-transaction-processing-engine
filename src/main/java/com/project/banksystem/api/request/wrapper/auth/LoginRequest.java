package com.project.banksystem.api.request.wrapper.auth;

import com.project.banksystem.api.request.dto.auth.UserLoginDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;


public class LoginRequest {
    @Valid
    @NotNull
    private UserLoginDTO userLoginDTO;

    public LoginRequest() {

    }

    public UserLoginDTO getUserLoginDTO() {
        return userLoginDTO;
    }

    public void setUserLoginDTO(UserLoginDTO userLoginDTO) {
        this.userLoginDTO = userLoginDTO;
    }
}
