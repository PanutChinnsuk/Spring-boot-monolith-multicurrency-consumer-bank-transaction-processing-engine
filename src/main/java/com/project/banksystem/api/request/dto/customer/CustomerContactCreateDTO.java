package com.project.banksystem.api.request.dto.customer;

import com.project.banksystem.core.enums.customer.ContactType;
import com.project.banksystem.core.enums.customer.CountryDialingPrefix;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CustomerContactCreateDTO {
    @NotNull
    private UUID customerId;

    @NotNull
    private ContactType contactType;

    @NotBlank
    private String value;

    @Nullable
    private CountryDialingPrefix countryDialingPrefix;


    public CustomerContactCreateDTO() {

    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setCountryDialingPrefix(@Nullable CountryDialingPrefix countryDialingPrefix) {
        this.countryDialingPrefix = countryDialingPrefix;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public String getValue() {
        return value;
    }

    @Nullable
    public CountryDialingPrefix getCountryDialingPrefix() {
        return countryDialingPrefix;
    }
}
