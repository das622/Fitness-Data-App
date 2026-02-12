package com.gym.gym_exercises.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository){
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> getExercise(){
        return exerciseRepository.findAll();
    }

    public List<Exercise> getExercisesByMuscle(String muscle) {
        return exerciseRepository.findAll().stream()
                .filter(exercise -> exercise.getPrimary_muscles().toLowerCase().contains(muscle.toLowerCase()))
                .collect(Collectors.toList());
    }
    public List<Exercise> getExercisesByName(String searchName) {
        return exerciseRepository.findAll().stream()
                .filter(exercise -> exercise.getName().toLowerCase().contains(searchName.toLowerCase()))
                .collect(Collectors.toList());
    }
    public List<Exercise> getExercisesByLevel(String level) {
        return exerciseRepository.findAll().stream()
                .filter(exercise -> level.equalsIgnoreCase(exercise.getLevel()))
                .collect(Collectors.toList());
    }
    // 2. Search by Equipment (e.g., "body only", "kettlebells")
    public List<Exercise> getExercisesByEquipment(String equipment) {
        return exerciseRepository.findAll().stream()
                .filter(exercise -> exercise.getEquipment().toLowerCase().contains(equipment.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Exercise addExercise(Exercise exercise){
        exerciseRepository.save(exercise);
        return exercise;
    }
}
