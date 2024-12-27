package org.example.fitnessplatform.controller;

import org.example.fitnessplatform.dto.LoginDTO;
import org.example.fitnessplatform.dto.RegistrationDTO;
import org.example.fitnessplatform.model.User;
import org.example.fitnessplatform.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegistrationDTO registrationDTO) {
        User user = new User();
        user.setName(registrationDTO.getName());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(registrationDTO.getPassword());
        User newUser = authService.registerUser(user);
        if (newUser == null) {
            return "Пользователь с такой почтой уже существует";
        } else {
            return "Пользователь успешно зарегистрирован";
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        User loginUser = authService.getUserByEmail(loginDTO.getEmail());
        if (loginUser == null) {
            return "Нет такого логина";
        } else {
            authService.loginUser(loginUser);
            return "Вы успешно зарегистрированы";
        }
    }
}
