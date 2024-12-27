package org.example.fitnessplatform.controller;

import org.example.fitnessplatform.dto.UserDto;
import org.example.fitnessplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/updateUser")
    public String updateUser(@RequestParam(value = "name", required = false) String newName,
                              @RequestParam(value = "avatar", required = false) MultipartFile newAvatar,
                              @RequestParam(value = "old_password", required = false) String oldPassword,
                              @RequestParam(value = "new_password", required = false) String newPassword) throws IOException {
        return userService.updateUser(newName, newAvatar, oldPassword, newPassword);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
