package com.gym.gym_exercises.repository;

import com.gym.gym_exercises.model.WorkoutSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutSetRepository extends JpaRepository<WorkoutSet, Long> {
    // Handy custom method for later!
    List<WorkoutSet> findAllByWorkoutId(Long workoutId);
}