package com.project.banksystem.application.backend.customeraddress.impl;

import com.project.banksystem.application.backend.customeraddress.CustomerAddressManager;
import com.project.banksystem.application.backend.customeraddress.query.CustomerAddressFinder;
import com.project.banksystem.application.backend.customeraddress.validator.CustomerAddressValidator;
import com.project.banksystem.api.request.dto.customer.CustomerAddressUpdateDTO;
import com.project.banksystem.infrastructure.model.CustomerAddress;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class CustomerAddressUpdateService {
    private final CustomerAddressValidator customerAddressValidator;
    private final CustomerAddressFinder customerAddressFinder;


    public CustomerAddressUpdateService(CustomerAddressValidator customerAddressValidator,
                                        CustomerAddressFinder customerAddressFinder) {
        this.customerAddressValidator = customerAddressValidator;
        this.customerAddressFinder = customerAddressFinder;
    }

    private CustomerAddress getUpdatable(final UUID customerAddressId) {
        CustomerAddress customerAddress = customerAddressFinder.findById(customerAddressId);
        customerAddressValidator.validateCanUpdate(customerAddress);

        return customerAddress;
    }


    public CustomerAddress updateCustomerAddress(final CustomerAddressUpdateDTO customerAddressUpdateDTO) {
        UUID customerAddressId = customerAddressUpdateDTO.getCustomerAddressId();

        CustomerAddress customerAddress = getUpdatable(customerAddressId);

        String country = customerAddressUpdateDTO.getCountry();
        String city = customerAddressUpdateDTO.getCity();
        String line1 = customerAddressUpdateDTO.getLine1();
        String postalCode = customerAddressUpdateDTO.getPostalCode();

        if (country != null) {customerAddress.changeCountry(country);}
        if (city != null) {customerAddress.changeCity(city);}
        if (line1 != null) {customerAddress.changeLine1(line1);}
        if (postalCode != null) {customerAddress.changePostalCode(postalCode);}
        customerAddress.changeVerified(false);

        return customerAddress;

    }


    public CustomerAddress verifyCustomerAddress(final UUID customerAddressId) {
        CustomerAddress customerAddress = getUpdatable(customerAddressId);

        customerAddressValidator.validateCanVerify(customerAddress);

        customerAddress.changeVerified(true);

        return customerAddress;
    }
}
