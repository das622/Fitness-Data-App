package com.gym.gym_exercises.service;

import com.gym.gym_exercises.repository.ExerciseRepository;
import com.gym.gym_exercises.model.Exercise;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository){
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> getExercise(){
        return exerciseRepository.findAll();
    }

    public List<Exercise> getExercisesByMuscle(String muscle) {
        return exerciseRepository.findByPrimaryMusclesContainingIgnoreCase(muscle);
    }

    public List<Exercise> getExercisesByName(String searchName) {
        return exerciseRepository.findByNameContainingIgnoreCase(searchName);
    }

    public List<Exercise> getExercisesByEquipment(String equipment) {
        return exerciseRepository.findByEquipmentContainingIgnoreCase(equipment);
    }

    public Exercise addExercise(Exercise exercise){
        return exerciseRepository.save(exercise);
    }
}