package com.project.banksystem.api.restcontroller.auth;


import com.project.banksystem.api.request.wrapper.auth.LoginRequest;
import com.project.banksystem.api.request.wrapper.auth.LoginTwoFactorVerificationRequest;
import com.project.banksystem.application.backend.auth.body.AuthResponseBody;
import com.project.banksystem.application.backend.verification.internal.body.TwoFactorDataResultFromRequest;
import com.project.banksystem.application.backend.verification.internal.InternalTwoFactorService;
import com.project.banksystem.application.orchestrator.auth.AuthenticationOrchestrator;
import com.project.banksystem.core.enums.jwt.TokenType;
import com.project.banksystem.core.response.DataResult;
import com.project.banksystem.infrastructure.http.request.HttpServletRequestIpResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationOrchestrator authenticationOrchestrator;
    private final InternalTwoFactorService internalTwoFactorService;

    public AuthenticationController(AuthenticationOrchestrator authenticationOrchestrator,
                                           InternalTwoFactorService internalTwoFactorService) {
        this.authenticationOrchestrator = authenticationOrchestrator;
        this.internalTwoFactorService = internalTwoFactorService;
    }




    @PostMapping("/login")
    public ResponseEntity<DataResult<AuthResponseBody>> login(@RequestBody @Valid LoginRequest r,
                                                              HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(authenticationOrchestrator.login(
                r.getUserLoginDTO(),
                HttpServletRequestIpResolver.getDeviceIpFromRequest(httpServletRequest)
        ));
    }



    @PostMapping("/verify")
    public ResponseEntity<DataResult<AuthResponseBody>> verifyLogin2FA(@RequestBody @Valid LoginTwoFactorVerificationRequest r,
                                                                       HttpServletRequest httpServletRequest) {

        TwoFactorDataResultFromRequest result =
                internalTwoFactorService.extractDataForVerify(httpServletRequest,
                        TokenType.TWO_FACTOR_LOGIN);


        return ResponseEntity.ok(authenticationOrchestrator.verify2FA(
                result.codeId(),
                r.getVerificationCode(),
                result.userIdFromToken(),
                result.deviceIp()

        ));
    }
}
