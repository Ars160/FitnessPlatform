package org.example.fitnessplatform;

import org.example.fitnessplatform.model.User;
import org.example.fitnessplatform.repository.RoleRepository;
import org.example.fitnessplatform.repository.TrainingProgramRepository;
import org.example.fitnessplatform.repository.UserRepository;
import org.example.fitnessplatform.service.TrainingProgramService;
import org.example.fitnessplatform.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class FitnessPlatformApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TrainingProgramService trainingProgramService;
    @Autowired
    private TrainingProgramRepository trainingProgramRepository;

    @Test
    void contextLoads() {
    }

    String folderPath = "src/main/resources";
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

    @Test
    void checkRoleUpdate() {

        User user = new User();
        user.setEmail("email@gmail.com");
        user.setRole(roleRepository.findByName("USER"));
        userRepository.save(user);

        userService.updateUserRole(user.getId(), "ADMIN");

        User updatedUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));

        Assertions.assertEquals("ADMIN", updatedUser.getRole().getName());

        userRepository.deleteAll();
    }

    @Test
    void checkDeleteUser() {
        User user = new User();
        user.setEmail("email@gmail.com");
        user.setRole(roleRepository.findByName("USER"));
        userRepository.save(user);

        userService.deleteUser(user.getId());

        Assertions.assertFalse(userRepository.existsById(user.getId()));

        userRepository.deleteAll();
    }

    @Test
    void createTrainingProgramSuccess() throws IOException {
        String title = "Test Training Program";
        String description = "This is a test training program.";
        String type = "Strength";
        int duration = 60;

        byte[] imageBytes = "image content".getBytes(); // Здесь можно использовать реальные данные изображения

        // Создаем MultipartFile с изображением
        MockMultipartFile image = new MockMultipartFile(
                "file",                           // Имя поля файла (аналогично как оно будет передано в запросе)
                "test-image.jpg",                 // Имя файла
                "image/jpeg",                     // MIME тип
                imageBytes                        // Данные изображения
        );

        String result = trainingProgramService.createTrainingProgram(title, description, type, duration, image);

        Assertions.assertEquals("Training saved", result);
        Assertions.assertTrue(trainingProgramRepository.count() > 0, "Training program should be saved in the database.");
    }

}
