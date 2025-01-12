package com.churchwebsite.churchwebsite.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errMsg = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        // Add attributes to the model
        model.addAttribute("status", statusCode);
        model.addAttribute("message", errMsg);
        model.addAttribute("timestamp", new java.util.Date());
        model.addAttribute("pageTitle", "Error");

        return "error"; // Render `error.html` from templates
    }

}
