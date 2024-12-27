package org.example.fitnessplatform.controller;

import org.example.fitnessplatform.dto.UserDto;
import org.example.fitnessplatform.model.User;
import org.example.fitnessplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> updateUser(
            @RequestParam(value = "name", required = false) String newName,
            @RequestParam(value = "avatar", required = false) MultipartFile newAvatar,
            @RequestParam(value = "old_password", required = false) String oldPassword,
            @RequestParam(value = "new_password", required = false) String newPassword) {

        // Валидация и обновление данных
        String updateResult = userService.updateUser(newName, newAvatar, oldPassword, newPassword);

        Map<String, Object> response = new HashMap<>();

        // Если обновление прошло успешно
        if ("Данные успешно обновлены".equals(updateResult)) {
            response.put("status", "success");
            response.put("redirectUrl", "/profile");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", updateResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
