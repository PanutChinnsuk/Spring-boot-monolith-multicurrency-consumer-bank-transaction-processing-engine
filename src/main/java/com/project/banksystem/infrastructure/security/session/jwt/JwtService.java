package com.project.banksystem.infrastructure.security.session.jwt;

import com.project.banksystem.infrastructure.model.*;
import com.project.banksystem.infrastructure.security.session.jwt.builder.JwtTokenBuilder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private final JwtTokenBuilder jwtTokenBuilder;

    public JwtService(JwtTokenBuilder jwtTokenBuilder) {
        this.jwtTokenBuilder = jwtTokenBuilder;
    }

    public String createAccessSessionJwtToken(final User user,
                                              final AccessSession accessSession,
                                              final Long version,
                                              final Instant expiry) {
        Set<String> roles = user.getUserRoles().stream()
                .map(ur -> ur.getRole())
                .map(r -> r.getUserAccess().name())
                .collect(Collectors.toSet());

        return jwtTokenBuilder.generateAccessToken(user,
                user.getCustomer(),
                roles,
                accessSession.getAccessSessionId(),
                expiry,
                version);
    }



}
