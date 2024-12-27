package org.example.fitnessplatform.controller;

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
    public String registerUser(@RequestParam(name = "name") String name,
                               @RequestParam(name = "email") String email,
                               @RequestParam(name = "password") String password,
                               @RequestParam(name = "repeat_password") String repeatPassword) {
        if (password.equals(repeatPassword)) {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            User newUser = authService.registerUser(user);
            if (newUser == null) {
                return "Пользователь с такой почтой уже существует";
            } else {
                return "Пользователь успешно зарегистрирован";
            }
        } else {
            return "Пароли не совпадают";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "name") String email,
                        @RequestParam(name = "password") String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            User loginUser = authService.getUserByEmail(email);
            if (loginUser == null) {
                return "Нет такого логина";
            } else {
                authService.loginUser(loginUser);
                return "Вы успешно зарегистрированы";
            }
        } else {
            return "Заполните поля полностью";
        }
    }
}
