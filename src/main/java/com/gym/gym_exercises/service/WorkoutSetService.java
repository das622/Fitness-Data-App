package com.gym.gym_exercises.service;

import com.gym.gym_exercises.dto.SetSummaryDTO;
import com.gym.gym_exercises.dto.WorkoutSummaryDTO;
import com.gym.gym_exercises.model.Exercise;
import com.gym.gym_exercises.model.Workout;
import com.gym.gym_exercises.model.WorkoutSet;
import com.gym.gym_exercises.repository.ExerciseRepository;
import com.gym.gym_exercises.repository.WorkoutRepository;
import com.gym.gym_exercises.repository.WorkoutSetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    // CREATE SET (Updated Find or Create Logic)
    public WorkoutSet createSet(Long workoutId, WorkoutSet workoutSet) {
        // 1. Find the parent workout
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new RuntimeException("Workout not found"));
        workoutSet.setWorkout(workout);

        // 2. The Clean "Find or Create" Logic
        String incomingName = workoutSet.getExercise().getName();

        // Search by name. If found, use it. If NOT found, instantly create and save it.
        Exercise finalExercise = exerciseRepository.findByNameIgnoreCase(incomingName)
                .orElseGet(() -> {
                    Exercise newExercise = new Exercise();
                    newExercise.setName(incomingName);
                    newExercise.setCategory("Custom");
                    return exerciseRepository.save(newExercise);
                });

        // 3. Link the guaranteed exercise and save the set
        workoutSet.setExercise(finalExercise);
        return workoutSetRepository.save(workoutSet);
    }


    public WorkoutSet addSetToWorkout(Long workoutId, WorkoutSet workoutSet) {
        // 1. Find the workout folder
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        // 2. Find the specific exercise from the catalog
        // (This expects the frontend to send the exercise ID in the JSON)
//        String exerciseId = workoutSet.getExercise().getId();
//        Exercise exercise = exerciseRepository.findById(exerciseId)
//                .orElseThrow(() -> new RuntimeException("Exercise not found"));
        String exerciseId = workoutSet.getExercise().getId();
        String exerciseName = workoutSet.getExercise().getName();

        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseGet(() -> {
                    Exercise newEx = new Exercise();
                    newEx.setId(exerciseId);
                    newEx.setName(exerciseName != null ? exerciseName : exerciseId);
                    return exerciseRepository.save(newEx);
                });

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

    // DELETE A SPECIFIC SET
    public void deleteSet(Long setId) {
        if (!workoutSetRepository.existsById(setId)) {
            throw new RuntimeException("Set not found");
        }
        workoutSetRepository.deleteById(setId);
    }

    // FETCH A CLEAN WORKOUT SUMMARY
    public WorkoutSummaryDTO getWorkoutSummary(Long workoutId) {
        // 1. Fetch the raw Workout entity (assuming you have workoutRepository injected!)
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        // 2. Fetch the raw Sets
        List<WorkoutSet> rawSets = workoutSetRepository.findAllByWorkoutId(workoutId);

        // 3. Convert the raw Sets into clean SetSummaryDTOs
        List<SetSummaryDTO> cleanSets = rawSets.stream().map(set -> {
            return new SetSummaryDTO(
                    set.getExercise().getName(), // We dig into the related Exercise table just to grab the string name!
                    set.getReps(),
                    set.getWeight()
            );
        }).toList();

        // 4. Pack it all into the final WorkoutSummaryDTO box and return it
        return new WorkoutSummaryDTO(workout.getId(), workout.getName(), cleanSets);
    }

    public double calculate1RepMax(String exerciseId) {
        List<WorkoutSet> history = workoutSetRepository.findAllByExerciseId(exerciseId);

        if (history.isEmpty()) {
            return 0.0; // Return 0 if they have never done this exercise
        }

        double absoluteMax = 0.0;

        for (WorkoutSet set : history) {
            // The Epley Formula requires a double for accurate division
            double estimated1RM = set.getWeight() * (1.0 + (set.getReps() / 30.0));

            // Keep the highest number we find
            if (estimated1RM > absoluteMax) {
                absoluteMax = estimated1RM;
            }
        }

        // Round to 2 decimal places so the JSON looks clean (e.g., 273.33)
        return Math.round(absoluteMax * 100.0) / 100.0;
    }
}