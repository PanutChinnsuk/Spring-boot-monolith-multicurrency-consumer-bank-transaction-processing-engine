package com.project.banksystem.api.restcontroller.twofactor;


import com.project.banksystem.api.request.wrapper.twofactor.TwoFactorRequest;
import com.project.banksystem.api.restcontroller.RestControllerResponder;
import com.project.banksystem.application.backend.verification.internal.body.TwoFactorResponseBody;
import com.project.banksystem.application.orchestrator.twofactor.TwoFactorRequestOrchestrator;
import com.project.banksystem.core.response.DataResult;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAllRoles;
import com.project.banksystem.infrastructure.http.request.HttpServletRequestIpResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/two-factor/request")
@AllowedAllRoles
public class PublicTwoFactorRequestController {
    private final TwoFactorRequestOrchestrator twoFactorRequestOrchestrator;

    public PublicTwoFactorRequestController(TwoFactorRequestOrchestrator twoFactorRequestOrchestrator) {
        this.twoFactorRequestOrchestrator = twoFactorRequestOrchestrator;
    }


    @PostMapping("/email")
    public ResponseEntity<DataResult<TwoFactorResponseBody>>
    requestTwoFactorCode(@RequestBody @Valid TwoFactorRequest r,
                         HttpServletRequest httpServletRequest) {
        return RestControllerResponder
                .successResponse(twoFactorRequestOrchestrator.requestTwoFactorCode(
                        HttpServletRequestIpResolver.getDeviceIpFromRequest(httpServletRequest),
                        r.getTwoFactorPurpose()
                ));
    }

    @PostMapping("/sms")
    public ResponseEntity<DataResult<TwoFactorResponseBody>>
    requestOtpCode(@RequestBody @Valid TwoFactorRequest r,
                         HttpServletRequest httpServletRequest) {
        return RestControllerResponder
                .successResponse(twoFactorRequestOrchestrator.requestOtpCode(
                        HttpServletRequestIpResolver.getDeviceIpFromRequest(httpServletRequest),
                        r.getTwoFactorPurpose()
                ));
    }




}
