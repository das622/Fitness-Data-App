package com.gym.gym_exercises.controller;

import com.gym.gym_exercises.dto.WorkoutSummaryDTO;
import com.gym.gym_exercises.model.WorkoutSet;
import com.gym.gym_exercises.service.WorkoutSetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
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

    // DELETE A SET (Industry Standard)
    @DeleteMapping("/sets/{setId}")
    public ResponseEntity<Void> deleteSet(@PathVariable Long setId) {
        workoutSetService.deleteSet(setId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/workouts/{workoutId}/summary")
    public ResponseEntity<WorkoutSummaryDTO> getWorkoutSummary(@PathVariable Long workoutId) {
        WorkoutSummaryDTO summary = workoutSetService.getWorkoutSummary(workoutId);
        return ResponseEntity.ok(summary);
    }

    // GET 1-REP MAX FOR AN EXERCISE
    // GET 1-REP MAX FOR AN EXERCISE (Professional JSON Format)
    @GetMapping("/sets/1rm/{exerciseId}")
    public ResponseEntity<Map<String, Object>> getExercise1RepMax(@PathVariable String exerciseId) {
        double oneRepMax = workoutSetService.calculate1RepMax(exerciseId);

        // Build a clean JSON object on the fly
        Map<String, Object> response = new HashMap<>();
        response.put("exerciseId", exerciseId);
        response.put("oneRepMax", oneRepMax);
        response.put("unit", "lbs");
        response.put("formulaUsed", "Epley");

        return ResponseEntity.ok(response);
    }
}