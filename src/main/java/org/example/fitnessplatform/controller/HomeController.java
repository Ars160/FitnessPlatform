package org.example.fitnessplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/home_page")
    public String getHomePage() {
        return "index";
    }

    @GetMapping("/register_page")
    public String showRegiterPage(){
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
}
