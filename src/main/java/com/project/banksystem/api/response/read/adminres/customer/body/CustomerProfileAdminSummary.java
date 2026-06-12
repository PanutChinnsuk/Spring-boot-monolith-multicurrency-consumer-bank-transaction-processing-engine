package com.project.banksystem.api.response.read.adminres.customer.body;

import java.time.LocalDate;
import java.util.UUID;

public class CustomerProfileAdminSummary {
    private UUID customerProfileId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String nationality;
    private String nationalId;

    public CustomerProfileAdminSummary(UUID customerProfileId,
                                       String firstName,
                                       String lastName,
                                       LocalDate dateOfBirth,
                                       String nationality,
                                       String nationalId) {
        this.customerProfileId = customerProfileId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.nationalId = nationalId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UUID customerProfileId;
        String firstName;
        String lastName;
        LocalDate dateOfBirth;
        String nationality;
        String nationalId;

        public Builder customerProfileId(UUID customerProfileId) {
            this.customerProfileId = customerProfileId;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder nationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public Builder nationalId(String nationalId) {
            this.nationalId = nationalId;
            return this;
        }

        public CustomerProfileAdminSummary build() {
            return new CustomerProfileAdminSummary(customerProfileId
                    ,firstName, lastName, dateOfBirth,nationality, nationalId);
        }
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getNationality() {
        return nationality;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
