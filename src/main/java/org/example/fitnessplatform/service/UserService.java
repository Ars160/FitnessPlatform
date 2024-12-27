package org.example.fitnessplatform.service;

import org.example.fitnessplatform.dto.UserDto;
import org.example.fitnessplatform.dto.UserMapper;
import org.example.fitnessplatform.model.Role;
import org.example.fitnessplatform.model.User;
import org.example.fitnessplatform.repository.RoleRepository;
import org.example.fitnessplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

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

//    public User registerUser(User user) {
//        User checkUser = userRepository.findByEmail(user.getEmail());
//        if (checkUser != null) {
//            return null;
//        } else {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            Role userRole = roleRepository.findByName("USER");
//            user.setRoles(Set.of(userRole));
//            return userRepository.save(user);
//        }
//    }
//
//    public User loginUser(User user) {
//        User checkUser = userRepository.findByEmail(user.getUsername());
//        System.out.println(checkUser);
//        if (checkUser != null) {
//            if (!checkUser.getEmail().equals(user.getEmail())
//                    || passwordEncoder.matches(checkUser.getPassword(), user.getPassword())) {
//                return null;
//            } else {
//                return checkUser;
//            }
//        } else {
//            return null;
//        }
//
//    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDTOList(users);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
