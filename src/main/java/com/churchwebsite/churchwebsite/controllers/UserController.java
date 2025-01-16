package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.User;
import com.churchwebsite.churchwebsite.services.CustomUserDetailService;
import com.churchwebsite.churchwebsite.services.PaginationService;
import com.churchwebsite.churchwebsite.services.RoleService;
import com.churchwebsite.churchwebsite.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("dashboard/users")
public class UserController {

    private final UserService userService;
    private final CustomUserDetailService customUserDetailService;
    private final PaginationService paginationService;
    private final RoleService roleService;

//    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";
    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-layouts/dash-base";

    @Autowired
    public UserController(UserService userService, CustomUserDetailService customUserDetailService, PaginationService paginationService, RoleService roleService) {
        this.userService = userService;
        this.customUserDetailService = customUserDetailService;
        this.paginationService = paginationService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String getAllUsers(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                              @RequestParam(value = "size", required = false) Integer pageSize,
                              @RequestParam(value = "sortBy", defaultValue = "username") String sortBy,
                              @RequestParam(value = "email", required = false) String email,
                              @RequestParam(value = "username", required = false) String username,
                              @RequestParam(value = "firstName", required = false) String firstName,
                              @RequestParam(value = "lastName", required = false) String lastName,
                              @RequestParam(value = "role", required = false) Integer roleId,
                              HttpServletRequest request,
                              Model model){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<User> pagedUsers = userService.findAll(email, username, firstName, lastName, roleId, page, pageSize, sortBy);


        List<User> users = pagedUsers.getContent();


        model.addAttribute("currentPage", pagedUsers.getNumber()+1);
        model.addAttribute("totalItems", pagedUsers.getTotalElements());
        model.addAttribute("totalPages", pagedUsers.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("pageTitle", "Users List");

        model.addAttribute("roles", roleService.findAll());

        model.addAttribute("users", users);
        model.addAttribute("activeDashPage", "users-list");

        return DASHBOARD_MAIN_PANEL;

    }


    @GetMapping("/edit/{id}")
    public String showUserEditForm(@PathVariable("id") User user, Model model){

        model.addAttribute("user", user);
        model.addAttribute("userRoles", roleService.findAll());
        model.addAttribute("activeDashPage", "user-edit-form");

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@ModelAttribute("user") User user, Model model){
        if(user.isBlocked()){
            user.setActive(false);
        }
        userService.saveUser(user);

        return "redirect:/dashboard/users";
    }

    @GetMapping("/detail/{id}")
    public String showUserDetail(@PathVariable("id") User user, Model model){

        model.addAttribute("user", user);
        model.addAttribute("activeDashPage", "user-detail");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer userId){

        userService.deleteById(userId);
        return "redirect:/dashboard/users";
    }
}
