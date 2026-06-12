package com.project.banksystem.api.restcontroller.refreshtoken;

import com.project.banksystem.api.restcontroller.RestControllerResponder;
import com.project.banksystem.application.backend.refreshtoken.body.RefreshResponseBody;
import com.project.banksystem.application.orchestrator.refreshtoken.RefreshTokenOrchestrator;
import com.project.banksystem.core.enums.jwt.TokenType;
import com.project.banksystem.core.response.DataResult;
import com.project.banksystem.infrastructure.annotation.customvalidator.anno.authorityvalidation.AllowedAllRoles;
import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.http.request.HttpServletRequestIpResolver;
import com.project.banksystem.infrastructure.http.request.HttpServletRequestTokenResolver;
import com.project.banksystem.infrastructure.security.session.jwt.validator.JwtValidator;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/public/refresh")
@AllowedAllRoles
public class RefreshTokenController {
    private final RefreshTokenOrchestrator refreshTokenOrchestrator;
    private final JwtValidator jwtValidator;

    public RefreshTokenController(RefreshTokenOrchestrator refreshTokenOrchestrator,
                                  JwtValidator jwtValidator) {
        this.refreshTokenOrchestrator = refreshTokenOrchestrator;
        this.jwtValidator = jwtValidator;
    }


    @GetMapping("/access-session")
    public ResponseEntity<DataResult<RefreshResponseBody>> refreshAccessSession(HttpServletRequest httpServletRequest) {
        String token =  HttpServletRequestTokenResolver.getTokenFromHeader(
                httpServletRequest, "RefreshToken"
        );

        if (token == null) {
            throw new Forbidden("Invalid refresh token header.");
        }

        Claims claims = jwtValidator.getValidTokenBodyWithMatchedTokenType(
                token, TokenType.REFRESH
        );


        String deviceIp = HttpServletRequestIpResolver.getDeviceIpFromRequest(httpServletRequest);
        UUID userIdFromToken = UUID.fromString(claims.getSubject());
        UUID refreshTokenIdFromToken = UUID.fromString(claims.getId());



        return RestControllerResponder.successResponse(refreshTokenOrchestrator.refreshAccessSession(refreshTokenIdFromToken,
                userIdFromToken, deviceIp));
    }
}
