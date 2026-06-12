package com.project.banksystem.infrastructure.model;

import com.project.banksystem.infrastructure.annotation.customvalidator.anno.fieldsvalidation.LegalNameFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "customer_profiles")
@DynamicUpdate
public class CustomerProfile {
    @Id
    private UUID id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false, unique = true)
    private Customer customer;

    @Version
    @Column(nullable = false)
    private Long version;

    @LegalNameFormat
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @LegalNameFormat
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotBlank
    @Column(name = "nationality", nullable = false)
    private String nationality;

    @NotBlank
    @Column(name = "national_id", nullable = false, unique = true)
    private String nationalId;

    protected CustomerProfile() {

    }

    public CustomerProfile(Customer customer,
                           String firstName,
                           String lastName,
                           LocalDate dateOfBirth,
                           String nationality,
                           String nationalId) {
        this.customer = customer;
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
        Customer customer;
        String firstName;
        String lastName;
        LocalDate dateOfBirth;
        String nationality;
        String nationalId;

        public Builder customer(Customer customer) {
            this.customer = customer;
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

        public CustomerProfile build() {
            return new CustomerProfile(customer, firstName, lastName, dateOfBirth, nationality,
                    nationalId);
        }
    }

    public void changeFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void changeLastName(String lastName) {
        this.lastName = lastName;
    }

    public void changeDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void changeNationality(String nationality) {
        this.nationality = nationality;
    }

    public void changeNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public UUID getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public String getNationalId() {
        return nationalId;
    }
}
