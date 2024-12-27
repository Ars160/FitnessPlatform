package org.example.fitnessplatform.controller;

import org.example.fitnessplatform.dto.UserDto;
import org.example.fitnessplatform.model.User;
import org.example.fitnessplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public UserDto getUserById() {
        return userService.getUserById(userService.getCurrentSessionUser().getId());
    }

    @PostMapping("/updateProfile")
    public UserDto updateUser(@RequestParam(value = "name", required = false) String newName,
                           @RequestParam(value = "avatar", required = false) MultipartFile newAvatar,
                           @RequestParam(value = "old_password", required = false) String oldPassword,
                           @RequestParam(value = "new_password", required = false) String newPassword) {
        return userService.updateUser(newName, newAvatar, oldPassword, newPassword);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
