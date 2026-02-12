package com.gym.gym_exercises.exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, String> {

    void deleteByName(String name);

    Optional<Exercise> findByName(String exercise);

    List<Exercise> findByCategory(String category); // Find "Strength", "Cardio", etc.

    List<Exercise> findByLevel(String level);       // Find "Beginner", "Intermediate"

    List<Exercise> findByEquipment(String equipment); // Find "Dumbbell", "Barbell"
}
