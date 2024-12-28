package org.example.fitnessplatform.controller;

import org.example.fitnessplatform.dto.UserDto;
import org.example.fitnessplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/api/admin/users/updateRole")
    public ResponseEntity<Map<String, Object>> updateUserRole(@RequestParam Long userId, @RequestParam String role) {
        System.out.println("AAAAAAAAAAAAAA");

        userService.updateUserRole(userId, role);
        Map<String, Object> response = new HashMap<>();
        return ResponseEntity.ok(response);
    }
}
