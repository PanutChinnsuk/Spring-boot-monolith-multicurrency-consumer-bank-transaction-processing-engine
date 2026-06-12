package com.project.banksystem.infrastructure.security.session.jwt.filter;

import com.project.banksystem.application.backend.accesssession.internal.InternalAccessSessionService;
import com.project.banksystem.core.enums.jwt.TokenType;
import com.project.banksystem.infrastructure.exception.ex.Forbidden;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.exception.ex.RateLimitExceededException;
import com.project.banksystem.infrastructure.exception.ex.Unauthorized;
import com.project.banksystem.infrastructure.http.request.HttpServletRequestIpResolver;
import com.project.banksystem.infrastructure.http.request.HttpServletRequestTokenResolver;
import com.project.banksystem.infrastructure.http.response.HttpServletResponseWriter;
import com.project.banksystem.infrastructure.security.cache.healthcheck.RedisHealthService;
import com.project.banksystem.infrastructure.security.ratelimiting.memory.TemporaryRateLimitingService;
import com.project.banksystem.infrastructure.security.ratelimiting.redis.DistributedBucket4jRateLimitService;
import com.project.banksystem.infrastructure.security.session.caching.caffeine.InMemoryCaffeineJwtTokenBlacklist;
import com.project.banksystem.infrastructure.security.session.jwt.body.UserDetailsThreadBody;
import com.project.banksystem.infrastructure.security.session.jwt.validator.JwtValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
public class JwtAccessSessionFilter extends OncePerRequestFilter {
    private final JwtValidator jwtValidator;
    private final InternalAccessSessionService internalAccessSessionService;
    private final InMemoryCaffeineJwtTokenBlacklist inMemoryCaffeineJwtTokenBlacklist;
    private final RedisHealthService redisHealthService;
    private final TemporaryRateLimitingService temporaryRateLimitingService;
    private final DistributedBucket4jRateLimitService distributedBucket4JRateLimitService;

    public JwtAccessSessionFilter(JwtValidator jwtValidator,
                                  InternalAccessSessionService internalAccessSessionService,
                                  InMemoryCaffeineJwtTokenBlacklist inMemoryCaffeineJwtTokenBlacklist,
                                  RedisHealthService redisHealthService,
                                  TemporaryRateLimitingService temporaryRateLimitingService,
                                  DistributedBucket4jRateLimitService distributedBucket4JRateLimitService) {
        this.jwtValidator = jwtValidator;
        this.internalAccessSessionService = internalAccessSessionService;
        this.inMemoryCaffeineJwtTokenBlacklist = inMemoryCaffeineJwtTokenBlacklist;
        this.redisHealthService = redisHealthService;
        this.temporaryRateLimitingService = temporaryRateLimitingService;
        this.distributedBucket4JRateLimitService = distributedBucket4JRateLimitService;

    }

    private void validateSession(Claims claims,
                                 HttpServletRequest request,
                                 String tokenForBlacklist) {
        String sessionUUID = claims.getId();
        String userIdFromJwtToken = claims.getSubject();
        String deviceIp = HttpServletRequestIpResolver.getDeviceIpFromRequest(request);
        Long version = claims.get("version", Long.class);

        internalAccessSessionService.checkForValidSession(
                sessionUUID,
                userIdFromJwtToken,
                deviceIp,
                version,
                tokenForBlacklist
        );
    }

    private void throwIfBlacklisted(String token) {
        if (inMemoryCaffeineJwtTokenBlacklist.isTokenBlacklisted(token)) {
            throw new Forbidden("Invalid access token.");
        }
    }

    private UserDetailsThreadBody buildPerThread(Claims claims) {
        UUID userId = UUID.fromString(claims.getSubject());
        UUID customerId = UUID.fromString(claims.get("customerId", String.class));
        Set<String> set = new HashSet<>(claims.get("roles", List.class));

        return UserDetailsThreadBody.builder()
                .userId(userId)
                .customerId(customerId)
                .roles(set)
                .build();
    }

    private void rateLimitBeforeSessionCheck() {
        boolean rateLimitExceeded;
        try {
            //AUTO USE CURRENT THREAD USER ID AS KEY -> PASSED NULL
            rateLimitExceeded = distributedBucket4JRateLimitService.apiRateExceeded(null);
        }catch (Exception e) {
            rateLimitExceeded = temporaryRateLimitingService.apiRateExceeded(null);
        }

        if (rateLimitExceeded) {
            throw new RateLimitExceededException("Rate limit exceeded.");
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = HttpServletRequestTokenResolver
                .getTokenFromHeader(request, "Authorization");
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            throwIfBlacklisted(token);
            Claims claims = jwtValidator.getValidTokenBodyWithMatchedTokenType(token,
                    TokenType.ACCESS);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails user = buildPerThread(claims);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            rateLimitBeforeSessionCheck();
            validateSession(claims, request, token);
        }catch (ExpiredJwtException e) {
            HttpServletResponseWriter.response(
                    response,
                    "Expired token.",
                    HttpStatus.UNAUTHORIZED.value());
            SecurityContextHolder.clearContext();
            logger.error("Jwt access session token expired exception. " + e.getMessage());
            return;
        }catch (JwtException | Unauthorized | Forbidden | NotFoundException e) {
            HttpServletResponseWriter.response(
                    response,
                    "Invalid token.",
                    HttpStatus.UNAUTHORIZED.value());
            SecurityContextHolder.clearContext();
            logger.error("Jwt access session security exception. " + e.getMessage());
            return;
        }catch (RateLimitExceededException e) {
            HttpServletResponseWriter.response(
                    response,
                    "Rate limit exceeded.",
                    HttpStatus.TOO_MANY_REQUESTS.value());
            SecurityContextHolder.clearContext();
            logger.error("Jwt access session rate limit exception. " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
