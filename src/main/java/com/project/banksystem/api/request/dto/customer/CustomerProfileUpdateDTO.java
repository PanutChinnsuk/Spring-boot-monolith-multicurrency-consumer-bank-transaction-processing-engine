package com.project.banksystem.api.request.dto.customer;

import com.project.banksystem.infrastructure.annotation.customvalidator.anno.fieldsvalidation.NotBlankIfProvided;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class CustomerProfileUpdateDTO {
    @NotNull
    private UUID customerId;

    @NotBlankIfProvided
    private String firstName;

    @NotBlankIfProvided
    private String lastName;

    private LocalDate dateOfBirth;

    @NotBlankIfProvided
    private String nationality;

    @NotBlankIfProvided
    private String nationalId;

    public CustomerProfileUpdateDTO() {

    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
