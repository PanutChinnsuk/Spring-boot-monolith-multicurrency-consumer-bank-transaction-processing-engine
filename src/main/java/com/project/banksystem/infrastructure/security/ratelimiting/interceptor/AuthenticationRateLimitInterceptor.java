package com.project.banksystem.infrastructure.security.ratelimiting.interceptor;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.infrastructure.http.request.HttpServletRequestIpResolver;
import com.project.banksystem.infrastructure.http.response.HttpServletResponseWriter;
import com.project.banksystem.infrastructure.security.ratelimiting.memory.TemporaryRateLimitingService;
import com.project.banksystem.infrastructure.security.ratelimiting.redis.DistributedBucket4jRateLimitService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class AuthenticationRateLimitInterceptor implements HandlerInterceptor {
    private final TemporaryRateLimitingService temporaryRateLimitingService;
    private final DistributedBucket4jRateLimitService distributedBucket4jRateLimitService;
    private final RateLimitInterceptorService rateLimitInterceptorService;

    public AuthenticationRateLimitInterceptor(TemporaryRateLimitingService temporaryRateLimitingService,
                                              DistributedBucket4jRateLimitService distributedBucket4jRateLimitService,
                                              RateLimitInterceptorService rateLimitInterceptorService) {
        this.temporaryRateLimitingService = temporaryRateLimitingService;
        this.distributedBucket4jRateLimitService = distributedBucket4jRateLimitService;
        this.rateLimitInterceptorService = rateLimitInterceptorService;
    }




    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {
        String key = rateLimitInterceptorService.getPredefinedKey(request);

        return rateLimitInterceptorService.handle(request, response,
                key, "Authentication rate limit exceeded.",
                distributedBucket4jRateLimitService::authRateExceeded,
                temporaryRateLimitingService::authRateExceeded);
    }
}
