package org.example.fitnessplatform.controller;

import org.example.fitnessplatform.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private UserService userService;

    @GetMapping("/home_page")
    public String getHomePage() {
        return "index";
    }

    @GetMapping("/register_page")
    public String showRegisterPage(){
        return "register_page";
    }

    @GetMapping("/login_page")
    public String showLoginPage(){
        return "login_page";
    }

    @GetMapping("/profile")
    public String getProfile() {
        return "profile";
    }

    @GetMapping("/training_page")
    public String getTrainingForm() {
        System.out.println("dddd");
        return "createTrainingProgram";
    }
}
