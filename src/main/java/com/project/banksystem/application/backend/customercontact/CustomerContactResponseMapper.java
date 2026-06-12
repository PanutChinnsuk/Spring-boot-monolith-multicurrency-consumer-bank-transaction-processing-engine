package com.project.banksystem.application.backend.customercontact;

import com.project.banksystem.api.response.read.adminres.customer.body.CustomerContactAdminSummary;
import com.project.banksystem.infrastructure.model.CustomerContact;

import java.util.Set;
import java.util.stream.Collectors;

public final class CustomerContactResponseMapper {
    private CustomerContactResponseMapper() {

    }

    private static final CustomerContactAdminSummary toCustomerContactAdminSummary(final CustomerContact c) {
        return CustomerContactAdminSummary.builder()
                .contactId(c.getContactId())
                .contactType(c.getContactType())
                .value(c.getValue())
                .verified(c.getVerified())
                .build();
    }

    public static final Set<CustomerContactAdminSummary> contactsToCustomerContactAdminSummaries(final Set<CustomerContact> customerContacts) {
        return customerContacts.stream().map(c -> {
            return toCustomerContactAdminSummary(c);
        }).collect(Collectors.toSet());
    }


    public static final CustomerContactAdminSummary contactToCustomerContactAdminSummary(final CustomerContact customerContact) {
        return toCustomerContactAdminSummary(customerContact);
    }

}
