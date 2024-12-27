package org.example.fitnessplatform.service;

import org.example.fitnessplatform.dto.LoginDTO;
import org.example.fitnessplatform.dto.RegistrationDTO;
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

import java.util.Set;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
    }

    public User registerUser(User user) {
        User checkUser = userRepository.findByEmail(user.getEmail());
        if (checkUser != null) {
            return null;
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role userRole = roleRepository.findByName("USER");
            user.setRole(userRole);
            return userRepository.save(user);
        }
    }

    public boolean loginUser(LoginDTO loginDTO) {
        User checkUser = userRepository.findByEmail(loginDTO.getEmail());
        if (checkUser != null) {
            if (!passwordEncoder.matches(loginDTO.getPassword(), checkUser.getPassword())) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
