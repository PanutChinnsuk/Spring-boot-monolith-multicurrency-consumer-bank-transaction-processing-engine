package com.project.banksystem.infrastructure.model;

import com.project.banksystem.core.enums.customer.AddressType;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.fieldsvalidation.NotBlankIfProvided;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Entity
@Table(name = "customer_addresses", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"customer_id", "type"})
})
@DynamicUpdate
public class CustomerAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "address_id", columnDefinition = "BINARY(16)", updatable = false)
    private UUID addressId;

    @Version
    @Column(nullable = false)
    private Long version;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, updatable = false)
    private AddressType addressType;

    @NotBlank
    @Column(name = "country", nullable = false)
    private String country;

    @NotBlank
    @Column(name = "city", nullable = false)
    private String city;

    @NotBlank
    @Column(name = "line1", nullable = false)
    private String line1;

    @NotBlankIfProvided
    @Column(name = "postal_code")
    private String postalCode;

    @NotNull
    @Column(name = "verified", nullable = false)
    private Boolean verified;

    protected CustomerAddress() {

    }

    public CustomerAddress(Customer customer,
                           AddressType addressType,
                           String country,
                           String city,
                           String line1,
                           String postalCode) {
        this.customer = customer;
        this.addressType = addressType;
        this.country = country;
        this.city = city;
        this.line1 = line1;
        this.postalCode = postalCode;

    }

    @PrePersist
    private void defaultValue() {
        this.verified = true;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Customer customer;
        AddressType addressType;
        String country;
        String city;
        String line1;
        String postalCode;


        public Builder customer(Customer customer) {
            this.customer = customer;
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



        public CustomerAddress build() {
            return new CustomerAddress(customer, addressType, country, city, line1, postalCode);
        }
    }


    public void changeCountry(String country) {
        this.country = country;
    }

    public void changeCity(String city) {
        this.city = city;
    }

    public void changeLine1(String line1) {
        this.line1 = line1;
    }

    public void changePostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void changeVerified(Boolean verified) {
        this.verified = verified;
    }

    public Customer getCustomer() {
        return customer;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public Boolean getVerified() {
        return verified;
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

    public UUID getAddressId() {
        return addressId;
    }
}
