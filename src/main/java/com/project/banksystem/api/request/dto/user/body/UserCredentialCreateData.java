package com.project.banksystem.api.request.dto.user.body;

import com.project.banksystem.infrastructure.annotation.customvalidator.anno.fieldsvalidation.Pin;
import jakarta.validation.constraints.NotBlank;

public class UserCredentialCreateData {
    @NotBlank
    private String userPassword;

    @NotBlank
    private String userConfirmedPassword;

    @Pin
    private String personalPin;

    @Pin
    private String confirmedPersonalPin;

    public UserCredentialCreateData() {

    }

    public void setConfirmedPersonalPin(String confirmedPersonalPin) {
        this.confirmedPersonalPin = confirmedPersonalPin;
    }

    public void setUserConfirmedPassword(String userConfirmedPassword) {
        this.userConfirmedPassword = userConfirmedPassword;
    }

    public void setPersonalPin(String personalPin) {
        this.personalPin = personalPin;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getConfirmedPersonalPin() {
        return confirmedPersonalPin;
    }

    public String getUserConfirmedPassword() {
        return userConfirmedPassword;
    }

    public String getPersonalPin() {
        return personalPin;
    }

    public String getUserPassword() {
        return userPassword;
    }
}
