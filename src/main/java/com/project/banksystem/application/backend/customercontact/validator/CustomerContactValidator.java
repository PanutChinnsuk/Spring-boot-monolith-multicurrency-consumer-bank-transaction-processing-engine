package com.project.banksystem.application.backend.customercontact.validator;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.application.backend.customer.validator.CustomerValidator;
import com.project.banksystem.api.request.dto.customer.CustomerContactCreateDTO;
import com.project.banksystem.application.backend.customercontact.validator.body.CustomerContactConflict;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.core.enums.customer.ContactType;
import com.project.banksystem.infrastructure.exception.ex.SystemMisusedException;
import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.model.CustomerContact;
import com.project.banksystem.infrastructure.model.EmailObject;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.TwoFactorCode;
import com.project.banksystem.infrastructure.model.sharedmodel.verification.VerificationCode;
import com.project.banksystem.infrastructure.repository.CustomerContactRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerContactValidator {
    private final CustomerContactRepository customerContactRepository;
    private final CustomerValidator customerValidator;

    public CustomerContactValidator(CustomerContactRepository customerContactRepository,
                                    CustomerValidator customerValidator) {
        this.customerContactRepository = customerContactRepository;
        this.customerValidator = customerValidator;
    }


    public void validateCanCreate(final Customer customer, final CustomerContactCreateDTO customerContactCreateDTO) {
        ContactType contactType = customerContactCreateDTO.getContactType();
        String value = customerContactCreateDTO.getValue();

        CustomerContactConflict result = customerContactRepository.check(customer.getCustomerId(),
                value, contactType);

        if (result.getExistsContactType()) {
            throw new BusinessException("Customer already have contact with this type.");
        }

        if (result.getExistsValue()) {
            throw new BusinessException("Customer already have contact with this value.");
        }

        if (result.getExistsValueAndContactType()) {
            throw new BusinessException("Customer already have this contact.");
        }
    }

    public void validateCanAddPhone(final CustomerContactCreateDTO customerContactCreateDTO) {
        if (customerContactCreateDTO.getCountryDialingPrefix() == null) {
            throw new BusinessException("Country dialing prefix is needed to add phone " +
                    "number.");
        }
    }

    public void validateCanUpdate(final CustomerContact customerContact) {
        customerValidator.validateCustomerOperational(customerContact.getCustomer());
    }

    public void validateCanVerify(final CustomerContact customerContact,
                                  final ContactType specificContactType) {
        if (customerContact.getVerified().equals(Boolean.TRUE)) {
            throw new BusinessException("Customer contact is already verified.");
        }
        if (specificContactType != null) {
            if (customerContact.getContactType() != specificContactType) {
                throw new BusinessException("Mismatched customer contact type of verification.");
            }
        }
    }

    public void validateCanUpdateValue(final String value) {
        if (customerContactRepository.existsByValue(value)) {
            throw new BusinessException("Value already exist.");
        }
    }



    public void validateContactBelongToCurrentCustomer(final CustomerContact customerContact) {
        UUID customerId = customerContact.getCustomer().getCustomerId();
        UUID currentId = CurrentUser.getCurrentUserCustomerId();

        if (!customerId.equals(currentId)) {
            throw new BusinessException("Contact does not belong to customer.");
        }
    }

}
