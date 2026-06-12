package com.project.banksystem.infrastructure.security.config;

import com.project.banksystem.application.backend.verification.interceptor.TwoFactorRequestValidationInterceptor;
import com.project.banksystem.infrastructure.security.ratelimiting.interceptor.AuthenticationRateLimitInterceptor;
import com.project.banksystem.infrastructure.security.ratelimiting.interceptor.TwoFactorRateLimitInterceptor;
import com.project.banksystem.infrastructure.security.ratelimiting.interceptor.RequestRateLimitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityInterceptorConfig implements WebMvcConfigurer {
    private final AuthenticationRateLimitInterceptor authenticationRateLimitInterceptor;
    private final RequestRateLimitInterceptor requestRateLimitInterceptor;
    private final TwoFactorRateLimitInterceptor twoFactorRateLimitInterceptor;
    private final TwoFactorRequestValidationInterceptor twoFactorRequestValidationInterceptor;

    public SecurityInterceptorConfig(AuthenticationRateLimitInterceptor authenticationRateLimitInterceptor,
                                     RequestRateLimitInterceptor requestRateLimitInterceptor,
                                     TwoFactorRateLimitInterceptor twoFactorRateLimitInterceptor,
                                     TwoFactorRequestValidationInterceptor twoFactorRequestValidationInterceptor) {
        this.authenticationRateLimitInterceptor = authenticationRateLimitInterceptor;
        this.requestRateLimitInterceptor = requestRateLimitInterceptor;
        this.twoFactorRateLimitInterceptor = twoFactorRateLimitInterceptor;
        this.twoFactorRequestValidationInterceptor = twoFactorRequestValidationInterceptor;
    }

    private final String LOGIN = "/api/auth/**";
    private final String REFRESH = "/api/public/refresh/**";
    private final String LOGIN_2STEP = "/api/auth/verify";
    private final String CONTACT_2STEP = "/api/public/contact/verify/**";

    private final String TWO_FACTOR_REQUEST = "/api/public/two-factor/request/**";
    private final String PAYMENT_SESSION_REQUEST = "/api/public/payment-session/request/**";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationRateLimitInterceptor)
                .addPathPatterns(LOGIN, REFRESH)
                .excludePathPatterns(LOGIN_2STEP);

        registry.addInterceptor(requestRateLimitInterceptor)
                .addPathPatterns(PAYMENT_SESSION_REQUEST, TWO_FACTOR_REQUEST);

        registry.addInterceptor(twoFactorRequestValidationInterceptor)
                .addPathPatterns(LOGIN_2STEP, CONTACT_2STEP);

        registry.addInterceptor(twoFactorRateLimitInterceptor)
                .addPathPatterns(LOGIN_2STEP, CONTACT_2STEP);





    }
}
