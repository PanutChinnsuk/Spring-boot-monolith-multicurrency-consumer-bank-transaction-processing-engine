package com.project.banksystem.infrastructure.http.response;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public final class HttpServletResponseWriter {
    private HttpServletResponseWriter() {

    }


    public static void response(final HttpServletResponse httpServletResponse,
                                final String message,
                                final int errorCode) throws IOException {
        String safeMessage = message.replace("\"", "\\\"");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(errorCode);
        httpServletResponse.getWriter().write(
                "{\"error\":\"" + message + "\"}"
        );
        httpServletResponse.getWriter().flush();

    }
}
