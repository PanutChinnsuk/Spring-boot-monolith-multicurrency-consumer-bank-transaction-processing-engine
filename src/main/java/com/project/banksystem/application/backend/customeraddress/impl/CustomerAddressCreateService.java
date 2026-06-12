package com.project.banksystem.application.backend.customeraddress.impl;

import com.project.banksystem.application.backend.customer.query.CustomerFinder;
import com.project.banksystem.application.backend.customeraddress.CustomerAddressManager;
import com.project.banksystem.application.backend.customeraddress.validator.CustomerAddressValidator;
import com.project.banksystem.api.request.dto.customer.CustomerAddressCreateDTO;
import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.model.CustomerAddress;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerAddressCreateService {
    private final CustomerAddressManager customerAddressManager;
    private final CustomerAddressValidator customerAddressValidator;
    private final CustomerFinder customerFinder;

    public CustomerAddressCreateService(CustomerAddressManager customerAddressManager,
                                        CustomerAddressValidator customerAddressValidator,
                                        CustomerFinder customerFinder) {
        this.customerAddressManager = customerAddressManager;
        this.customerAddressValidator = customerAddressValidator;
        this.customerFinder = customerFinder;
    }



    public CustomerAddress createCustomerAddress(final CustomerAddressCreateDTO customerAddressCreateDTO) {
        UUID customerId = customerAddressCreateDTO.getCustomerId();

        Customer customer = customerFinder.findOperationalById(customerId);
        customerAddressValidator.validateAvailableAddressType(customer, customerAddressCreateDTO.getAddressType());

        return customerAddressManager.save(CustomerAddress.builder()
                .customer(customer)
                .addressType(customerAddressCreateDTO.getAddressType())
                .country(customerAddressCreateDTO.getCountry())
                .city(customerAddressCreateDTO.getCity())
                .line1(customerAddressCreateDTO.getLine1())
                .postalCode(customerAddressCreateDTO.getPostalCode())
                .build());

    }
}
