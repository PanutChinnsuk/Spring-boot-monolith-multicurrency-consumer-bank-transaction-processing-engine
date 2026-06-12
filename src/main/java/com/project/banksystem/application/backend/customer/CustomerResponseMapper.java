package com.project.banksystem.application.backend.customer;

import com.project.banksystem.api.response.read.adminres.customer.body.CustomerAdminSummary;
import com.project.banksystem.api.response.read.adminres.customer.CustomerAdminResponse;
import com.project.banksystem.application.backend.customeraddress.CustomerAddressResponseMapper;
import com.project.banksystem.application.backend.customercontact.CustomerContactResponseMapper;
import com.project.banksystem.application.backend.customerprofile.CustomerProfileResponseMapper;
import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.model.CustomerAddress;
import com.project.banksystem.infrastructure.model.CustomerContact;
import com.project.banksystem.infrastructure.model.CustomerProfile;

import java.util.Set;

public final class CustomerResponseMapper {
    private CustomerResponseMapper() {

    }

    public static final CustomerAdminSummary toCustomerAdminSummary(final Customer customer) {
        CustomerProfile customerProfile = customer.getCustomerProfile();

        return CustomerAdminSummary.builder()
                .customerId(customer.getCustomerId())
                .firstName(customerProfile.getFirstName())
                .lastName(customerProfile.getLastName())
                .customerStatus(customer.getCustomerStatus())
                .build();
    }




    public static final CustomerAdminResponse toAdminResponse(final Customer customer) {
        CustomerProfile customerProfile = customer.getCustomerProfile();
        Set<CustomerAddress> customerAddresses = customer.getCustomerAddresses();
        Set<CustomerContact> customerContacts = customer.getCustomerContacts();

        return CustomerAdminResponse.builder()
                .customerId(customer.getCustomerId())
                .customerType(customer.getCustomerType())
                .riskRating(customer.getRiskRating())
                .customerStatus(customer.getCustomerStatus())

                .customerProfileAdminSummary(customerProfile == null
                        ? null
                        : CustomerProfileResponseMapper.toCustomerProfileAdminSummary(customerProfile))

                .customerAddressAdminSummaries(customerAddresses == null
                        ? null
                        : CustomerAddressResponseMapper.addressesToCustomerAddressAdminSummaries(customerAddresses))

                .customerContactAdminSummaries(customerContacts == null
                        ? null
                        : CustomerContactResponseMapper.contactsToCustomerContactAdminSummaries(customerContacts))

                .createdAt(customer.getCreatedAt())
                .build();

    }


}
