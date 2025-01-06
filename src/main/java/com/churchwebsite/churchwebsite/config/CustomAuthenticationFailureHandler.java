package com.churchwebsite.churchwebsite.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String failureUrl;
        if (exception instanceof DisabledException) {
            failureUrl = "/users/login?error=inactive";
        } else if (exception instanceof LockedException) {
            failureUrl = "/users/login?error=blocked";
        } else {
            failureUrl = "/users/login?error=true";
        }

        // Set the failure URL
        setDefaultFailureUrl(failureUrl);
        response.sendRedirect(failureUrl);
//        super.onAuthenticationFailure(request, response, exception);
    }
}
