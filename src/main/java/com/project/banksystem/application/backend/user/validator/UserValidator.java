package com.project.banksystem.application.backend.user.validator;

import com.project.banksystem.application.backend.PasswordEncoderService;
import com.project.banksystem.application.backend.customer.validator.CustomerValidator;
import com.project.banksystem.api.request.dto.user.UserCreateDTO;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.core.enums.user.UserStatus;
import com.project.banksystem.infrastructure.exception.ex.Unauthorized;
import com.project.banksystem.infrastructure.model.Credential;
import com.project.banksystem.infrastructure.model.Customer;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.UUID;

@Component
public class UserValidator {
    private final CustomerValidator customerValidator;
    private final UserRepository userRepository;
    private final PasswordEncoderService passwordEncoderService;

    public UserValidator(CustomerValidator customerValidator,
                         UserRepository userRepository,
                         PasswordEncoderService passwordEncoderService) {
        this.customerValidator = customerValidator;
        this.userRepository = userRepository;
        this.passwordEncoderService = passwordEncoderService;
    }



    public void validateCanCreate(final UserCreateDTO userCreateDTO,
                                  final Customer customer) {
        if (userRepository.existsByUserName(userCreateDTO.getUserName())) {
            throw new BusinessException("User name already exist.");
        }

    }


    public void validateOperational(final User user) {
        UserStatus userStatus = user.getUserStatus();

        if (userStatus != UserStatus.ACTIVE) {
            throw new BusinessException("User is not operational.");
        }

        customerValidator.validateCustomerOperational(user.getCustomer());
    }


    public void validateUserNotTerminated(final User user) {
        if (user.getUserStatus() == UserStatus.TERMINATED) {
            throw new BusinessException("User is terminated.");
        }
        customerValidator.validateCustomerNotTerminated(user.getCustomer());
    }

    public void validateUserNotPending(final User user) {
        if (user.getUserStatus() == UserStatus.PENDING) {
            throw new BusinessException("User is on pending.");
        }
    }

    public void validateCanUpdateUserName(final String userName,
                                                 final UUID userId) {
        if (userRepository.existsByUserNameAndUserIdNot(userName, userId)) {
            throw new BusinessException("User name already exist.");
        }
    }

    public void validatePinMatched(final String pin,
                                   final Credential credential) {
        if (!passwordEncoderService.isMatched(pin, credential.getPinHash())) {
            throw new Unauthorized("Invalid pin.");
        }
    }


}
