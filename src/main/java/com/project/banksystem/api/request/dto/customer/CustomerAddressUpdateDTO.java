package com.project.banksystem.api.request.dto.customer;

import com.project.banksystem.infrastructure.annotation.customvalidator.anno.fieldsvalidation.NotBlankIfProvided;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;


public class CustomerAddressUpdateDTO {
    @NotNull
    private UUID customerAddressId;

    @NotBlankIfProvided
    private String country;

    @NotBlankIfProvided
    private String city;

    @NotBlankIfProvided
    private String line1;

    @NotBlankIfProvided
    private String postalCode;


    public CustomerAddressUpdateDTO() {

    }

    public void setCustomerAddressId(UUID customerAddressId) {
        this.customerAddressId = customerAddressId;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public UUID getCustomerAddressId() {
        return customerAddressId;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }


    public String getLine1() {
        return line1;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
