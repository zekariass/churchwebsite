package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Role;
import com.churchwebsite.churchwebsite.entities.User;
import com.churchwebsite.churchwebsite.entities.UserProfile;
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

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "login-form";
    }

    @GetMapping("/logout")
    public String processLogout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:?logout";
    }

    @GetMapping("/register")
    public String showUserRegistrationForm(Model model){
        Set<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("user", new User());
        model.addAttribute("userProfile", new UserProfile());
        return "user-registration-form";
    }

    @PostMapping("/processUserRegistration")
    public String processUserRegistration(@ModelAttribute User user, @ModelAttribute UserProfile userProfile, Model model){
        System.out.println("==========================>: "+ user);
        System.out.println("==========================>: "+ userProfile);

        userService.addNewUserWithProfile(user, userProfile);
        return "redirect:/users/login";
    }

}
