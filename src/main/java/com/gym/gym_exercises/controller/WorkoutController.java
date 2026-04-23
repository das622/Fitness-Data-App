package com.gym.gym_exercises.controller;

import com.gym.gym_exercises.model.Workout;
import com.gym.gym_exercises.service.WorkoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = {"https://gym-frontend-one-gamma.vercel.app", "http://localhost:5174"})
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class WorkoutController {
    final private WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    // CREATE WORKOUT
    @PostMapping("/users/{userId}/workouts")
    public ResponseEntity<Workout> createWorkout(@PathVariable Long userId,@RequestBody Workout workout){
        Workout newWorkOut = workoutService.createWorkout(userId, workout);
        return new ResponseEntity<>(newWorkOut, HttpStatus.CREATED);

    }

    // READ WORKOUT
    @GetMapping("/users/{userId}/workouts")
    public ResponseEntity<List<Workout>> getAllWorkouts(@PathVariable Long userId){
        return ResponseEntity.ok(workoutService.getAllWorkouts(userId));

    }

    // UPDATE WORKOUT
    @PutMapping("/users/{userId}/workouts/{workoutId}")
    public ResponseEntity<Workout> updateWorkout(
            @PathVariable Long userId,
            @PathVariable Long workoutId,
            @RequestBody Workout workout){
        Workout updatedWorkout = workoutService.updateWorkout(userId,workoutId, workout);
        return ResponseEntity.ok(updatedWorkout);
    }

    // DELETE WORKOUT (Cascades to sets automatically)
    @DeleteMapping("/workouts/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long workoutId) {
        workoutService.deleteWorkout(workoutId);
        return ResponseEntity.noContent().build();
    }



}
