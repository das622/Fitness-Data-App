package com.gym.gym_exercises.controller;

import com.gym.gym_exercises.model.Exercise;
import com.gym.gym_exercises.service.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;


    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public List<Exercise> getExercise(){
        return exerciseService.getExercise();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Exercise>> getExerciseByName(@PathVariable String name){
        return ResponseEntity.ok(exerciseService.getExercisesByName(name));
    }

    @PostMapping
    public ResponseEntity<Exercise> addExercise(@RequestBody Exercise exercise) {
        Exercise createdExercise = exerciseService.addExercise(exercise);
        return new ResponseEntity<>(createdExercise, HttpStatus.CREATED);
    }

    @GetMapping("/muscles/{primaryMuscles}")
    public ResponseEntity<List<Exercise>> getExerciseByMuscle(@PathVariable String primaryMuscles){
        return ResponseEntity.ok(exerciseService.getExercisesByMuscle(primaryMuscles));
    }

    @GetMapping("/equipment/{equipment}")
    public ResponseEntity<List<Exercise>> getExerciseByEquipment(@PathVariable String equipment){
        return ResponseEntity.ok(exerciseService.getExercisesByEquipment(equipment));
    }
}






