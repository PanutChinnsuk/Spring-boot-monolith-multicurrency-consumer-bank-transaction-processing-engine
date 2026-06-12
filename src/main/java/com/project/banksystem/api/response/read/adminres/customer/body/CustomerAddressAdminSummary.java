package com.project.banksystem.api.response.read.adminres.customer.body;

import com.project.banksystem.core.enums.customer.AddressType;

import java.util.UUID;

public class CustomerAddressAdminSummary {
    private UUID addressId;
    private AddressType addressType;
    private String country;
    private String city;
    private String line1;
    private String postalCode;
    private Boolean verified;

    public CustomerAddressAdminSummary(UUID addressId,
                                       AddressType addressType,
                                       String country,
                                       String city,
                                       String line1,
                                       String postalCode,
                                       Boolean verified) {
        this.addressId = addressId;
        this.addressType = addressType;
        this.country = country;
        this.city = city;
        this.line1 = line1;
        this.postalCode = postalCode;
        this.verified = verified;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        UUID addressId;
        AddressType addressType;
        String country;
        String city;
        String line1;
        String postalCode;
        Boolean verified;

        public Builder addressId(UUID addressId) {
            this.addressId = addressId;
            return this;
        }

        public Builder addressType(AddressType addressType) {
            this.addressType = addressType;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder line1(String line1) {
            this.line1 = line1;
            return this;
        }

        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder verified(Boolean verified) {
            this.verified = verified;
            return this;
        }

        public CustomerAddressAdminSummary build() {
            return new CustomerAddressAdminSummary(addressId, addressType,country,city,line1,postalCode,verified);
        }
    }

    public UUID getAddressId() {
        return addressId;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getLine1() {
        return line1;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public Boolean getVerified() {
        return verified;
    }
}
