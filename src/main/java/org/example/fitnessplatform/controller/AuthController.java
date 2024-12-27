package org.example.fitnessplatform.controller;

import org.example.fitnessplatform.dto.RegistrationDTO;
import org.example.fitnessplatform.model.User;
import org.example.fitnessplatform.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegistrationDTO registrationDTO, Model model) {
        User user = new User();
        user.setName(registrationDTO.getName());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(registrationDTO.getPassword());

        User newUser = authService.registerUser(user);
        if (newUser == null) {
            model.addAttribute("error", "Пользователь с такой почтой уже существует");
            return "redirect:/register_page";
        } else {
            model.addAttribute("message", "Пользователь успешно зарегистрирован");
            return "redirect:/login_page"; // Перенаправление на страницу логина
        }
    }
}
