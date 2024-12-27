package org.example.fitnessplatform.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.fitnessplatform.dto.UserDto;
import org.example.fitnessplatform.dto.UserMapper;
import org.example.fitnessplatform.model.User;
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

    public void deleteUser(Long userId) {
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
