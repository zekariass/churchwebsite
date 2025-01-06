package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.dtos.PasswordResetDTO;
import com.churchwebsite.churchwebsite.entities.Role;
import com.churchwebsite.churchwebsite.entities.User;
import com.churchwebsite.churchwebsite.entities.UserProfile;
import com.churchwebsite.churchwebsite.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final ChurchDetailService churchDetailService;
    private final UserProfileService userProfileService;
    private final PasswordEncoder passwordEncoder;

    private String PUBLIC_CONTENT = "layouts/base";

    public UserController(UserService userService, RoleService roleService, ChurchDetailService churchDetailService, UserProfileService userProfileService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.churchDetailService = churchDetailService;
        this.userProfileService = userProfileService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        UserDetails userDetails = userService.getCurrentUser();
        if(userDetails == null){
            return "login-form";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String processLogout(HttpServletRequest request, HttpServletResponse response, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return "redirect:?logout";
    }

    @GetMapping("/register")
    public String showUserRegistrationForm(Model model){
        Set<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("user", new User());
        model.addAttribute("userProfile", new UserProfile());
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        return "user-registration-form";
    }

    @PostMapping("/register/form/process")
    public String processUserRegistration(@Valid @ModelAttribute ("user") User user,
                                          BindingResult result,
                                          @ModelAttribute ("userProfile") UserProfile userProfile,
                                          @RequestParam(value = "profilePhotoBlob", required = false) MultipartFile multipartFile,
                                          Model model){


        // Check if username exists
        if(user.getUsername() != null || !Objects.equals(user.getUsername(), "")){
            boolean userExist = userService.existsByUsername(user.getUsername());
            if(userExist){
                result.rejectValue("username", "error.username", "Username already registered!");
            }
        }

        // Check if Email exist
        if(user.getEmail() != null || !Objects.equals(user.getEmail(), "")){
            boolean emailExist = userService.existsByEmail(user.getEmail());
            if(emailExist){
                result.rejectValue("email", "error.email", "Email already registered!");
            }
        }

        // Check if password and passwordConfirm does not match
        if(!(user.getPassword().isEmpty() && user.getPasswordConfirm().isEmpty()) && !(user.getPassword() == null && user.getPasswordConfirm() == null)){
            if(!Objects.equals(user.getPasswordConfirm(), user.getPassword())){
                result.rejectValue("passwordConfirm", "error.passwordConfirm", "Password does not match");
            }
        }

        // Check for errors
        if(result.hasErrors()){
            Set<Role> roles = roleService.findAll();
            model.addAttribute("roles", roles);
            model.addAttribute("user", user);
            model.addAttribute("userProfile", userProfile);
            model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

            return "user-registration-form";
        }


        userService.addNewUserWithProfile(user, userProfile, multipartFile);
        return "redirect:/users/login";
    }


    @GetMapping("/profile")
    public String showUserProfile(Model model){

        model.addAttribute("activeContentPage", "user-profile");
        model.addAttribute("user", userService.getCurrentUser().getUser());
        model.addAttribute("userProfile", userService.getCurrentUser().getUser().getUserProfile());
        model.addAttribute("resetPasswordDto", new PasswordResetDTO());

        return PUBLIC_CONTENT;
    }

    @PostMapping("/profile/edit/process")
    public String processUserProfileUpdate(@Valid @ModelAttribute UserProfile userProfile,
                                           BindingResult result,
                                           @RequestParam(value = "userProfilePhotoBlob", required = false) MultipartFile userProfilePhoto,
                                           Model model
                                           ){

        if(result.hasErrors()){
            model.addAttribute("activeContentPage", "user-profile");
            model.addAttribute("user", userService.getCurrentUser().getUser());
            model.addAttribute("userProfile", userProfile);
            model.addAttribute("resetPasswordDto", new PasswordResetDTO());

            return PUBLIC_CONTENT;
        }

        userProfileService.updateUserProfile(userProfile, userProfilePhoto);

        return "redirect:/users/profile";
    }


    @PostMapping("/password/reset")
    public String processPasswordReset(@Valid @ModelAttribute("resetPasswordDto") PasswordResetDTO passwordResetDTO,
                                       BindingResult result,
                                       RedirectAttributes redirectAttributes,
                                       Model model){

        User currentUser = userService.getCurrentUser().getUser();

        if(!passwordResetDTO.passwordMatches()){
            result.rejectValue("newPasswordConfirm", "error.newPasswordConfirm", "Password does not match!");
        }

        if(result.hasErrors()){
            model.addAttribute("activeContentPage", "user-profile");
            model.addAttribute("user", currentUser);
            model.addAttribute("userProfile", currentUser.getUserProfile());
            model.addAttribute("resetPasswordDto", passwordResetDTO);

            return PUBLIC_CONTENT;
        }

        if(!passwordEncoder.matches(passwordResetDTO.getCurrentPassword(), currentUser.getPassword())){
            redirectAttributes.addFlashAttribute("error", "Current password is incorrect");
            return "redirect:/users/profile";
        }

        currentUser.setPassword(passwordEncoder.encode(passwordResetDTO.getNewPassword()));
        currentUser.setPasswordConfirm(passwordResetDTO.getNewPasswordConfirm());

        userService.saveUser(currentUser);

        redirectAttributes.addFlashAttribute("message", "Password updated successfully");
        return "redirect:/users/profile";
    }

}
