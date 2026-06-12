package com.project.banksystem.application.backend.customer.impl;

import com.project.banksystem.application.backend.customer.validator.CustomerStatusValidator;
import com.project.banksystem.application.backend.customer.query.CustomerFinder;
import com.project.banksystem.application.backend.customer.validator.CustomerValidator;
import com.project.banksystem.core.enums.customer.CustomerStatus;
import com.project.banksystem.infrastructure.model.Customer;
import org.springframework.stereotype.Component;


import java.util.UUID;

@Component
public class CustomerUpdateService {
    private final CustomerFinder customerFinder;
    private final CustomerValidator customerValidator;

    public CustomerUpdateService(CustomerFinder customerFinder,
                                 CustomerValidator customerValidator) {
        this.customerFinder = customerFinder;
        this.customerValidator = customerValidator;
    }


    public Customer updateCustomerStatus(final UUID customerId,
                                                                  final CustomerStatus customerStatus) {
        Customer customer = customerFinder.findNonTerminatedById(customerId);

        CustomerStatusValidator.assertCustomerStatus(customerStatus, customer);

        customer.changeCustomerStatus(customerStatus);
        return customer;
    }


    public Customer updateCustomerRiskRating(final UUID customerId,
                                                                  final Integer riskRating) {
        Customer customer = customerFinder.findNonTerminatedById(customerId);

        customerValidator.validateCanUpdateRiskRating(customer, riskRating);

        customer.changeRiskRating(riskRating);
        return customer;
    }
}
