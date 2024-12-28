package org.example.fitnessplatform.controller;

import lombok.RequiredArgsConstructor;
import org.example.fitnessplatform.model.TrainingProgram;
import org.example.fitnessplatform.service.TrainingProgramService;
import org.example.fitnessplatform.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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
    public String getTrainingProgramById(@PathVariable Long id, Model model) {
        Optional<TrainingProgram> trainingProgram = trainingProgramService.getById(id);
        if (trainingProgram.isPresent()) {
            model.addAttribute("trener", trainingProgram.get().getTrainer().getName());
            model.addAttribute("program", trainingProgram.get());
        } else {
            model.addAttribute("errorMessage", "Training program not found");
        }
        return "showOneTrainingProgram";
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

    @PostMapping("/edit")
    public String updateTraining(@RequestParam("id") Long id,
                                 @RequestParam("title") String newTitle,
                                 @RequestParam("description") String newDescription,
                                 @RequestParam("type") String newType,
                                 @RequestParam("duration") Integer newDuration,
                                 @RequestParam(value = "image", required = false) MultipartFile newImage,Model model) {
        if (trainingProgramService.updateTrainingProgram(id, newTitle, newDescription, newType, newDuration, newImage) !=null){
         model.addAttribute("error","Trainer is updated");
        return "redirect:/training-programs";
        }return "redirect:/training-programs";
    }

    @GetMapping("/{id}/edit")
    public String editTrainingProgram(@PathVariable Long id, Model model) {
        Optional<TrainingProgram> trainingProgram = trainingProgramService.getById(id);
        if (trainingProgram.isPresent()) {
            model.addAttribute("program", trainingProgram.get());
            return "showEditTrainingProgram";
        } else {
            model.addAttribute("errorMessage", "Training program not found");
            return "showEditTrainingProgram";
        }
    }


    @PostMapping("/delete/{id}")
    public String deleteTraining(@PathVariable Long id) {
        trainingProgramService.deleteTrainingProgram(id);
        return "redirect:/training-programs";
    }

}
