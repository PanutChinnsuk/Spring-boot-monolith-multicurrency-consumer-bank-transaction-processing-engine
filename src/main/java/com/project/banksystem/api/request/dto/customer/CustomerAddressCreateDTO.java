package com.project.banksystem.api.request.dto.customer;

import com.project.banksystem.core.enums.customer.AddressType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;


public class CustomerAddressCreateDTO {
    @NotNull
    private UUID customerId;

    @NotNull
    private AddressType addressType;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
    private String line1;

    @NotBlank
    private String postalCode;



    public CustomerAddressCreateDTO() {

    }


    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
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

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }


    public UUID getCustomerId() {
        return customerId;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public String getLine1() {
        return line1;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
