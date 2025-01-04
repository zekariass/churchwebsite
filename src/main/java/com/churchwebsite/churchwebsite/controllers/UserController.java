package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Role;
import com.churchwebsite.churchwebsite.entities.User;
import com.churchwebsite.churchwebsite.entities.UserProfile;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.RoleService;
import com.churchwebsite.churchwebsite.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final ChurchDetailService churchDetailService;

    public UserController(UserService userService, RoleService roleService, ChurchDetailService churchDetailService) {
        this.userService = userService;
        this.roleService = roleService;
        this.churchDetailService = churchDetailService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        return "login-form";
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

    @PostMapping("/processUserRegistration")
    public String processUserRegistration(@ModelAttribute User user, @ModelAttribute UserProfile userProfile, Model model){

        userService.addNewUserWithProfile(user, userProfile);
        return "redirect:/users/login";
    }

}
