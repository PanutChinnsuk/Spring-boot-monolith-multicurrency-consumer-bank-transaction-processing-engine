package com.project.banksystem.application.backend.customeraddress.validator;

import com.project.banksystem.application.backend.customer.validator.CustomerValidator;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.core.enums.customer.AddressType;
import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.model.CustomerAddress;
import com.project.banksystem.infrastructure.repository.CustomerAddressRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomerAddressValidator {
    private final CustomerValidator customerValidator;
    private final CustomerAddressRepository customerAddressRepository;

    public CustomerAddressValidator(CustomerValidator customerValidator,
                                    CustomerAddressRepository customerAddressRepository) {
        this.customerValidator = customerValidator;
        this.customerAddressRepository = customerAddressRepository;
    }

    public void validateCanUpdate(final CustomerAddress customerAddress) {
        customerValidator.validateCustomerOperational(customerAddress.getCustomer());
    }

    public void validateAvailableAddressType(final Customer customer, final AddressType addressType) {
        if (customerAddressRepository.existsByCustomerAndAddressType(customer, addressType)) {
            throw new BusinessException("Customer address already have this address type.");
        }
    }

    public void validateCanVerify(final CustomerAddress customerAddress) {
        if (customerAddress.getVerified().equals(Boolean.TRUE)) {
            throw new BusinessException("Customer address is already verified.");
        }
    }
}
