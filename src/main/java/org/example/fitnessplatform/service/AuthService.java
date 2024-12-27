package org.example.fitnessplatform.service;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    String folderPath = "C:\\Users\\Mykty\\IdeaProjects\\social-media\\src\\main\\resources";
    String fileName = "noavatar.jpg";
    String imagePath = folderPath + File.separator + fileName;

    public byte[] convertImageToBytes(String imagePath) throws IOException {
        File file = new File(imagePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fis.read(bytes);
        fis.close();
        return bytes;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
    }

    public User registerUser(User user) throws IOException {
        User checkUser = userRepository.findByEmail(user.getEmail());
        if (checkUser != null) {
            return null;
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role userRole = roleRepository.findByName("USER");
            user.setRole(userRole);
            byte[] avatar = convertImageToBytes(imagePath);
            user.setAvatar(avatar);
            return userRepository.save(user);
        }
    }

}
