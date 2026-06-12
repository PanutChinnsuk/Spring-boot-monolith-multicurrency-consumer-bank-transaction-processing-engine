package com.project.banksystem.application.backend.customer.query;

import com.project.banksystem.application.backend.customer.validator.CustomerValidator;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerFinder {
    private final CustomerRepository customerRepository;
    private final CustomerValidator customerValidator;

    private CustomerFinder(CustomerRepository customerRepository,
                           CustomerValidator customerValidator) {
        this.customerRepository = customerRepository;
        this.customerValidator = customerValidator;
    }

    private Customer id(final UUID customerId) {
        return customerRepository.findFirstByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found."));
    }



    public Customer findById(final UUID customerId) {
        return id(customerId);
    }

    public Customer findNonTerminatedById(final UUID customerId) {
        Customer customer = id(customerId);

        customerValidator.validateCustomerNotTerminated(customer);

        return customer;
    }

    public Customer findOperationalById(final UUID customerId) {
        Customer customer = id(customerId);

        customerValidator.validateCustomerOperational(customer);

        return customer;
    }

}
