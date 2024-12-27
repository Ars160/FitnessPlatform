package org.example.fitnessplatform.controller;

import lombok.RequiredArgsConstructor;
import org.example.fitnessplatform.model.TrainingProgram;
import org.example.fitnessplatform.service.TrainingProgramService;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/{id}")
    public TrainingProgram getOne(@PathVariable(name = "id") Long id){
            return trainingProgramService.getById(id).get();
    }

    @PostMapping("/")
    public TrainingProgram saveTrainingProgram (@RequestBody TrainingProgram book){
        return trainingProgramService.save(book);
    }

    @PutMapping("/{id}")
    public TrainingProgram updateTrainingProgram(@PathVariable Long id, @RequestBody TrainingProgram updatedTrainingProgram) {
        return trainingProgramService.update(id, updatedTrainingProgram);
    }


    @DeleteMapping(value = "/{id}")
    public void deleteTrainingProgram(@PathVariable(name = "id") Long id){
        trainingProgramService.delete(id);
        }

}
