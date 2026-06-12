package com.project.banksystem.api.request.dto.user;

import com.project.banksystem.api.request.dto.user.body.UserCredentialCreateData;
import com.project.banksystem.api.request.dto.user.body.UserVerifyInformationData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserActivationDTO {
    @NotBlank
    private String activationCode;

    @NotNull
    @Valid
    private UserVerifyInformationData userVerifyInformationData;

    @NotNull
    @Valid
    private UserCredentialCreateData userCredentialCreateData;

    public UserActivationDTO() {

    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public void setUserCredentialCreateData(UserCredentialCreateData userCredentialCreateData) {
        this.userCredentialCreateData = userCredentialCreateData;
    }

    public void setUserVerifyInformationData(UserVerifyInformationData userVerifyInformationData) {
        this.userVerifyInformationData = userVerifyInformationData;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public UserCredentialCreateData getUserCredentialCreateData() {
        return userCredentialCreateData;
    }

    public UserVerifyInformationData getUserVerifyInformationData() {
        return userVerifyInformationData;
    }
}
