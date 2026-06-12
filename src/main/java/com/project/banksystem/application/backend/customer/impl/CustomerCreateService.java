package com.project.banksystem.application.backend.customer.impl;

import com.project.banksystem.application.backend.customer.CustomerManager;
import com.project.banksystem.api.request.dto.customer.CustomerCreateDTO;
import com.project.banksystem.infrastructure.model.Customer;
import org.springframework.stereotype.Service;


@Service
public class CustomerCreateService {
    private final CustomerManager customerManager;



    public CustomerCreateService(CustomerManager customerManager) {
        this.customerManager = customerManager;

    }



    public Customer addCustomer(final CustomerCreateDTO customerCreateDTO) {
            return customerManager.save(Customer.builder()
                    .customerType(customerCreateDTO.getCustomerType())
                    .build());
    }


}
