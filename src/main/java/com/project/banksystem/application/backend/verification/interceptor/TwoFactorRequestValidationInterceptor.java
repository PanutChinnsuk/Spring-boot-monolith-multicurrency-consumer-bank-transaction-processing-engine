package com.project.banksystem.application.backend.verification.interceptor;

import com.project.banksystem.infrastructure.http.request.HttpServletRequestTokenResolver;
import com.project.banksystem.infrastructure.http.response.HttpServletResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TwoFactorRequestValidationInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String header = HttpServletRequestTokenResolver
                .getTokenFromHeader(request, "TwoFactorToken");
        if (header == null) {
            HttpServletResponseWriter.response(response,
                    "Invalid two factor token.",
                    HttpStatus.FORBIDDEN.value());
            return false;
        }

        return true;
    }
}
