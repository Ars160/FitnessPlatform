package org.example.fitnessplatform.service;

import org.example.fitnessplatform.dto.UserDto;
import org.example.fitnessplatform.dto.UserMapper;
import org.example.fitnessplatform.model.Role;
import org.example.fitnessplatform.model.User;
import org.example.fitnessplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDTOList(users);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
