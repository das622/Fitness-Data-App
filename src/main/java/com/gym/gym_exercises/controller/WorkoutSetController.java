package com.gym.gym_exercises.controller;

import com.gym.gym_exercises.model.WorkoutSet;
import com.gym.gym_exercises.service.WorkoutSetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1") // Adjust this if your base URL is different
public class WorkoutSetController {

    private final WorkoutSetService workoutSetService;

    public WorkoutSetController(WorkoutSetService workoutSetService) {
        this.workoutSetService = workoutSetService;
    }

    // CREATE A NEW SET
    @PostMapping("/workouts/{workoutId}/sets")
    public ResponseEntity<WorkoutSet> addSet(@PathVariable Long workoutId, @RequestBody WorkoutSet workoutSet) {
        WorkoutSet newSet = workoutSetService.addSetToWorkout(workoutId, workoutSet);
        return new ResponseEntity<>(newSet, HttpStatus.CREATED);
    }

    // GET ALL SETS FOR A WORKOUT
    @GetMapping("/workouts/{workoutId}/sets")
    public ResponseEntity<List<WorkoutSet>> getSetsForWorkout(@PathVariable Long workoutId) {
        List<WorkoutSet> workoutSets = workoutSetService.getSetsForWorkout(workoutId);
        return ResponseEntity.ok(workoutSets);
    }
}