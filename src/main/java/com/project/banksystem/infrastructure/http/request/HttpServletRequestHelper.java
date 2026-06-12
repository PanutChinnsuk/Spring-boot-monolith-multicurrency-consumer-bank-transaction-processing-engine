package com.project.banksystem.infrastructure.http.request;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public final class HttpServletRequestHelper {
    private HttpServletRequestHelper() {

    }


    public static HttpServletRequest getCurrentThreadServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attr = (ServletRequestAttributes) requestAttributes;
        return attr.getRequest();
    }

}
