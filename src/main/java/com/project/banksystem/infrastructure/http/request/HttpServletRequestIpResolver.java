package com.project.banksystem.infrastructure.http.request;


import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public final class HttpServletRequestIpResolver {
    private HttpServletRequestIpResolver() {

    }


    public static String getDeviceIpFromRequest(final HttpServletRequest httpServletRequest) {
        String ip = httpServletRequest.getHeader("X-Forwarded-For");

        if (ip != null && !ip.isBlank() && !"unknown".equalsIgnoreCase(ip)) {
            return ip.split(",")[0].trim();
        }

        return httpServletRequest.getRemoteAddr();
    }



}
