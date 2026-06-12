package com.project.banksystem.application.backend.customer.impl;

import com.project.banksystem.application.backend.customer.validator.CustomerStatusValidator;
import com.project.banksystem.application.backend.customer.query.CustomerFinder;
import com.project.banksystem.application.backend.customer.validator.CustomerValidator;
import com.project.banksystem.core.enums.customer.CustomerStatus;
import com.project.banksystem.infrastructure.model.Customer;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerTerminationService {
    private final CustomerFinder customerFinder;
    private final CustomerValidator customerValidator;

    public CustomerTerminationService(CustomerFinder customerFinder,
                                      CustomerValidator customerValidator) {
        this.customerFinder = customerFinder;
        this.customerValidator = customerValidator;
    }

    public Customer terminateCustomer(final UUID customerId) {
        Customer customer = customerFinder.findById(customerId);

        CustomerStatusValidator.assertTerminate(customer);

        customer.changeCustomerStatus(CustomerStatus.TERMINATED);
        return customer;
    }

    public Customer intentionalUnterminateCustomer(final UUID customerId) {
        Customer customer = customerFinder.findById(customerId);

        CustomerStatusValidator.assertUnterminate(customer);

        customer.changeCustomerStatus(CustomerStatus.PENDING);
        return customer;
    }
}
