package com.churchwebsite.churchwebsite.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");


        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        boolean hasMemberRole = authentication.getAuthorities().stream().anyMatch(
                r -> r.getAuthority().equals("ROLE_MEMBER")
        );

        boolean hasAdminRole = authentication.getAuthorities().stream().anyMatch(
                r -> r.getAuthority().equals("ROLE_ADMIN")
        );

        boolean hasClergyRole = authentication.getAuthorities().stream().anyMatch(
                r -> r.getAuthority().equals("ROLE_CLERGY")
        );

        if(hasMemberRole || hasAdminRole || hasClergyRole){
//            response.sendRedirect("/");
            // If there's a saved request, redirect to the original URL
            if (savedRequest != null && !savedRequest.getRedirectUrl().contains("/user/login")) {
                response.sendRedirect(savedRequest.getRedirectUrl());
            } else {
                // Otherwise, redirect to the default home page
                response.sendRedirect("/");
            }
        }

    }
}
