package com.gym.gym_exercises.controller;

import com.gym.gym_exercises.model.Exercise;
import com.gym.gym_exercises.service.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class ExerciseController {
    private final ExerciseService exerciseService;


    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("/exercises")
    public List<Exercise> getExercisesForUser(@RequestParam Long userId) {
        return exerciseService.getExercisesForUser(userId);
    }

    @GetMapping("/exercises/name/{name}")
    public ResponseEntity<List<Exercise>> getExerciseByName(@PathVariable String name){
        return ResponseEntity.ok(exerciseService.getExercisesByName(name));
    }

    @PostMapping("/exercises")
    public ResponseEntity<Exercise> addExercise(@RequestBody Exercise exercise, @RequestParam Long userId) {

        // Stamp it with the owner's ID and mark it as custom
        exercise.setUserId(userId);
        exercise.setCustom(true);

        Exercise createdExercise = exerciseService.addExercise(exercise);
        return new ResponseEntity<>(createdExercise, HttpStatus.CREATED);
    }

    @GetMapping("/exercises/muscles/{primaryMuscles}")
    public ResponseEntity<List<Exercise>> getExerciseByMuscle(@PathVariable String primaryMuscles){
        return ResponseEntity.ok(exerciseService.getExercisesByMuscle(primaryMuscles));
    }

    @GetMapping("/exercises/equipment/{equipment}")
    public ResponseEntity<List<Exercise>> getExerciseByEquipment(@PathVariable String equipment){
        return ResponseEntity.ok(exerciseService.getExercisesByEquipment(equipment));
    }
}






