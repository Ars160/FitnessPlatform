package org.example.fitnessplatform.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.fitnessplatform.dto.UserDto;
import org.example.fitnessplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the user.");
        }
    }

    @PostMapping("/{userId}/role")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> updateUserRole(@PathVariable Long userId, @RequestParam String newRole) {
        try {
            String result = userService.updateUserRole(userId, newRole);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Role not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the role.");
        }
    }


}
