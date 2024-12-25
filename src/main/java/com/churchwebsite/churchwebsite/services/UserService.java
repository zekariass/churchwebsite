package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Role;
import com.churchwebsite.churchwebsite.entities.User;
import com.churchwebsite.churchwebsite.entities.UserProfile;
import com.churchwebsite.churchwebsite.repositories.UserProfileRepository;
import com.churchwebsite.churchwebsite.repositories.UserRepository;
import com.churchwebsite.churchwebsite.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Set<Role> getRolesByUserId(Integer userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        return user.getRoles();
    }

    public User addNewUserWithProfile(User user, UserProfile userProfile){
        UserProfile savedUserProfile = userProfileRepository.save(userProfile);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setBlocked(true);
        user.setUserProfileId(savedUserProfile);
        User savedUser = userRepository.save(user);


        return savedUser;

    }


    public CustomUserDetails getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated and not anonymous
        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return (CustomUserDetails) authentication.getPrincipal();
        }

        // Return null or throw an exception if the user is anonymous or not authenticated
        return null;
    }

    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }
}
