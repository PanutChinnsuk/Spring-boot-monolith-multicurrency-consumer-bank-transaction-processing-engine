package com.project.banksystem.application.backend.customercontact;

import com.project.banksystem.infrastructure.model.CustomerContact;
import com.project.banksystem.infrastructure.repository.CustomerContactRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomerContactManager {
    private final CustomerContactRepository customerContactRepository;

    public CustomerContactManager(CustomerContactRepository customerContactRepository) {
        this.customerContactRepository = customerContactRepository;
    }

    public CustomerContact save(final CustomerContact customerContact) {
        return customerContactRepository.save(customerContact);
    }


}
