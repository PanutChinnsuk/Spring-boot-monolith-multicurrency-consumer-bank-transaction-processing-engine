package com.project.banksystem.api.restcontroller.customercontact;

import com.project.banksystem.api.request.wrapper.customercontact.EmailContactTwoFactorVerificationRequest;
import com.project.banksystem.api.restcontroller.RestControllerResponder;
import com.project.banksystem.application.backend.verification.internal.body.TwoFactorCodeVerifiedResult;
import com.project.banksystem.application.backend.verification.internal.body.TwoFactorDataResultFromRequest;
import com.project.banksystem.application.backend.verification.internal.InternalTwoFactorService;
import com.project.banksystem.application.orchestrator.customercontact.CustomerContactOrchestrator;
import com.project.banksystem.core.enums.jwt.TokenType;
import com.project.banksystem.core.response.Result;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAllRoles;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/contact/verify")
@AllowedAllRoles
public class PublicCustomerContactVerificationController {
    private final CustomerContactOrchestrator customerContactOrchestrator;
    private final InternalTwoFactorService internalTwoFactorService;

    public PublicCustomerContactVerificationController(CustomerContactOrchestrator customerContactOrchestrator,
                                                       InternalTwoFactorService internalTwoFactorService) {
        this.customerContactOrchestrator = customerContactOrchestrator;
        this.internalTwoFactorService = internalTwoFactorService;
    }

    private TwoFactorDataResultFromRequest getVerified(HttpServletRequest httpServletRequest,
                                                  TokenType tokenType) {
        return internalTwoFactorService.extractDataForVerify(httpServletRequest,
                        tokenType);
    }

    @PostMapping("/email")
    public ResponseEntity<Result> verifyEmailContact(@RequestBody @Valid EmailContactTwoFactorVerificationRequest r,
                                                     HttpServletRequest httpServletRequest) {
        TwoFactorDataResultFromRequest result =
                getVerified(httpServletRequest, TokenType.TWO_FACTOR_EMAIL_ADDRESS);

        return RestControllerResponder
                .successResponse(customerContactOrchestrator
                        .verifyEmailContact(r.getVerificationCode(),
                                r.getCustomerContactId(),
                                result.codeId(),
                                result.deviceIp(),
                                result.userIdFromToken()));
    }

    @PostMapping("/phone")
    public ResponseEntity<Result> verifyPhoneContact(@RequestBody @Valid EmailContactTwoFactorVerificationRequest r,
                                                     HttpServletRequest httpServletRequest) {
        TwoFactorDataResultFromRequest result =
                getVerified(httpServletRequest, TokenType.TWO_FACTOR_PHONE_NUMBER);

        return RestControllerResponder
                .successResponse(customerContactOrchestrator
                        .verifyPhoneContact(r.getVerificationCode(),
                                r.getCustomerContactId(),
                                result.codeId(),
                                result.deviceIp(),
                                result.userIdFromToken()));
    }
}
