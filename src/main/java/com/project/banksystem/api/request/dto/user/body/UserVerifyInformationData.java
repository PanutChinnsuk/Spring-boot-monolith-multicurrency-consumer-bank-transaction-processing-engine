package com.project.banksystem.api.request.dto.user.body;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class UserVerifyInformationData {
    @NotBlank
    private String userName;

    @NotBlank
    private String nationalId;

    @NotNull
    private LocalDate dateOfBirth;

    public UserVerifyInformationData() {

    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getNationalId() {
        return nationalId;
    }
}
