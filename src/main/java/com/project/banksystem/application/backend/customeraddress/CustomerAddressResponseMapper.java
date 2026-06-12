package com.project.banksystem.application.backend.customeraddress;

import com.project.banksystem.api.response.read.adminres.customer.body.CustomerAddressAdminSummary;
import com.project.banksystem.infrastructure.model.CustomerAddress;

import java.util.Set;
import java.util.stream.Collectors;

public final class CustomerAddressResponseMapper {
    private CustomerAddressResponseMapper() {

    }

    private static final CustomerAddressAdminSummary toCustomerAddressAdminSummary(final CustomerAddress a) {
        return CustomerAddressAdminSummary.builder()
                .addressId(a.getAddressId())
                .addressType(a.getAddressType())
                .country(a.getCountry())
                .city(a.getCity())
                .line1(a.getLine1())
                .postalCode(a.getPostalCode())
                .verified(a.getVerified())
                .build();
    }

    public static final Set<CustomerAddressAdminSummary> addressesToCustomerAddressAdminSummaries(final Set<CustomerAddress> customerAddresses) {
        return customerAddresses.stream().map(a -> {
            return toCustomerAddressAdminSummary(a);
        }).collect(Collectors.toSet());
    }

    public static final CustomerAddressAdminSummary addressToCustomerAddressAdminSummary(final CustomerAddress customerAddress) {
        return toCustomerAddressAdminSummary(customerAddress);
    }


}
