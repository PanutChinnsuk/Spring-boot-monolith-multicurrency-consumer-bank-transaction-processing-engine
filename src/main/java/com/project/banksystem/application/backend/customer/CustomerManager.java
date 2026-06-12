package com.project.banksystem.application.backend.customer;

import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.repository.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomerManager {
    private CustomerRepository customerRepository;

    public CustomerManager(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public Customer save(final Customer customer) {
        return customerRepository.save(customer);
    }

}
