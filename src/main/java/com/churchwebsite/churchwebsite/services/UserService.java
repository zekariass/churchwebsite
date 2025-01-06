package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Role;
import com.churchwebsite.churchwebsite.entities.User;
import com.churchwebsite.churchwebsite.entities.UserProfile;
import com.churchwebsite.churchwebsite.repositories.RoleRepository;
import com.churchwebsite.churchwebsite.repositories.UserProfileRepository;
import com.churchwebsite.churchwebsite.repositories.UserRepository;
import com.churchwebsite.churchwebsite.utils.CustomUserDetails;
import com.churchwebsite.churchwebsite.utils.LocalFileStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Value("${local.file.user.profile-dir}")
    private String userProfilePicDir;

    @Autowired
    public UserService(UserRepository userRepository, UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public Set<Role> getRolesByUserId(Integer userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        return user.getRoles();
    }

    public User addNewUserWithProfile(User user, UserProfile userProfile, MultipartFile multipartFile) {

        UserProfile savedUserProfile = null;

        // Check if userProfile has data and save it
        if (userProfile != null) {
            if (!multipartFile.isEmpty()) {
                // If a file is uploaded, save it and set the profile photo path
                LocalFileStorageManager storageManager = new LocalFileStorageManager(userProfilePicDir);
                String filePath = storageManager.storeFile(multipartFile);
                userProfile.setProfilePhoto(filePath);
            }

            // Save userProfile after assigning the profile photo if needed
            savedUserProfile = userProfileRepository.save(userProfile);
        }

        // Set the password (hashed) and other properties for the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setBlocked(false);

        // If no roles are assigned, set the default role (ROLE_MEMBER)
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role role = roleRepository.findByRoleName("ROLE_MEMBER");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
        }

        // If the userProfile was saved, link it to the user
        if (savedUserProfile != null) {
            user.setUserProfileId(savedUserProfile);
        }

        // Save the user entity to the database
        return userRepository.save(user);
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

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

}
