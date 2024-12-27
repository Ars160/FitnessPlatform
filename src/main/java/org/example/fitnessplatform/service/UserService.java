package org.example.fitnessplatform.service;

import org.example.fitnessplatform.dto.UserDto;
import org.example.fitnessplatform.dto.UserMapper;
import org.example.fitnessplatform.model.User;
import org.example.fitnessplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDTOList(users);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
