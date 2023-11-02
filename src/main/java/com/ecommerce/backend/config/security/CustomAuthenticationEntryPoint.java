package com.ecommerce.backend.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        String exceptionMessage = authException.getMessage();
        String message;

        if (exceptionMessage.contains("Unable to read JSON value")) {
            message = "Invalid JWT token";
        }
        else if (exceptionMessage.contains("JWT expired")) {
            message = "JWT token expired";
        }
        else {
            switch (exceptionMessage) {
                case "Bad credentials": {
                    message = "Username or password is wrong";
                    break;
                }
                case "Full authentication is required to access this resource":
                    message = "You need authenticate first to access this api";
                    break;
                default:
                    message = "General error";
            }
        }

        System.out.println("Unauthorized error: " + authException.getMessage());

        response.resetBuffer();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("Content-Type", "application/json");
        response.getOutputStream().write(String.format(
                "{\"message\":\"%s\",\"detail\":%s}",
                message,
                new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(new AuthExceptionInfo(authException))).getBytes("UTF-8"));
        response.flushBuffer();
    }

    @Getter
    @Setter
    private class AuthExceptionInfo {
        private String message;
        private Throwable[] suppressed;
        private String localizedMessage;

        public AuthExceptionInfo(AuthenticationException authException) {
            this.message = authException.getMessage();
            this.suppressed = authException.getSuppressed();
            this.localizedMessage = authException.getLocalizedMessage();
        }
    }
}
