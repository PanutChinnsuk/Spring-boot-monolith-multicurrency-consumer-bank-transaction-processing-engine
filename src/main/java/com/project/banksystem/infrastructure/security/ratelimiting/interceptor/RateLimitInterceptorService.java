package com.project.banksystem.infrastructure.security.ratelimiting.interceptor;

import com.project.banksystem.application.CurrentUser;
import com.project.banksystem.infrastructure.customerfuncinterface.ThrowingFunction;
import com.project.banksystem.infrastructure.http.request.HttpServletRequestIpResolver;
import com.project.banksystem.infrastructure.http.response.HttpServletResponseWriter;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.function.Function;

@Service
public class RateLimitInterceptorService {


    public String getPredefinedKey(HttpServletRequest request) {
        return CurrentUser.isAlreadyAuthenticated()
                ? CurrentUser.getCurrentUserId().toString()
                : HttpServletRequestIpResolver.getDeviceIpFromRequest(request);


    }

    public boolean handle(HttpServletRequest request,
                          HttpServletResponse response,
                          @Nullable String customKey,
                          String message,
                          ThrowingFunction<String, Boolean> distributed,
                          Function<String, Boolean> temporary) throws IOException {
        boolean exceeded;
        try {
            exceeded = distributed.apply(customKey);
        }catch (Exception e) {
            exceeded = temporary.apply(customKey);
        }

        if (exceeded) {
            HttpServletResponseWriter.response(
                    response,
                    message,
                    HttpStatus.TOO_MANY_REQUESTS.value()
            );
            return false;
        }

        return true;
    }

}
