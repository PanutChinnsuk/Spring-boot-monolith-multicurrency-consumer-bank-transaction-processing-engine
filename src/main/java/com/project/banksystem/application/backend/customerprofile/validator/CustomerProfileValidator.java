package com.project.banksystem.application.backend.customerprofile.validator;

import com.project.banksystem.api.request.dto.user.body.UserVerifyInformationData;
import com.project.banksystem.application.backend.customer.validator.CustomerValidator;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;

import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.model.CustomerProfile;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.repository.CustomerProfileRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class CustomerProfileValidator {
    private final CustomerProfileRepository customerProfileRepository;
    private final CustomerValidator customerValidator;

    public CustomerProfileValidator(CustomerProfileRepository customerProfileRepository,
                                    CustomerValidator customerValidator) {
        this.customerProfileRepository = customerProfileRepository;
        this.customerValidator = customerValidator;
    }


    public void validateNationalIdAvailable(final String nationalId) {
        if (customerProfileRepository.existsByNationalId(nationalId)) {
            throw new BusinessException("National id already exist.");
        }
    }

    public void validateCanHaveProfile(final Customer customer) {
        if (customer.getCustomerProfile() != null) {
            throw new BusinessException("Customer already have profile.");
        }
    }

    public void validateCanUpdate(final CustomerProfile customerProfile) {
        customerValidator.validateCustomerOperational(customerProfile.getCustomer());
    }

    public void validateMatchedCustomerProfile(final User user,
                                               final UserVerifyInformationData userVerifyInformationData) {
        CustomerProfile customerProfile = user.getCustomer().getCustomerProfile();

        String nationalId = userVerifyInformationData.getNationalId();
        LocalDate dateOfBirth = userVerifyInformationData.getDateOfBirth();

        if (!customerProfile.getNationalId().equals(nationalId)) {
            throw new BusinessException("National id not matched.");
        }
        if (!customerProfile.getDateOfBirth().isEqual(dateOfBirth)) {
            throw new BusinessException("Date of birth not matched.");
        }
    }






}
