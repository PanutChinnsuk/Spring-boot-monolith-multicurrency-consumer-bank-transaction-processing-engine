package com.project.banksystem.application;

import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.exception.ex.Unauthorized;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.security.session.jwt.body.UserDetailsThreadBody;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentUser {
    private CurrentUser() {

    }

    private static final Authentication getValidAuthentication() {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
        || authentication.getPrincipal().equals("anonymousUser") ||
        authentication instanceof AnonymousAuthenticationToken) {
            throw new Unauthorized("Unauthorized user.");
        }

        return authentication;
    }

    private static final UserDetailsThreadBody getValidPrincipalFromAuthentication(final Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal == null || !(principal instanceof UserDetailsThreadBody)) {
            throw new Unauthorized("Invalid authenticated principal.");
        }

        return (UserDetailsThreadBody) principal;
    }


    public static final UUID getCurrentUserId() {
        return getValidPrincipalFromAuthentication(getValidAuthentication())
                .getUserId();
    }

    public static final String  getCurrentUserName() {
        return getValidPrincipalFromAuthentication(getValidAuthentication())
                .getUsername();
    }

    public static final UUID getCurrentUserCustomerId() {
        return getValidPrincipalFromAuthentication(getValidAuthentication())
                .getCustomerId();
    }

    public static final boolean isUserSystemAdministrator() {
        return getValidPrincipalFromAuthentication(getValidAuthentication())
                .isSystemAdministrator();
    }

    public static final boolean requiredAuditing() {
        return getValidPrincipalFromAuthentication(getValidAuthentication())
                .isForcedToAudit();
    }

    public static final boolean isAlreadyAuthenticated() {
        try {
            getValidPrincipalFromAuthentication(getValidAuthentication());
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
