package com.gym.gym_exercises.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/exercise")
public class ExerciseController {
    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public List<Exercise> getExercise(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String primary_muscles,
            @RequestParam(required = false) String bylevel,
            @RequestParam(required = false) String equipment) {
        if (name != null) {
            return exerciseService.getExercisesByName(name);
        } else if (primary_muscles != null) {
            return exerciseService.getExercisesByMuscle(primary_muscles);
        } else if (bylevel != null) {
            return exerciseService.getExercisesByLevel(bylevel);

        } else if (equipment != null) {
            return exerciseService.getExercisesByEquipment(equipment);

        } else {
            return exerciseService.getExercise();
        }
    }
    @PostMapping
    public ResponseEntity<Exercise> addExercise(@RequestBody Exercise exercise) {
        Exercise createdExercise = exerciseService.addExercise(exercise);
        return new ResponseEntity<>(createdExercise, HttpStatus.CREATED);
    }
}






