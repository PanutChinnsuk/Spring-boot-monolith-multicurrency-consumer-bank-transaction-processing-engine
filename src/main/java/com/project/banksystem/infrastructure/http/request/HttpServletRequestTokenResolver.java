package com.project.banksystem.infrastructure.http.request;


import jakarta.servlet.http.HttpServletRequest;

public final class HttpServletRequestTokenResolver {
    private HttpServletRequestTokenResolver() {

    }

    private static final String TOKEN_PREFIXED = "Bearer ";

    public static String getTokenFromHeader(final HttpServletRequest httpServletRequest,
                                            final String header) {

        String head = httpServletRequest.getHeader(header);
        if (head == null || !head.startsWith(TOKEN_PREFIXED)) {
            return null;
        }
        String token = head.substring(TOKEN_PREFIXED.length());
        if (token == null || token.isBlank()) {
            return null;
        }


        return token;
    }
}
