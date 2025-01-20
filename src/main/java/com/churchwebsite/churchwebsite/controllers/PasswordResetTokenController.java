package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.dtos.ChurchDetailDTO;
import com.churchwebsite.churchwebsite.dtos.EmailDTO;
import com.churchwebsite.churchwebsite.dtos.PasswordResetWithLinkDTO;
import com.churchwebsite.churchwebsite.entities.PasswordResetToken;
import com.churchwebsite.churchwebsite.entities.Settings;
import com.churchwebsite.churchwebsite.entities.User;
import com.churchwebsite.churchwebsite.services.*;
import com.churchwebsite.churchwebsite.utils.TokenUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/users/password")
public class PasswordResetTokenController {


    private final PasswordResetTokenService passwordResetTokenService;
    private final CustomUserDetailService customUserDetailService;
    private final UserService userService;
    private final SettingsService settingsService;
    private final NotificationService notificationService;
    private final TemplateEngine templateEngine;
    private final ChurchDetailService churchDetailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordResetTokenController(PasswordResetTokenService passwordResetTokenService, PasswordResetTokenService passwordResetTokenService1, CustomUserDetailService customUserDetailService, UserService userService, SettingsService settingsService, NotificationService notificationService, TemplateEngine templateEngine, ChurchDetailService churchDetailService, PasswordEncoder passwordEncoder) {
        this.passwordResetTokenService = passwordResetTokenService1;
        this.customUserDetailService = customUserDetailService;
        this.userService = userService;
        this.settingsService = settingsService;
        this.notificationService = notificationService;
        this.templateEngine = templateEngine;
        this.churchDetailService = churchDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/link/request/form")
    public String showForgotPasswordLinkRequestForm(Model model){

        model.addAttribute("emailDto", new EmailDTO());

        return "forgot-password-request-form";
    }

    @PostMapping("/link/request/form/process")
    public String processForgotPasswordLinkRequestForm(@Valid @ModelAttribute("emailDto") EmailDTO emailDto,
                                                       BindingResult result,
                                                       Model model,
                                                       HttpServletRequest request,
                                                       RedirectAttributes redirectAttributes){
        // Check if email is registered otherwise reply error
        User user = customUserDetailService.loadUserByEmail(emailDto.getEmail());

        if(user == null){
            result.rejectValue("email", "emailDto.email", "Email is not registered in the system. Provide another email");
        }

        if(result.hasErrors()){
            model.addAttribute("emailDto", emailDto);
            return "forgot-password-request-form";
        }

        Settings tokenSetting = settingsService.findBySettingName("PASSWORD_RESET_TOKEN_LIFETIME");
        int tokenLifetimeInMinutes = tokenSetting != null ? tokenSetting.getSettingValueInt() : 15;

        // Create token
        String token = TokenUtils.generatePasswordResetToken();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setEmail(emailDto.getEmail());
        passwordResetToken.setExpireAt(LocalDateTime.now().plusMinutes(tokenLifetimeInMinutes));
        passwordResetToken.setUsed(false);

        PasswordResetToken savedToken = passwordResetTokenService.save(passwordResetToken);

        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        ChurchDetailDTO churchDetail = churchDetailService.getChurchDetail();

        StringBuilder resetLink = new StringBuilder();
        resetLink.append(scheme)
                .append("://")
                .append(serverName)
                .append(":")
                .append(serverPort)
                .append("/users/password/link/reset/form")
                .append("?token=")
                .append(savedToken.getToken());

        StringBuilder churchLogoUrl = new StringBuilder();

        if (churchDetail != null && churchDetail.getChurch() != null){
            churchLogoUrl.append(scheme)
                    .append("://")
                    .append(serverName)
                    .append(":")
                    .append(serverPort)
                    .append(churchDetail.getChurch().getChurchLogo());
        }

        String subject = "Password Reset Link";

        Context context = new Context();

        if(user != null && user.getUserProfile() != null && user.getUserProfile().getFirstName() != null && !user.getUserProfile().getFirstName().isEmpty()){
            context.setVariable("firstName", user.getUserProfile().getFirstName());
        }else {
            context.setVariable("firstName", "User");
        }
        context.setVariable("link", resetLink);
        context.setVariable("expiryTime", tokenLifetimeInMinutes);
        context.setVariable("timeUnit", "minutes");

        context.setVariable("churchDetail", churchDetail);
        context.setVariable("churchLogoUrl", churchLogoUrl);

        String htmlContent = templateEngine.process("password-reset-email-template", context);
        try {
            notificationService.sendPasswordResetEmail(emailDto.getEmail(), subject, htmlContent);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        redirectAttributes.addFlashAttribute("email", emailDto.getEmail());
        return "redirect:/users/password/link/request/sent";
    }


    @GetMapping("/link/request/sent")
    public String showForgotPasswordLinkRequestSentMessage(Model model){

        return "forgot-password-request-sent";
    }




    @GetMapping("/link/reset/form")
    public String showForgotPasswordResetForm(@RequestParam("token") String token,
                                              RedirectAttributes redirectAttributes,
                                              Model model) {
        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token);
        if (passwordResetToken == null) {
            model.addAttribute("error", "Invalid link. Please make sure clicking on a valid link !");
            return "forgot-password-token-error";
        }if (passwordResetToken.isUsed()) {
            model.addAttribute("error", "Invalid link. Link already used!");
            return "forgot-password-token-error";
        } else {
            // Get PASSWORD_RESET_TOKEN_LIFETIME from setting to get the lifetime of token
            Settings tokenSetting = settingsService.findBySettingName("PASSWORD_RESET_TOKEN_LIFETIME");

            int tokenLifetimeInMinutes = tokenSetting != null ? tokenSetting.getSettingValueInt() : 15;

            LocalDateTime createdTime = passwordResetToken.getCreatedAt();
            LocalDateTime expireTime = passwordResetToken.getExpireAt();
            // Check if the token is still valid
            boolean isTokenValid = createdTime.plusMinutes(tokenLifetimeInMinutes).isBefore(expireTime) ||
                    createdTime.plusMinutes(tokenLifetimeInMinutes).isEqual(expireTime);

            if(!isTokenValid){
                model.addAttribute("error", "Password reset link is expired!");
                return "forgot-password-token-error";
            }


            User user = customUserDetailService.loadUserByEmail(passwordResetToken.getEmail());

            PasswordResetWithLinkDTO passwordResetWithLinkDTO = new PasswordResetWithLinkDTO();
            passwordResetWithLinkDTO.setUsername(user.getUsername());

            model.addAttribute("passwordResetWithLinkDto", passwordResetWithLinkDTO);
            model.addAttribute("resetToken", token);
            model.addAttribute("pageTitle", "Forgot Password Reset Form");

            return "forgot-password-reset-form";
        }
    }



    @PostMapping("/link/reset/form/process")
    public String processPasswordReset(@Valid @ModelAttribute("passwordResetWithLinkDto") PasswordResetWithLinkDTO passwordResetWithLinkDTO,
                                       BindingResult result,
                                       @RequestParam("resetToken") String resetToken,
                                       RedirectAttributes redirectAttributes,
                                       Model model){

        User user = userService.getUserByUsername(passwordResetWithLinkDTO.getUsername());
        if(!passwordResetWithLinkDTO.passwordMatches()){
            result.rejectValue("newPasswordConfirm", "error.newPasswordConfirm", "Password does not match!");
        }

        if(result.hasErrors()){
            model.addAttribute("passwordResetWithLinkDTO", passwordResetWithLinkDTO);
            model.addAttribute("resetToken", resetToken);

            return "forgot-password-reset-form";
        }

        user.setPassword(passwordEncoder.encode(passwordResetWithLinkDTO.getNewPassword()));
        user.setPasswordConfirm(passwordResetWithLinkDTO.getNewPasswordConfirm());
        userService.saveUser(user);

        // Update reset token to used
        PasswordResetToken tokenObject = passwordResetTokenService.findByToken(resetToken);

        if(tokenObject !=null){
            tokenObject.setUsed(true);
            passwordResetTokenService.save(tokenObject);
        }

        redirectAttributes.addFlashAttribute("message", "Password updated successfully");
        return "redirect:/users/login";
    }

}
