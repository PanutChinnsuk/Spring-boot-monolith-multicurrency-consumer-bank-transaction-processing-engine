package com.project.banksystem.application.backend.useractivation.impl;

import com.project.banksystem.api.request.dto.user.body.UserCredentialCreateData;
import com.project.banksystem.api.request.dto.user.body.UserVerifyInformationData;
import com.project.banksystem.application.backend.credential.internal.InternalCredentialCreateService;
import com.project.banksystem.application.backend.credential.validator.CredentialValidator;
import com.project.banksystem.application.backend.customerprofile.validator.CustomerProfileValidator;
import com.project.banksystem.application.backend.user.validator.UserStatusValidator;
import com.project.banksystem.application.backend.useractivation.query.ActivationCodeFinder;
import com.project.banksystem.application.backend.useractivation.validator.ActivationCodeValidator;
import com.project.banksystem.application.backend.user.query.UserFinder;
import com.project.banksystem.application.backend.userrole.impl.UserRoleAddService;
import com.project.banksystem.api.request.dto.credential.CredentialInternalCreateDTO;
import com.project.banksystem.api.request.dto.user.UserActivationDTO;
import com.project.banksystem.core.enums.user.UserStatus;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.repository.ActivationCodeRepository;
import org.springframework.stereotype.Service;

@Service
public class UserActivationService {
    private final ActivationCodeValidator activationCodeValidator;
    private final ActivationCodeRepository activationCodeRepository;
    private final UserRoleAddService userRoleAddService;
    private final UserFinder userFinder;
    private final InternalCredentialCreateService internalCredentialCreateService;
    private final CredentialValidator credentialValidator;
    private final CustomerProfileValidator customerProfileValidator;
    private final ActivationCodeFinder activationCodeFinder;


    public UserActivationService(ActivationCodeValidator activationCodeValidator,
                                 ActivationCodeRepository activationCodeRepository,
                                 UserRoleAddService userRoleAddService,
                                 UserFinder userFinder,
                                 InternalCredentialCreateService internalCredentialCreateService,
                                 CredentialValidator credentialValidator,
                                 CustomerProfileValidator customerProfileValidator,
                                 ActivationCodeFinder activationCodeFinder) {
        this.activationCodeValidator = activationCodeValidator;
        this.activationCodeRepository = activationCodeRepository;
        this.userRoleAddService = userRoleAddService;
        this.userFinder = userFinder;
        this.internalCredentialCreateService = internalCredentialCreateService;
        this.credentialValidator = credentialValidator;
        this.customerProfileValidator = customerProfileValidator;
        this.activationCodeFinder = activationCodeFinder;
    }




    public void verifyUser(final UserActivationDTO userActivationDTO) {
        String activationCode = userActivationDTO.getActivationCode();
        UserVerifyInformationData userVerifyInformationData = userActivationDTO.getUserVerifyInformationData();
        UserCredentialCreateData userCredentialCreateData = userActivationDTO.getUserCredentialCreateData();

        String userPassword = userCredentialCreateData.getUserPassword();
        String userConfirmedPassword = userCredentialCreateData.getUserConfirmedPassword();
        String personalPin = userCredentialCreateData.getPersonalPin();
        String confirmedPersonalPin = userCredentialCreateData.getConfirmedPersonalPin();

        User user = userFinder.findOperationalByUserName(userVerifyInformationData.getUserName());

        UserStatusValidator.assertVerify(user);
        activationCodeValidator.validateNonRevoked(activationCodeFinder.findMatchedCode(user, activationCode));
        credentialValidator.validateCanCreatePassword(userPassword, userConfirmedPassword);
        credentialValidator.validateCanCreatePin(personalPin, confirmedPersonalPin);
        customerProfileValidator.validateMatchedCustomerProfile(user, userVerifyInformationData);

        user.changeUserStatus(UserStatus.ACTIVE);

        activationCodeRepository.revokedAllUserActivationCode(user.getUserId());

        internalCredentialCreateService.createCredential(CredentialInternalCreateDTO.builder()
                .user(user)
                .password(userPassword)
                .pin(personalPin)
                .build());

        userRoleAddService.addDefaultCustomerRole(user);
    }




}
