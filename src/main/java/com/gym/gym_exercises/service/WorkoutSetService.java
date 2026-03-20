package com.gym.gym_exercises.service;

import com.gym.gym_exercises.model.Exercise;
import com.gym.gym_exercises.model.Workout;
import com.gym.gym_exercises.model.WorkoutSet;
import com.gym.gym_exercises.repository.ExerciseRepository;
import com.gym.gym_exercises.repository.WorkoutRepository;
import com.gym.gym_exercises.repository.WorkoutSetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutSetService {

    private final WorkoutSetRepository workoutSetRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    public WorkoutSetService(WorkoutSetRepository workoutSetRepository,
                             WorkoutRepository workoutRepository,
                             ExerciseRepository exerciseRepository) {
        this.workoutSetRepository = workoutSetRepository;
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public WorkoutSet addSetToWorkout(Long workoutId, WorkoutSet workoutSet) {
        // 1. Find the workout folder
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        // 2. Find the specific exercise from the catalog
        // (This expects the frontend to send the exercise ID in the JSON)
        String exerciseId = workoutSet.getExercise().getId();
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        // 3. Link everything together
        workoutSet.setWorkout(workout);
        workoutSet.setExercise(exercise);

        // 4. Save to the database
        return workoutSetRepository.save(workoutSet);
    }
    // FETCH ALL SETS FOR A WORKOUT
    public List<WorkoutSet> getSetsForWorkout(Long workoutId) {
        // We use the custom repository method we created earlier!
        return workoutSetRepository.findAllByWorkoutId(workoutId);
    }
}