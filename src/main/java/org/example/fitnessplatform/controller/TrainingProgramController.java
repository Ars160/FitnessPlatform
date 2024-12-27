package org.example.fitnessplatform.controller;

import lombok.RequiredArgsConstructor;
import org.example.fitnessplatform.model.TrainingProgram;
import org.example.fitnessplatform.service.TrainingProgramService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/training-programs")
@RequiredArgsConstructor
public class TrainingProgramController {



    private final TrainingProgramService trainingProgramService;

    @GetMapping("")
    public String getAllTrain(Model model){
        List<TrainingProgram> trainingPrograms = trainingProgramService.getAllTrainingPrograms();
        model.addAttribute("programs",trainingPrograms);
        return "showTrainingProgram";
    }


    @GetMapping("/{id}")
    public TrainingProgram getTrainingProgramById(@PathVariable Long id) {
        return trainingProgramService.getById(id).get();
    }


    @PostMapping("/create")
    public String createTraining(@RequestParam("title") String title,
                                 @RequestParam("description") String description,
                                 @RequestParam("type") String type,
                                 @RequestParam("duration") int duration,
                                 @RequestParam("image") MultipartFile image, Model model) {
        if(trainingProgramService.createTrainingProgram(title, description, type,duration,image) != null) {
            model.addAttribute("error","Train is Saved");
            return "redirect:/training-programs";
        }else {
            model.addAttribute("error", "This isn't saved");
            return "redirect:/training-programs/create";
        }
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
