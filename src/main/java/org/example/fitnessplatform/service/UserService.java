package org.example.fitnessplatform.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.fitnessplatform.dto.UserDto;
import org.example.fitnessplatform.dto.UserMapper;
import org.example.fitnessplatform.model.Role;
import org.example.fitnessplatform.model.User;
import org.example.fitnessplatform.repository.RoleRepository;
import org.example.fitnessplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDTOList(users);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return userMapper.toDTO(user);
    }

    public String updateUser(String newName, MultipartFile newAvatar, String oldPassword, String newPassword) {
        try {
            User existingUser = getCurrentSessionUser();

            if (oldPassword != null && !oldPassword.isEmpty()) {
                if (!passwordEncoder.matches(oldPassword, existingUser.getPassword())) {
                    return "Неправильный старый пароль";
                }
            }

            if (newName != null && !newName.isEmpty()) {
                existingUser.setName(newName);
            }

            if (newAvatar != null && !newAvatar.isEmpty()) {
                existingUser.setAvatar(newAvatar.getBytes());
            }

            if (newPassword != null && !newPassword.isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(newPassword));
            }

            userRepository.save(existingUser);
            return "Данные успешно обновлены";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String updateUserRole(Long userId, String newRole) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = roleRepository.findByName(newRole);
        if (role == null) {
            throw new EntityNotFoundException("Role not found");
        }
        user.setRole(role);
        userRepository.save(user);
        return "Role updated successfully";
    }


    public void deleteUser(Long userId) {
            if (!userRepository.existsById(userId)) {
                throw new EntityNotFoundException("User with ID " + userId + " not found.");
            }
            userRepository.deleteById(userId);
        }


    public User getCurrentSessionUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            if (user != null) {
                return user;
            }
        }
        return null;
    }
}
