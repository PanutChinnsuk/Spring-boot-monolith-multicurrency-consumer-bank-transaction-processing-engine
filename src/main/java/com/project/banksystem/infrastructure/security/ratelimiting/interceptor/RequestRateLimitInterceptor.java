package com.project.banksystem.infrastructure.security.ratelimiting.interceptor;

import com.project.banksystem.infrastructure.security.ratelimiting.memory.TemporaryRateLimitingService;
import com.project.banksystem.infrastructure.security.ratelimiting.redis.DistributedBucket4jRateLimitService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class RequestRateLimitInterceptor implements HandlerInterceptor {
    private final TemporaryRateLimitingService temporaryRateLimitingService;
    private final DistributedBucket4jRateLimitService distributedBucket4jRateLimitService;
    private final RateLimitInterceptorService rateLimitInterceptorService;

    public RequestRateLimitInterceptor(TemporaryRateLimitingService temporaryRateLimitingService,
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
        return rateLimitInterceptorService.handle(request, response,
                null, "Request rate limit exceeded.",
                distributedBucket4jRateLimitService::requestRateExceeded,
                temporaryRateLimitingService::requestRateExceeded);
    }
}
