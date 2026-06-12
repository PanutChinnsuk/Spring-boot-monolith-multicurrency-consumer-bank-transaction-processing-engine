package com.project.banksystem.application.backend.customer.validator;

import com.project.banksystem.core.enums.customer.CustomerStatus;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.model.Customer;

public final class CustomerStatusValidator {
    private CustomerStatusValidator() {

    }

    public static void assertTerminate(final Customer customer) {
        if (customer.getCustomerStatus() == CustomerStatus.TERMINATED) {
            throw new BusinessException("Customer is already terminated.");
        }
    }

    public static void assertUnterminate(final Customer customer) {
        if (customer.getCustomerStatus() != CustomerStatus.TERMINATED) {
            throw new BusinessException("Customer is not terminated.");
        }
    }

    public static void assertCustomerStatus(final CustomerStatus inputStatus, Customer customer) {
        if (inputStatus == CustomerStatus.TERMINATED) {
            throw new BusinessException("Terminated status is prohibited.");
        }
        if (customer.getCustomerStatus() == inputStatus) {
            throw new BusinessException("Customer already have this customer status.");
        }
    }
}
