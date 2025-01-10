package com.churchwebsite.churchwebsite.controller_advices;

import com.churchwebsite.churchwebsite.services.UserService;
import com.churchwebsite.churchwebsite.utils.CustomUserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UserAdvice {

    private final UserService userService;


    public UserAdvice(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void userDetail(Model model){

        CustomUserDetails userDetails = userService.getCurrentUser();

        if(userDetails != null){
            model.addAttribute("user", userDetails.getUser());
        }

    }
}
