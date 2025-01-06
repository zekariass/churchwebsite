package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.User;
import com.churchwebsite.churchwebsite.repositories.UserRepository;
import com.churchwebsite.churchwebsite.utils.CustomUserDetails;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User Not Found!")
        );

//        if(!user.isActive()){
//            throw new DisabledException("User is inactive");
//        }
//
//        if(user.isBlocked()){
//            throw new LockedException("User is Blocked");
//        }

        return new CustomUserDetails(user);
    }

    public User loadUserByEmail(@Email(message = "Invalid Email.") String email) {
        return userRepository.findByEmail(email);
    }
}
