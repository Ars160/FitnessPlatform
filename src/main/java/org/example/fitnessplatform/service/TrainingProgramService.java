package org.example.fitnessplatform.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.fitnessplatform.model.TrainingProgram;
import org.example.fitnessplatform.repository.TrainingProgramRepository;
import org.example.fitnessplatform.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingProgramService {
    private  final TrainingProgramRepository trainingProgramRepository;

    public String createTrainingProgram(String title, String description,String type , int duration, MultipartFile image) {
        try {
            TrainingProgram training = new TrainingProgram();
            training.setTitle(title);
            training.setDescription(description);
            training.setType(type);
            training.setDuration(duration);
            training.setImage(image.getBytes());
            training.setTrainer();
            trainingProgramRepository.save(training);
            return "Training saved";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
    }


    public List<TrainingProgram> getAllTrainingPrograms(){
        return trainingProgramRepository.findAll();
    }

    public Optional<TrainingProgram> getById(Long id){
        return Optional.of(trainingProgramRepository.findById(id).orElse(new TrainingProgram()));
    }



public String updateTrainingProgram(Long id, String newTitle, String newDescription, String newType, Integer newDuration, MultipartFile newImage) {
    try {
        TrainingProgram existingTraining = trainingProgramRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post with id " + id + " not found"));

        existingTraining.setTitle(newTitle);
        existingTraining.setDescription(newDescription);
        existingTraining.setType(newType);
        existingTraining.setDuration(newDuration);

        if (newImage != null && !newImage.isEmpty()) {
            existingTraining.setImage(newImage.getBytes());
        }

        trainingProgramRepository.save(existingTraining);
        return "Training updated";
    } catch (IOException e) {
        e.printStackTrace();
        return "Error";
    }
}

    public void deleteTrainingProgram(Long id){
        trainingProgramRepository.deleteById(id);
    }


}
