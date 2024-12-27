package org.example.fitnessplatform.service;

import lombok.RequiredArgsConstructor;
import org.example.fitnessplatform.model.TrainingProgram;
import org.example.fitnessplatform.repository.TrainingProgramRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingProgramService {
    private  final TrainingProgramRepository trainingProgramRepository;

    public TrainingProgram save(TrainingProgram book){
        return trainingProgramRepository.save(book);
    }


    public List<TrainingProgram> getAllTrainingPrograms(){
        return trainingProgramRepository.findAll();
    }

    public Optional<TrainingProgram> getById(Long id){
        return Optional.of(trainingProgramRepository.findById(id).orElse(new TrainingProgram()));
    }



    public TrainingProgram update(Long id, TrainingProgram book){
        book.setId(id);
        return trainingProgramRepository.save(book);
    }

    public void delete(Long id){
        trainingProgramRepository.deleteById(id);
    }


}
