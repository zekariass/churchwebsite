package com.churchwebsite.churchwebsite.utils;

import com.churchwebsite.churchwebsite.entities.User;
import com.churchwebsite.churchwebsite.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<User> {

    @Autowired
    private UserService userService;

    @Override
    public Optional<User> getCurrentAuditor() {

        // Get the current authenticated user ID from Spring Security
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof CustomUserDetails){
            CustomUserDetails customUserDetails = (CustomUserDetails) principal;
            Integer userId = customUserDetails.getUserId();

            // Fetch the user entity by ID from the database
            Optional<User> user = userService.getUserById(userId);
            return user;
        }

        return Optional.empty();
    }
}
