package org.example.fitnessplatform.repository;

import org.example.fitnessplatform.model.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram , Long> {
}
