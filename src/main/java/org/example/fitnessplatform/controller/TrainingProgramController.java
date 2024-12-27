package org.example.fitnessplatform.controller;

import lombok.RequiredArgsConstructor;
import org.example.fitnessplatform.model.TrainingProgram;
import org.example.fitnessplatform.service.TrainingProgramService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/training-programs")
@RequiredArgsConstructor
public class TrainingProgramController {

    private final TrainingProgramService trainingProgramService;

    @GetMapping
    public List<TrainingProgram> getAll(){
        return trainingProgramService.getAllTrainingPrograms();
    }


    @GetMapping("/{id}")
    public TrainingProgram getTrainingProgramById(@PathVariable Long id) {
        return trainingProgramService.getById(id).get();
    }


    @PostMapping("/create")
    public String createTraining(@RequestParam("title") String title,
                                 @RequestParam("description") String description,
                                 @RequestParam("type") String type,
                                 @RequestParam("duration") Integer duration,
                                 @RequestParam("image") MultipartFile image) {
        return trainingProgramService.createTrainingProgram(title, description, type,duration,image);
    }

    @PutMapping("/{id}")
    public String updateTraining(@PathVariable Long id,
                                 @RequestParam("title") String newTitle,
                                 @RequestParam("description") String newDescription,
                                 @RequestParam("type") String newType,
                                 @RequestParam("duraiton") Integer newDuration,
                                 @RequestParam(value = "image", required = false) MultipartFile newImage) {
        return trainingProgramService.updateTrainingProgram(id, newTitle, newDescription, newType, newDuration, newImage);
    }

    @DeleteMapping("/{id}")
    public void deleteTraining(@PathVariable Long id) {
        trainingProgramService.deleteTrainingProgram(id);
    }
}
