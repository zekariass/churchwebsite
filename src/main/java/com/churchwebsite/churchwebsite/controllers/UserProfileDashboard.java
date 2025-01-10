package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.dtos.PasswordResetDTO;
import com.churchwebsite.churchwebsite.entities.User;
import com.churchwebsite.churchwebsite.entities.UserProfile;
import com.churchwebsite.churchwebsite.services.UserProfileService;
import com.churchwebsite.churchwebsite.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("dashboard/users")
public class UserProfileDashboard {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserProfileService userProfileService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public UserProfileDashboard(PasswordEncoder passwordEncoder, UserService userService, UserProfileService userProfileService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userProfileService = userProfileService;
    }

    @GetMapping("/profile")
    public String showUserProfile(Model model){

        User user = userService.getCurrentUser().getUser();

        model.addAttribute("activeDashPage", "user-profile");
        model.addAttribute("user", userService.getCurrentUser().getUser());

        if(user.getUserProfile() != null){
            model.addAttribute("userProfile", userService.getCurrentUser().getUser().getUserProfile());
        }else{
            model.addAttribute("userProfile", new UserProfile());
        }
        model.addAttribute("resetPasswordDto", new PasswordResetDTO());
        model.addAttribute("controller", "dashboard");

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/profile/edit/process")
    public String processUserProfileUpdate(@Valid @ModelAttribute UserProfile userProfile,
                                           BindingResult result,
                                           @RequestParam(value = "userProfilePhotoBlob", required = false) MultipartFile userProfilePhoto,
                                           Model model
    ){

        if(result.hasErrors()){
            model.addAttribute("activeDashPage", "user-profile");
            model.addAttribute("user", userService.getCurrentUser().getUser());
            model.addAttribute("userProfile", userProfile);
            model.addAttribute("resetPasswordDto", new PasswordResetDTO());

            return DASHBOARD_MAIN_PANEL;
        }

        userProfileService.updateUserProfile(userProfile, userProfilePhoto);

        return "redirect:/dashboard/users/profile";
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
            model.addAttribute("activeDashPage", "user-profile");
            model.addAttribute("user", currentUser);
            model.addAttribute("userProfile", currentUser.getUserProfile());
            model.addAttribute("resetPasswordDto", passwordResetDTO);

            return DASHBOARD_MAIN_PANEL;
        }

        if(!passwordEncoder.matches(passwordResetDTO.getCurrentPassword(), currentUser.getPassword())){
            redirectAttributes.addFlashAttribute("error", "Current password is incorrect");
            return "redirect:/dashboard/users/profile";
        }

        currentUser.setPassword(passwordEncoder.encode(passwordResetDTO.getNewPassword()));
        currentUser.setPasswordConfirm(passwordResetDTO.getNewPasswordConfirm());

        userService.saveUser(currentUser);

        redirectAttributes.addFlashAttribute("message", "Password updated successfully");
        return "redirect:/dashboard/users/profile";
    }
}
