package com.project.banksystem.application.backend.customerprofile;

import com.project.banksystem.api.response.read.adminres.customer.body.CustomerProfileAdminSummary;
import com.project.banksystem.infrastructure.model.CustomerProfile;

public final class CustomerProfileResponseMapper {
    private CustomerProfileResponseMapper() {

    }


    public static final CustomerProfileAdminSummary
    toCustomerProfileAdminSummary(final CustomerProfile customerProfile) {
        return CustomerProfileAdminSummary.builder()
                .customerProfileId(customerProfile.getId())
                .firstName(customerProfile.getFirstName())
                .lastName(customerProfile.getLastName())
                .dateOfBirth(customerProfile.getDateOfBirth())
                .nationality(customerProfile.getNationality())
                .nationalId(customerProfile.getNationalId())
                .build();
    }


}
